package com.example.wordhub.ui.train;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wordhub.R;
import com.example.wordhub.application.MyApplication;
import com.example.wordhub.utils.ConnectUtil;

public class ReviewActivity extends AppCompatActivity {
    private int count;
    private TextView wordName;
    private TextView wordPhonetic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        wordName = findViewById(R.id.word_name);
        wordPhonetic = findViewById(R.id.word_phonetic);
        Button realize = findViewById(R.id.realize);
        Button unRealize = findViewById(R.id.unrealize);

        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences data = getSharedPreferences("data", MODE_PRIVATE);
                int userId = data.getInt("userId", 0);
                ConnectUtil.getRecord(String.valueOf(userId));
            }
        }).start();

        changeText();

        realize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeText();
            }
        });

        unRealize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeText();
            }
        });
    }

    private void changeText() {
        if(MyApplication.recordList!=null && MyApplication.recordList.size()>0){
            count++;
            wordName.setText(MyApplication.recordList.get(count).getWordName());
            wordPhonetic.setText(MyApplication.recordList.get(count).getWordPhonetic());
        }

    }
}
