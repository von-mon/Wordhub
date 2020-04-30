package com.example.wordhub.bean;

import org.jetbrains.annotations.NotNull;

public class Record {
    private Integer id;
    private Integer userId;
    private Integer lexiconId;
    private String wordName;
    private String wordMeaning;
    private String wordPhonetic;
    private String wordSent;
    private String startTime;
    private String lastTime;
    private int memoryMode;

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", userId=" + userId +
                ", lexiconId=" + lexiconId +
                ", wordName='" + wordName + '\'' +
                ", wordMeaning='" + wordMeaning + '\'' +
                ", wordPhonetic='" + wordPhonetic + '\'' +
                ", wordSent='" + wordSent + '\'' +
                ", startTime='" + startTime + '\'' +
                ", lastTime='" + lastTime + '\'' +
                ", memoryMode=" + memoryMode +
                '}';
    }

    public String getWordSent() {
        return wordSent;
    }

    public void setWordSent(String wordSent) {
        this.wordSent = wordSent;
    }

    public Integer getLexiconId() {
        return lexiconId;
    }

    public void setLexiconId(Integer lexiconId) {
        this.lexiconId = lexiconId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public int getMemoryMode() {
        return memoryMode;
    }

    public void setMemoryMode(int memoryMode) {
        this.memoryMode = memoryMode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    public String getWordMeaning() {
        return wordMeaning;
    }

    public void setWordMeaning(String wordMeaning) {
        this.wordMeaning = wordMeaning;
    }

    public String getWordPhonetic() {
        return wordPhonetic;
    }

    public void setWordPhonetic(String wordPhonetic) {
        this.wordPhonetic = wordPhonetic;
    }
}
