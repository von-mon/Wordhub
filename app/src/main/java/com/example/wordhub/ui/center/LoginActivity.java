package com.example.wordhub.ui.center;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wordhub.MainActivity;
import com.example.wordhub.R;
import com.example.wordhub.conf.Configuration;
import com.example.wordhub.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

/**
 * @author LindaBlack
 * 登录功能处理
 */
public class LoginActivity extends AppCompatActivity {
    private EditText username, password;

    @SuppressLint("CommitPrefEdits")
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                try {
                    JSONObject jsonObject = new JSONObject(msg.obj.toString());
                    JSONObject object = jsonObject.getJSONObject("userInfo");
                    LogUtil.d("object",object.toString());
                    if(object.getString("username").equals("500")){
                        Toast.makeText(LoginActivity.this,"你还未注册",Toast.LENGTH_SHORT).show();
                    }else {
                        String pwd = object.getString("password");
                        int id = object.getInt("id");
                        String nickname = object.getString("nickname");
                        //保存用户信息
                        SharedPreferences.Editor edit = getSharedPreferences("data", MODE_PRIVATE).edit();
                        edit.putString("username", object.getString("username"));
                        edit.putString("nickname", nickname);
                        edit.putInt("userId", id);
                        edit.apply();
                        if (pwd.equals(password.getText().toString())) {
                            LogUtil.d("OkHTTP", "登录成功");
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                            LogUtil.d("OkHTTP", "密码错误");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        Button login = findViewById(R.id.login);
        Button register = findViewById(R.id.register);

        //向服务器验证登录数据
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient client = new OkHttpClient.Builder().build();
                        FormBody body = new FormBody.Builder()
                                .add("username", username.getText().toString())
                                .build();
                        final Request request = new Request.Builder()
                                .post(body)
                                .url("http://" + Configuration.IP + ":" + Configuration.PORT + Configuration.GETUSER)
                                .build();
                        try {
                            Response response = client.newCall(request).execute();
                            Message message = new Message();
                            message.obj = Objects.requireNonNull(response.body()).string();
                            message.what = 1;
                            handler.sendMessage(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        //跳转到登录界面
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }
}

