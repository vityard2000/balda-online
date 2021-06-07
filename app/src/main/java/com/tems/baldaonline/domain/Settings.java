package com.tems.baldaonline.domain;

public class Settings {
    private String firstNameUser;
    private String secondNameUser;
    private int timeForTurn = 0;
    private String startWord;

    public Settings(){}

    public Settings(String firstNameUser, String secondNameUser, int timeForTurn, String startWord) {
        this.firstNameUser = firstNameUser;
        this.secondNameUser = secondNameUser;
        this.timeForTurn = timeForTurn;
        this.startWord = startWord;
    }

    public String getFirstNameUser() {
        return firstNameUser;
    }

    public void setFirstNameUser(String firstNameUser) {
        this.firstNameUser = firstNameUser;
    }

    public String getSecondNameUser() {
        return secondNameUser;
    }

    public void setSecondNameUser(String secondNameUser) {
        this.secondNameUser = secondNameUser;
    }

    public int getTimeForTurn() {
        return timeForTurn;
    }

    public void setTimeForTurn(int timeForTurn) {
        this.timeForTurn = timeForTurn;
    }

    public String getStartWord() {
        return startWord;
    }

    public void setStartWord(String startWord) {
        this.startWord = startWord;
    }
}
