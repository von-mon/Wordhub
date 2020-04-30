package com.example.wordhub.ui.word;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wordhub.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity {
    private TextView word;
    private TextView phonetic;
    private TextView meaning;
    private EditText search;
    private TextToSpeech textToSpeech;
    private View view;
    private final String YOUDAO_URL = "https://openapi.youdao.com/api";
    private final String APP_KEY = "2ea24b5d839277c6"; //应用ID
    private final String APP_SECRET = "npGh2oF8v7KGD2MF9C3e8xOLvgzxFYBN"; //应用密钥


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Objects.requireNonNull(this.getSupportActionBar()).hide(); //隐藏标题栏

        TextView cancel = findViewById(R.id.cancel);
        search = findViewById(R.id.editText_search);
        //获取布局id
        view = findViewById(R.id.word_info);
        word = view.findViewById(R.id.search_word);
        phonetic = view.findViewById(R.id.search_phonetic);
        meaning = view.findViewById(R.id.search_meaning);
        ImageView pronounce = view.findViewById(R.id.search_pronounce);
        //设置回车图标为搜索图标
        search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        //设置布局不可见
        view.setVisibility(View.GONE);

        //点击返回主界面
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); //把当前活动退出栈
            }
        });

        //响应键盘搜索
        search.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                    //获取输入框里的内容
                    String q = search.getText().toString().trim();
                    String salt = String.valueOf(System.currentTimeMillis());
                    String curtime = String.valueOf(System.currentTimeMillis() / 1000);
                    String signStr = APP_KEY + truncate(q) + salt + curtime + APP_SECRET;
                    //签名
                    String sign = getDigest(signStr);
                    String url = YOUDAO_URL + "?appKey=" + APP_KEY + "&q=" + q + "&from=auto&to=auto" + "&salt=" + salt
                            + "&sign=" + sign + "&signType=v3" + "&curtime=" + curtime;
                    try {
                        //传入访问地址，请求数据
                        requestForHttp(new URL(url));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });

        //语音合成
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(SearchActivity.this, "不支持此语言", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        pronounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech.speak(search.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });

    }

    //请求服务器返回数据
    public void requestForHttp(final URL url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(url).build();
                    Response response = client.newCall(request).execute();
                    String data = Objects.requireNonNull(response.body()).string();
                    showWord(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //显示单词详细信息
    public void showWord(final String data) {
        runOnUiThread(new Runnable() {  //runOnUiThread 回到主线程进行ui更新操作，Android不允许在子线程进行ui操作
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                view.setVisibility(View.VISIBLE);
                Gson gson = new Gson();
                final ResultActivity resultActivity = gson.fromJson(data, ResultActivity.class);
                //查询的单词
                word.setText(resultActivity.getQuery());
                //音标
                phonetic.setText("/" + resultActivity.getBasic().getPhonetic() + "/");
                //单词的意思
                for (int i = 0; i < resultActivity.getBasic().getExplains().size(); i++) {
                    meaning.append(resultActivity.getBasic().getExplains().get(i) + "\n");
                }
            }
        });
    }

    /**
     * 生成加密字段
     */
    public static String getDigest(String string) {
        if (string == null) {
            return null;
        }
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        byte[] btInput = string.getBytes(StandardCharsets.UTF_8);
        try {
            MessageDigest mdInst = MessageDigest.getInstance("SHA-256");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * @param q 查询的字符串
     * @return 返回字符串长度
     */
    public static String truncate(String q) {
        if (q == null) {
            return null;
        }
        int len = q.length();
        return len <= 20 ? q : (q.substring(0, 10) + len + q.substring(len - 10, len));
    }
}



