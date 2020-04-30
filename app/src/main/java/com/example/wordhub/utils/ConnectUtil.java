package com.example.wordhub.utils;


import com.example.wordhub.application.MyApplication;
import com.example.wordhub.bean.Find;
import com.example.wordhub.bean.Lexicon;
import com.example.wordhub.bean.Record;
import com.example.wordhub.bean.Review;
import com.example.wordhub.bean.Word;
import com.example.wordhub.bean.WordBook;
import com.example.wordhub.conf.Configuration;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ConnectUtil {

    /**
     * 添加用户
     *
     * @param map 用户信息
     * @return boolean
     */

    public static boolean addUser(HashMap<String, Object> map) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        FormBody body = new FormBody.Builder()
                .add("data", String.valueOf(new JSONObject(map)))
                .build();
        final Request request = new Request.Builder()
                .post(body)
                .url("http://" + Configuration.IP + ":" + Configuration.PORT + Configuration.ADDUSER)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String temp = Objects.requireNonNull(response.body()).string();
            JSONObject jsonObject = new JSONObject(temp);
            int resultCode = jsonObject.getInt("resultCode");
            if (resultCode == 100) {
                return true;
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断用户是否存在
     *
     * @return boolean
     */
    public static boolean isUser(String name) {
        return connectUtilForResultCode(name, Configuration.ISUSER);
    }

    /**
     * 获取单词本的信息
     *
     * @param id 用户id
     */
    public static void getBook(String id) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        FormBody body = new FormBody.Builder()
                .add("id", id)
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url("http://" + Configuration.IP + ":" + Configuration.PORT + Configuration.GETBOOK)
                .build();
        try {
            Response response = client.newCall(request).execute();
            JSONObject jsonObject = new JSONObject(Objects.requireNonNull(response.body()).string());
            JSONArray array = jsonObject.getJSONArray("bookInfo");
            List<WordBook> list = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                WordBook book = new WordBook();
                JSONObject object = array.getJSONObject(i);
                book.setWordName(object.getString("wordName"));
                book.setWordMeaning(object.getString("wordMeaning"));
                book.setWordPhonetic(object.getString("wordPhonetic"));
                list.add(book);
            }
            MyApplication.bookList.clear();
            MyApplication.bookList.addAll(list);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加到单词本
     *
     * @param map 单词信息集合
     */
    public static void addBook(Map<String, Object> map) {
        connectUtil(String.valueOf(new JSONObject(map)), Configuration.ADDBOOK);
    }

    /**
     * 判断是否添加到单词本
     *
     * @param word 单词信息
     * @return boolean
     */
    public static boolean isBook(String word) {
        return connectUtilForResultCode(word, Configuration.ISBOOK);
    }

    /**
     * 删除单词本表里的所有数据
     */
    public static void deleteBook() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        FormBody body = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url("http://" + Configuration.IP + ":" + Configuration.PORT + Configuration.DELETEBOOK)
                .build();
        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加学习记录
     *
     * @param map 学习记录信息
     */
    public static void addRecord(HashMap<String, Object> map) {
        connectUtil(String.valueOf(new JSONObject(map)), Configuration.ADDRECORD);
    }

    /**
     * 获取用户学习记录
     * @param id 用户id
     */
    public static void getRecord(String id) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        FormBody body = new FormBody.Builder()
                .add("id", id)
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url("http://" + Configuration.IP + ":" + Configuration.PORT + Configuration.GETRECORD)
                .build();
        try {
            Response response = client.newCall(request).execute();
            JSONObject json = new JSONObject(Objects.requireNonNull(response.body()).string());
            JSONArray array = json.getJSONArray("recordInfo");
            List<Record> list = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Record record = new Record();
                record.setUserId(object.getInt("userId"));
                record.setWordName(object.getString("wordName"));
                record.setWordMeaning(object.getString("wordMeaning"));
                record.setWordPhonetic(object.getString("wordPhonetic"));
                record.setStartTime(object.getString("startTime"));
                record.setStartTime(object.getString("lastTime"));
                record.setMemoryMode(object.getInt("memoryMode"));
                list.add(record);
            }
            MyApplication.recordList.clear();
            MyApplication.recordList.addAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getRecordLexicon(HashMap<String,Object> map){
        OkHttpClient client = new OkHttpClient.Builder().build();
        FormBody body = new FormBody.Builder()
                .add("data", String.valueOf(new JSONObject(map)))
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url("http://" + Configuration.IP + ":" + Configuration.PORT + Configuration.GETRECORDLEXICON)
                .build();
        Response response = null;
        try{
            response = client.newCall(request).execute();
            JSONObject jsonObject = new JSONObject(Objects.requireNonNull(response.body()).string());
            JSONArray array = jsonObject.getJSONArray("recordLexiconInfo");
            List<Record> list = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Record record = new Record();
                record.setUserId(object.getInt("userId"));
                record.setWordName(object.getString("wordName"));
                record.setWordMeaning(object.getString("wordMeaning"));
                record.setWordPhonetic(object.getString("wordPhonetic"));
                record.setStartTime(object.getString("startTime"));
                record.setStartTime(object.getString("lastTime"));
                record.setMemoryMode(object.getInt("memoryMode"));
                list.add(record);
            }
            MyApplication.recordLexiconList.clear();
            MyApplication.recordLexiconList.addAll(list);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            assert response != null;
            response.close();
        }
    }

    /**
     * 判断是否记录
     *
     * @param word 单词
     * @return boolean
     */
    public static boolean isRecord(HashMap<String, Object> word) {
        return connectUtilForResultCode(String.valueOf(new JSONObject(word)), Configuration.ISRECORD);
    }

    /**
     * 添加计划
     *
     * @param map 计划信息
     */
    public static void addPlan(HashMap<String, Object> map) {
        connectUtil(String.valueOf(new JSONObject(map)), Configuration.ADDPLAN);
    }

    /**
     * 获取用户计划
     *
     * @param id 用户id
     */
    public static void getPlan(String id) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        FormBody body = new FormBody.Builder()
                .add("id", id)
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url("http://" + Configuration.IP + ":" + Configuration.PORT + Configuration.GETPLAN)
                .build();
        try {
            Response response = client.newCall(request).execute();
            JSONObject jsonObject = new JSONObject(Objects.requireNonNull(response.body()).string());
            JSONObject object = jsonObject.getJSONObject("planInfo");
            MyApplication.plan.setUserId(object.getInt("userId"));
            MyApplication.plan.setLexiconId(object.getInt("lexiconId"));
            MyApplication.plan.setLexiconName(object.getString("lexiconName"));
            MyApplication.plan.setPlan(object.getInt("plan"));
            MyApplication.plan.setDays(object.getInt("days"));
            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断是否添加计划
     *
     * @param id 用户id
     * @return boolean
     */
    public static boolean isPlan(String id) {
        return connectUtilForResultCode(id, Configuration.ISPLAN);
    }

    /**
     * 更新计划
     *
     * @param map 计划信息
     */
    public static void updatePlan(HashMap<String, Object> map) {
        connectUtil(String.valueOf(new JSONObject(map)), Configuration.UPDATEPLAN);
    }

    /**
     * 获得复习模式
     */
    public static void getReview() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        FormBody body = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url("http://" + Configuration.IP + ":" + Configuration.PORT + Configuration.GETREVIEW)
                .build();
        try {
            Response response = client.newCall(request).execute();
            JSONObject jsonObject = new JSONObject(Objects.requireNonNull(response.body()).string());
            JSONArray array = jsonObject.getJSONArray("reviewInfo");
            List<Review> list = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Review review = new Review();
                review.setId(object.getInt("id"));
                review.setMode(object.getString("mode"));
                list.add(review);
            }
            MyApplication.reviewList.clear();
            MyApplication.reviewList.addAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取词库
     */
    public static void getLexicon() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        FormBody body = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url("http://" + Configuration.IP + ":" + Configuration.PORT + Configuration.GETLEICON)
                .build();
        try {
            Response response = client.newCall(request).execute();
            JSONObject jsonObject = new JSONObject(Objects.requireNonNull(response.body()).string());
            JSONArray array = jsonObject.getJSONArray("lexiconInfo");
            List<Lexicon> list = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Lexicon lexicon = new Lexicon();
                lexicon.setId(object.getInt("id"));
                lexicon.setName(object.getString("name"));
                lexicon.setLevel(object.getString("level"));
                list.add(lexicon);
            }
            MyApplication.lexiconList.clear();
            MyApplication.lexiconList.addAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取扩展功能
     */
    public static void getFind() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        FormBody body = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url("http://" + Configuration.IP + ":" + Configuration.PORT + Configuration.GETFIND)
                .build();
        try {
            Response response = client.newCall(request).execute();
            JSONObject jsonObject = new JSONObject(Objects.requireNonNull(response.body()).string());
            JSONArray array = jsonObject.getJSONArray("findInfo");
            List<Find> list = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Find find = new Find();
                find.setId(object.getInt("id"));
                find.setName(object.getString("name"));
                list.add(find);
            }
            MyApplication.findList.clear();
            MyApplication.findList.addAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加到用户关系表
     *
     * @param map 词库和用户
     */
    public static void addUserLexicon(HashMap<String, Object> map) {
        connectUtil(String.valueOf(new JSONObject(map)), Configuration.ADDUSERLEXICON);
    }

    /**
     * 是否添加
     *
     * @param map 词库和用户
     * @return boolean
     */
    public static boolean isAddUserLexicon(HashMap<String, Object> map) {
        return connectUtilForResultCode(String.valueOf(new JSONObject(map)), Configuration.ISADDUSERLEXICON);
    }

    /**
     * 获取单词数据
     *
     * @param id 词库id
     */
    public static void getWord(String id) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        FormBody body = new FormBody.Builder()
                .add("id", id)
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url("http://" + Configuration.IP + ":" + Configuration.PORT + Configuration.GETWORD)
                .build();
        try {
            Response response = client.newCall(request).execute();
            JSONObject jsonObject = new JSONObject(Objects.requireNonNull(response.body()).string());
            JSONArray array = jsonObject.getJSONArray("wordThesaurusInfo");
            List<Word> list = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Word word = new Word();
                word.setId(object.getInt("id"));
                word.setWord(object.getString("word"));
                word.setType(object.getString("type"));
                word.setPhonetic(object.getString("phonetic"));
                word.setSent(object.getString("sent"));
                list.add(word);
            }
            MyApplication.wordList.clear();
            MyApplication.wordList.addAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取复习的记录
     * @param id 用户id
     */
    public static void getRecordMode(String id){
        OkHttpClient client = new OkHttpClient.Builder().build();
        FormBody body = new FormBody.Builder()
                .add("id", id)
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url("http://" + Configuration.IP + ":" + Configuration.PORT + Configuration.GETRECORDMODE)
                .build();
        Response response = null;
        try{
            response = client.newCall(request).execute();
            JSONObject jsonObject = new JSONObject(Objects.requireNonNull(response.body()).string());
            JSONArray array = jsonObject.getJSONArray("recordModeInfo");
            List<Record> list = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Record record = new Record();
                record.setUserId(object.getInt("userId"));
                record.setWordName(object.getString("wordName"));
                record.setWordMeaning(object.getString("wordMeaning"));
                record.setWordPhonetic(object.getString("wordPhonetic"));
                record.setStartTime(object.getString("startTime"));
                record.setStartTime(object.getString("lastTime"));
                record.setMemoryMode(object.getInt("memoryMode"));
                list.add(record);
            }
            MyApplication.recordModeList.clear();
            MyApplication.recordModeList.addAll(list);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            assert response != null;
            response.close();
        }

    }

    /**
     * 更新学习记录
     * @param map 学习记录
     */
    public static void updateRecord(HashMap<String,Object> map){
        connectUtil(String.valueOf(new JSONObject(map)),Configuration.UPDATEMODE);
    }

    /**
     * 连接工具类
     *
     * @param data   数据
     * @param method 方法
     */
    private static void connectUtil(String data, String method) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        FormBody body = new FormBody.Builder()
                .add("data", data)
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url("http://" + Configuration.IP + ":" + Configuration.PORT + method)
                .build();
        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接工具类
     *
     * @param data   数据
     * @param method 方法
     * @return boolean
     */
    private static boolean connectUtilForResultCode(String data, String method) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        FormBody body = new FormBody.Builder()
                .add("data", data)
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url("http://" + Configuration.IP + ":" + Configuration.PORT + method)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            String string = Objects.requireNonNull(response.body()).string();
            LogUtil.d("connectUtil", "resultCode：" + string);
            JSONObject jsonObject = new JSONObject(string);
            int resultCode = jsonObject.getInt("resultCode");
            if (resultCode == 100) {
                return true;
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            assert response != null;
            response.close();
        }
        return false;
    }
}

