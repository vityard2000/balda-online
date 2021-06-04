package com.tems.baldaonline.domain;

public class Turn {
    private String word;
    //private int[] cells;
    private char letter;
    private int numCel;
    private int user;

    public Turn(String word, char letter, int numCel, int user) {
        this.word = word;
        //this.cells = cells;
        this.letter = letter;
        this.numCel = numCel;
        this.user = user;
    }
    public Turn(){}

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    //public int[] getCells() {
    //    return cells;
    //}
//
    //public void setCells(int[] cells) {
    //    this.cells = cells;
    //}

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public int getNumCel() {
        return numCel;
    }

    public void setNumCel(int numCel) {
        this.numCel = numCel;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }
}
