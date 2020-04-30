package com.example.wordhub.ui.word;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wordhub.MainActivity;
import com.example.wordhub.R;
import com.example.wordhub.application.MyApplication;
import com.example.wordhub.bean.Record;
import com.example.wordhub.bean.Word;
import com.example.wordhub.utils.ConnectUtil;
import com.example.wordhub.utils.LogUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class ReciteActivity extends AppCompatActivity {
    private TextView word, meaning, example, phonetic, new_count, review_count;
    private TextToSpeech tts;
    private CheckBox add;
    //计数
    private int count = 0;
    //开始出现的单词的初始位置
    private int temp = 0;
    //记录复习的数量
    private int temp_word = 0;
    //初始计划
    private int plan = 0;
    //记录初始复习的数量
    private int currentCountReview = 0;
    //单词表
    List<Word> word_list = new ArrayList<>();
    //需要复习的单词表
    List<Record> words = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recite);
        //去除标题栏
        Objects.requireNonNull(getSupportActionBar()).hide();
        word = findViewById(R.id.recite_word);
        meaning = findViewById(R.id.recite_meaing);
        example = findViewById(R.id.recite_example);
        phonetic = findViewById(R.id.recite_phonetic);
        new_count = findViewById(R.id.new_count);
        review_count = findViewById(R.id.review);
        final Button realize = findViewById(R.id.realize);
        Button uncertain = findViewById(R.id.uncertain);
        Button unfamiliarity = findViewById(R.id.unfamiliarity);
        ImageView pronounce = findViewById(R.id.recite_pronounce);
        add = findViewById(R.id.recite_add);
        SharedPreferences data = getSharedPreferences("data", MODE_PRIVATE);
        plan = data.getInt("plan", 0);
        temp_word = data.getInt("plan", 0);
        new_count.setText(String.valueOf(temp_word));
        //开始复习的记录
        currentCountReview = 0;
        review_count.setText(String.valueOf(count));
        //review_count.setText(String.valueOf(currentCountReview));
        //获取单词信息
        word_list = MyApplication.wordList;
        temp = MyApplication.recordLexiconList.size();
        //显示单词信息
        changeText();
        //初始化TextToSpeech
        tts = new TextToSpeech(ReciteActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                tts.setLanguage(Locale.ENGLISH);
            }
        });


        /*
            1:认识
            0：不确定
            -1：不认识
         */
        //认识单词的情况的处理
        realize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果今日计划已完成，开始复习
                if (count == plan) {
                    currentCountReview--;
                    //复习完成就跳转到主界面
                    if (currentCountReview == 0){
                        Intent intent = new Intent(ReciteActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                    setReview(1);
                } else {
                    //开始今日计划
                    temp++;
                    count++;
                    temp_word--;
                    reciteUntil(1);
                }
            }
        });

        //不确定单词情况的处理
        uncertain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果今日计划已完成，开始复习
                if (count == plan) {
                    currentCountReview--;
                    //复习完成就跳转到主界面
                    if (currentCountReview == 0){
                        Intent intent = new Intent(ReciteActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                    setReview(0);
                } else {
                    //开始今日计划
                    temp++;
                    count++;
                    temp_word--;
                    reciteUntil(0);
                }
            }
        });

        //不认识单词情况的处理
        unfamiliarity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果今日计划已完成，开始复习
                if (count == plan) {
                    currentCountReview--;
                    //复习完成就跳转到主界面
                    if (currentCountReview == 0){
                        Intent intent = new Intent(ReciteActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                    setReview(-1);
                }else {
                    //开始今日计划
                    temp++;
                    count++;
                    temp_word--;
                    reciteUntil(-1);
                }
            }
        });

        //朗读发音
        pronounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.setSpeechRate(0.65f);
                tts.speak(word.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });

        //添加到单词本中
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences data = getSharedPreferences("data", MODE_PRIVATE);
                String addWord, addPhonetic, addMeaning;
                int id;
                id = data.getInt("userId", 0);
                addWord = word.getText().toString();
                addPhonetic = phonetic.getText().toString();
                addMeaning = meaning.getText().toString();
                Map<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("word", addWord);
                map.put("meaning", addMeaning);
                map.put("phonetic", addPhonetic);
                //点击添加到单词本
                if (add.isChecked()) {
                    ConnectUtil.addBook(map);
                }
            }
        });

    }

    //显示单词数据
    public void changeText() {
        if (word_list != null && word_list.size() > 0) {
            String word = word_list.get(temp).getWord();
            String type = word_list.get(temp).getType();
            String sent = word_list.get(temp).getSent();
            String phonetic = word_list.get(temp).getPhonetic();
            this.word.setText(word);
            meaning.setText(type);
            example.setText(sent);
            this.phonetic.setText(phonetic);
            //count++;
        }
        checkAdd();
    }


    @SuppressLint("SimpleDateFormat")
    private void reciteUntil(int mode) {
        Record record = new Record();
        new_count.setText(String.valueOf(temp_word));
        review_count.setText(String.valueOf(count));
        SharedPreferences data = getSharedPreferences("data", MODE_PRIVATE);
        final int userId = data.getInt("userId", 0);
        final int lexiconId = data.getInt("lexiconId",0);
        final String word = word_list.get(temp).getWord();
        String type = word_list.get(temp).getType();
        String sent = word_list.get(temp).getSent();
        String phonetic = word_list.get(temp).getPhonetic();
        this.word.setText(word);
        meaning.setText(type);
        example.setText(sent);
        this.phonetic.setText(phonetic);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String tempDate = formatter.format(date);
        final HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("lexiconId",lexiconId);
        map.put("wordName", word);
        map.put("wordPhonetic", phonetic);
        map.put("wordMeaning", type);
        map.put("startTime", tempDate);
        map.put("lastTime", tempDate);
        map.put("memoryMode", mode);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!ConnectUtil.isRecord(map)) {
                    LogUtil.d("addRecord","添加到学习记录");
                    ConnectUtil.addRecord(map);
                }else {
                    LogUtil.d("addRecord","已添加");
                }
            }
        }).start();
        //记录需要复习的单词
        if(mode == 0 || mode == -1){
            record.setWordName(word);
            record.setWordPhonetic(phonetic);
            record.setUserId(userId);
            record.setWordMeaning(type);
            record.setWordSent(sent);
            record.setStartTime(tempDate);
            record.setMemoryMode(mode);
            words.add(record);
            currentCountReview++;
        }
        checkAdd();
    }

    @SuppressLint("SimpleDateFormat")
    private void setReview(final int mode) {
        if(words.size()>0) {
            review_count.setText(String.valueOf(currentCountReview));
            LogUtil.d("ReciteActivity",String.valueOf(currentCountReview));
            SharedPreferences data = getSharedPreferences("data", MODE_PRIVATE);
            final int lexiconId = data.getInt("lexiconId", 0);
            final String word = words.get(currentCountReview).getWordName();
            String type = words.get(currentCountReview).getWordMeaning();
            String sent = words.get(currentCountReview).getWordSent();
            String phonetic = words.get(currentCountReview).getWordPhonetic();
            final int userId = words.get(currentCountReview).getUserId();
            this.word.setText(word);
            meaning.setText(type);
            example.setText(sent);
            this.phonetic.setText(phonetic);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HashMap<String, Object> map = new HashMap<>();
                    Date date = new Date(System.currentTimeMillis());
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String tempDate = formatter.format(date);
                    map.put("userId", userId);
                    map.put("word",word);
                    map.put("lexiconId",lexiconId);
                    map.put("lastTime", tempDate);
                    map.put("mode", mode);
                    ConnectUtil.updatePlan(map);
                }
            }).start();
        }
    }


    //检查是否添加到单词本上
    private void checkAdd() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (ConnectUtil.isBook(word.getText().toString())) {
                    add.setChecked(true);
                } else {
                    add.setChecked(false);
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        tts.shutdown();
        super.onDestroy();
    }
}
