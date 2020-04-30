package com.example.wordhub.conf;


/**
 * @author LindaBlack
 * 请求服务器的配置信息
 */
public class Configuration {

    //服务器地址
    public static final String IP = "192.168.0.101";
    //端口号
    public static final int PORT = 8080;
    //后台项目名+方法路径名
    public static final String ADDUSER = "/WordHubAPP/User/add";
    public static final String ISUSER = "/WordHubAPP/User/isUser";
    public static final String GETUSER = "/WordHubAPP/User/get";
    public static final String GETBOOK = "/WordHubAPP/Book/get";
    public static final String ADDBOOK = "/WordHubAPP/Book/add";
    public static final String ISBOOK = "/WordHubAPP/Book/isBook";
    public static final String DELETEBOOK = "/WordHubAPP/Book/delete";
    public static final String ADDRECORD = "/WordHubAPP/Record/add";
    public static final String GETRECORD = "/WordHubAPP/Record/get";
    public static final String ISRECORD = "/WordHubAPP/Record/isRecord";
    public static final String GETRECORDMODE = "/WordHubAPP/Record/getRecordMode";
    public static final String UPDATEMODE = "/WordHubAPP/Record/update";
    public static final String GETRECORDLEXICON = "/WordHubAPP/Record/update";
    public static final String ADDPLAN = "/WordHubAPP/Plan/add";
    public static final String GETPLAN = "/WordHubAPP/Plan/get";
    public static final String ISPLAN = "/WordHubAPP/Plan/isPlan";
    public static final String UPDATEPLAN = "/WordHubAPP/Plan/update";
    public static final String GETREVIEW = "/WordHubAPP/Review/get";
    public static final String GETLEICON = "/WordHubAPP/Lexicon/get";
    public static final String GETFIND = "/WordHubAPP/Find/get";
    public static final String ADDUSERLEXICON = "/WordHubAPP/UserLexicon/add";
    public static final String ISADDUSERLEXICON = "/WordHubAPP/UserLexicon/isAdd";
    public static final String GETWORD = "/WordHubAPP/WordThesaurus/get";

}
