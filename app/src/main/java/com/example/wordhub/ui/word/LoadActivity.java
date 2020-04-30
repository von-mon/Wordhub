package com.example.wordhub.ui.word;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.wordhub.R;
import com.example.wordhub.application.MyApplication;
import com.example.wordhub.bean.Word;
import com.example.wordhub.conf.Configuration;
import com.example.wordhub.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoadActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView load;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);


        WordTask wordTask = new WordTask();
        wordTask.execute();
        progressBar = findViewById(R.id.progressBar);
        load = findViewById(R.id.loading);
    }

    @SuppressLint("StaticFieldLeak")
    private class WordTask extends AsyncTask<Void, Integer, List<Word>> {

        @Override
        protected List<Word> doInBackground(Void... params) {
            SharedPreferences data = getSharedPreferences("data", MODE_PRIVATE);
            int lexiconId = data.getInt("lexiconId", 0);
            OkHttpClient client = new OkHttpClient.Builder().build();
            FormBody body = new FormBody.Builder()
                    .add("id",String.valueOf(lexiconId))
                    .build();
            Request request = new Request.Builder()
                    .post(body)
                    .url("http://" + Configuration.IP + ":" + Configuration.PORT + Configuration.GETWORD)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                JSONObject jsonObject = new JSONObject(Objects.requireNonNull(response.body()).string());
                JSONArray array = jsonObject.getJSONArray("wordThesaurusInfo");
                int total = array.length();
                LogUtil.v("数据的长度", String.valueOf(total));
                int count = 0;
                LogUtil.v("WordList", array.toString());
                List<Word> word_List = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    Word wordList = new Word();
                    JSONObject word = array.getJSONObject(i);
                    wordList.setId(word.getInt("id"));
                    wordList.setWord(word.getString("word"));
                    wordList.setType(word.getString("type"));
                    wordList.setPhonetic(word.getString("phonetic"));
                    wordList.setSent(word.getString("sent"));
                    //LogUtil.d("word", wordList.toString());
                    count += word.length();
                    //计算百分比
                    int progress = (count * 20) / total;
                    //LogUtil.d("当前长度",String.valueOf(count));
                    //LogUtil.d("百分比",String.valueOf(progress));
                    publishProgress(progress);
                    word_List.add(wordList);
                }
                return word_List;
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
            load.setText("单词数据加载中···");
        }

        @Override
        protected void onPostExecute(List<Word> list) {
            super.onPostExecute(list);
            LogUtil.d("跳转界面", "开始跳转");
            MyApplication.wordList=list;
            Intent intent = new Intent(LoadActivity.this, ReciteActivity.class);
            //LogUtil.v("单词信息",list.toString());
            startActivity(intent);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }
}


