package com.example.wordhub.bean;

import org.jetbrains.annotations.NotNull;

public class WordBook {
    private Integer id;
    private String wordName;
    private String wordMeaning;
    private String wordPhonetic;

    @NotNull
    @Override
    public String toString() {
        return "WordBook{" +
                "id=" + id +
                ", wordName='" + wordName + '\'' +
                ", wordMeaning='" + wordMeaning + '\'' +
                ", wordPhonetic='" + wordPhonetic + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

