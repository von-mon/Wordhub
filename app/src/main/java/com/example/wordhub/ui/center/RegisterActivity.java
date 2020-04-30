package com.example.wordhub.ui.center;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wordhub.R;
import com.example.wordhub.utils.ConnectUtil;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private EditText username, password, nickname, email, password2;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(RegisterActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(RegisterActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(RegisterActivity.this, "用户存在", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    try {
                        //休眠200毫秒
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //跳转到登录界面
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.register_username);
        password = findViewById(R.id.register_password);
        password2 = findViewById(R.id.register_password2);
        nickname = findViewById(R.id.register_nickname);
        email = findViewById(R.id.register_email);
        Button register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String name = username.getText().toString();
                        String pwd = password.getText().toString();
                        String pwd2 = password2.getText().toString();
                        String nick = nickname.getText().toString();
                        String address = email.getText().toString();
                        HashMap<String, Object> map = new HashMap<>();
                        //判空
                        if (name == null || pwd == null || pwd2 == null || nick == null || address == null) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                        //判等
                        if (pwd.equals(pwd2)) {
                            Message message = new Message();
                            message.what = 2;
                            handler.sendMessage(message);
                        }
                        map.put("username", name);
                        map.put("password", pwd);
                        map.put("nickname", nick);
                        map.put("email", address);
                        //判断用户是否存在
                        if (ConnectUtil.isUser(name)) {
                            //用户存在
                            Message message = new Message();
                            message.what = 3;
                            handler.sendMessage(message);
                        } else {
                            //用户不存在，则注册
                            if (ConnectUtil.addUser(map)) {
                                Message message = new Message();
                                message.what = 4;
                                handler.sendMessage(message);
                            }
                        }
                    }
                }).start();

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }
}
