package com.tems.baldaonline;

public class Cell {
    private char letter;
    private boolean focus;

    public Cell(char letter, boolean focus) {
        this.letter = letter;
        this.focus = focus;
    }

    public boolean getFocus() {
        return focus;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

}
