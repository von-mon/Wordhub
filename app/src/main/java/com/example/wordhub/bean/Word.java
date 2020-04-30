package com.example.wordhub.bean;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Word implements Serializable {
    private Integer id;
    private String word;
    private String type;
    private String phonetic;
    private String sent;

    public Integer getId() {
        return id;
    }

    @NotNull
    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", type='" + type + '\'' +
                ", phonetic='" + phonetic + '\'' +
                ", sent='" + sent + '\'' +
                '}';
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }
}
