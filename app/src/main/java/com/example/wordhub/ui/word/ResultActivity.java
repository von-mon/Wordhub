package com.example.wordhub.ui.word;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultActivity {

    /**
     * tSpeakUrl : http://openapi.youdao.com/ttsapi?q=%E6%88%91&langType=zh-CHS&sign=7BC2CABA9D352E8072C8BC2A4D549CFF&salt=1582887192126&voice=4&format=mp3&appKey=2ea24b5d839277c6
     * returnPhrase : ["me"]
     * web : [{"value":["制造工程师","迷","我","工程硕士"],"key":"ME"},{"value":["关于我","个人资料","公告","自我介绍"],"key":"About Me"},{"value":["火线救援","救救我","浴火群英","拯救我"],"key":"Rescue Me"}]
     * query : me
     * translation : ["我"]
     * errorCode : 0
     * dict : {"url":"yddict://m.youdao.com/dict?le=eng&q=me"}
     * webdict : {"url":"http://m.youdao.com/dict?le=eng&q=me"}
     * basic : {"exam_type":["初中"],"us-phonetic":"mi; miː","phonetic":"miː","uk-phonetic":"miː","uk-speech":"http://openapi.youdao.com/ttsapi?q=me&langType=en&sign=E23E82CA840D327FDD5992DF730D9AC7&salt=1582887192126&voice=5&format=mp3&appKey=2ea24b5d839277c6","explains":["pron. 我（宾格）","n. 自我；极端自私的人；自我的一部分","n. (Me)人名；(日)马(姓)；(朝)袂；(阿拉伯、柬、老)梅"],"us-speech":"http://openapi.youdao.com/ttsapi?q=me&langType=en&sign=E23E82CA840D327FDD5992DF730D9AC7&salt=1582887192126&voice=6&format=mp3&appKey=2ea24b5d839277c6"}
     * l : en2zh-CHS
     * speakUrl : http://openapi.youdao.com/ttsapi?q=me&langType=en&sign=E23E82CA840D327FDD5992DF730D9AC7&salt=1582887192126&voice=4&format=mp3&appKey=2ea24b5d839277c6
     */

    private String tSpeakUrl;
    private String query;
    private String errorCode;
    private DictBean dict;
    private WebdictBean webdict;
    private BasicBean basic;
    private String l;
    private String speakUrl;
    private List<String> returnPhrase;
    private List<WebBean> web;
    private List<String> translation;

    public String getTSpeakUrl() {
        return tSpeakUrl;
    }

    public void setTSpeakUrl(String tSpeakUrl) {
        this.tSpeakUrl = tSpeakUrl;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public DictBean getDict() {
        return dict;
    }

    public void setDict(DictBean dict) {
        this.dict = dict;
    }

    public WebdictBean getWebdict() {
        return webdict;
    }

    public void setWebdict(WebdictBean webdict) {
        this.webdict = webdict;
    }

    public BasicBean getBasic() {
        return basic;
    }

    public void setBasic(BasicBean basic) {
        this.basic = basic;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getSpeakUrl() {
        return speakUrl;
    }

    public void setSpeakUrl(String speakUrl) {
        this.speakUrl = speakUrl;
    }

    public List<String> getReturnPhrase() {
        return returnPhrase;
    }

    public void setReturnPhrase(List<String> returnPhrase) {
        this.returnPhrase = returnPhrase;
    }

    public List<WebBean> getWeb() {
        return web;
    }

    public void setWeb(List<WebBean> web) {
        this.web = web;
    }

    public List<String> getTranslation() {
        return translation;
    }

    public void setTranslation(List<String> translation) {
        this.translation = translation;
    }

    public static class DictBean {
        /**
         * url : yddict://m.youdao.com/dict?le=eng&q=me
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class WebdictBean {
        /**
         * url : http://m.youdao.com/dict?le=eng&q=me
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class BasicBean {
        /**
         * exam_type : ["初中"]
         * us-phonetic : mi; miː
         * phonetic : miː
         * uk-phonetic : miː
         * uk-speech : http://openapi.youdao.com/ttsapi?q=me&langType=en&sign=E23E82CA840D327FDD5992DF730D9AC7&salt=1582887192126&voice=5&format=mp3&appKey=2ea24b5d839277c6
         * explains : ["pron. 我（宾格）","n. 自我；极端自私的人；自我的一部分","n. (Me)人名；(日)马(姓)；(朝)袂；(阿拉伯、柬、老)梅"]
         * us-speech : http://openapi.youdao.com/ttsapi?q=me&langType=en&sign=E23E82CA840D327FDD5992DF730D9AC7&salt=1582887192126&voice=6&format=mp3&appKey=2ea24b5d839277c6
         */

        @SerializedName("us-phonetic")
        private String usphonetic;
        private String phonetic;
        @SerializedName("uk-phonetic")
        private String ukphonetic;
        @SerializedName("uk-speech")
        private String ukspeech;
        @SerializedName("us-speech")
        private String usspeech;
        private List<String> exam_type;
        private List<String> explains;

        public String getUsphonetic() {
            return usphonetic;
        }

        public void setUsphonetic(String usphonetic) {
            this.usphonetic = usphonetic;
        }

        public String getPhonetic() {
            return phonetic;
        }

        public void setPhonetic(String phonetic) {
            this.phonetic = phonetic;
        }

        public String getUkphonetic() {
            return ukphonetic;
        }

        public void setUkphonetic(String ukphonetic) {
            this.ukphonetic = ukphonetic;
        }

        public String getUkspeech() {
            return ukspeech;
        }

        public void setUkspeech(String ukspeech) {
            this.ukspeech = ukspeech;
        }

        public String getUsspeech() {
            return usspeech;
        }

        public void setUsspeech(String usspeech) {
            this.usspeech = usspeech;
        }

        public List<String> getExam_type() {
            return exam_type;
        }

        public void setExam_type(List<String> exam_type) {
            this.exam_type = exam_type;
        }

        public List<String> getExplains() {
            return explains;
        }

        public void setExplains(List<String> explains) {
            this.explains = explains;
        }
    }

    public static class WebBean {
        /**
         * value : ["制造工程师","迷","我","工程硕士"]
         * key : ME
         */

        private String key;
        private List<String> value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<String> getValue() {
            return value;
        }

        public void setValue(List<String> value) {
            this.value = value;
        }
    }
}
