package com.example.wordhub.bean;

import org.jetbrains.annotations.NotNull;

public class Plan {
    private Integer userId;
    private Integer lexiconId;
    private String lexiconName;
    private Integer plan;
    private Integer days;

    @NotNull
    @Override
    public String toString() {
        return "Plan{" +
                "userId=" + userId +
                ", lexiconId=" + lexiconId +
                ", lexiconName='" + lexiconName + '\'' +
                ", plan=" + plan +
                ", days=" + days +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLexiconId() {
        return lexiconId;
    }

    public void setLexiconId(Integer lexiconId) {
        this.lexiconId = lexiconId;
    }

    public String getLexiconName() {
        return lexiconName;
    }

    public void setLexiconName(String lexiconName) {
        this.lexiconName = lexiconName;
    }

    public Integer getPlan() {
        return plan;
    }

    public void setPlan(Integer plan) {
        this.plan = plan;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}
