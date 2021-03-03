package com.tems.baldaonline;

public class Cell {
    private Character letter;
    public Cell(char letter) {
        this.letter = letter;
    }
    public Cell() {
        this.letter = null;
    }
    public Character getLetter() {
        return letter;
    }
    public void setLetter(char letter) {
        this.letter = letter;
    }
}
