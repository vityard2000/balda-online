package com.tems.baldaonline.domain;

public class Word {
    private int id;
    private String word;
    private int code;
    private int codeParent;
    private String gender;
    private int soul;

    public Word(int id, String word, int code, int codeParent, String gender, int soul) {
        this.id = id;
        this.word = word;
        this.code = code;
        this.codeParent = codeParent;
        this.gender = gender;
        this.soul = soul;
    }
    public Word(){}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCodeParent() {
        return codeParent;
    }

    public void setCodeParent(int codeParent) {
        this.codeParent = codeParent;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int isSoul() {
        return soul;
    }

    public void setSoul(int soul) {
        this.soul = soul;
    }
}
