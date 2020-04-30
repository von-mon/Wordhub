package com.example.wordhub.bean;

import org.jetbrains.annotations.NotNull;

public class Lexicon {
    private int id;
    private String name;
    private String level;

    @NotNull
    @Override
    public String toString() {
        return "Lexicon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", level='" + level + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
