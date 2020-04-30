package com.example.wordhub.bean;

import org.jetbrains.annotations.NotNull;

public class Review {
    private Integer id;
    private String mode;

    @NotNull
    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", mode='" + mode + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
