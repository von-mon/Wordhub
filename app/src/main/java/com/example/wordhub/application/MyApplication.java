package com.example.wordhub.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;


import com.example.wordhub.bean.Find;
import com.example.wordhub.bean.Lexicon;
import com.example.wordhub.bean.Plan;
import com.example.wordhub.bean.Record;
import com.example.wordhub.bean.Review;
import com.example.wordhub.bean.Word;
import com.example.wordhub.bean.WordBook;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取全局context
 */
public class MyApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    public static List<Word> wordList;
    public static List<WordBook> bookList;
    public static List<Record> recordList;
    public static List<Review> reviewList;
    public static List<Lexicon> lexiconList;
    public static List<Find> findList;
    public static List<Record> recordModeList;
    public static List<Record> recordLexiconList;
    public static Plan plan;
    //初次加载标记
    public static boolean frag = false;
    //词库id
    public static int lexiconId;
    //词库名称
    public static String lexiconName;

    @Override
    public void onCreate() {
        super.onCreate();
        wordList = new ArrayList<>();
        bookList = new ArrayList<>();
        recordList = new ArrayList<>();
        reviewList = new ArrayList<>();
        lexiconList = new ArrayList<>();
        findList = new ArrayList<>();
        recordModeList = new ArrayList<>();
        recordLexiconList = new ArrayList<>();

        plan = new Plan();

        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
