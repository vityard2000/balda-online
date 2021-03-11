package com.tems.baldaonline;

import java.util.ArrayList;
import java.util.List;

public class WordCell {

    private List<Cell> word;

    public WordCell() {
        this.word = new ArrayList<Cell>();
    }

    public  WordCell(List<Cell> word){
        this.word = word;
    }

    public List<Cell> getWord() {
        return word;
    }

    public void setWord(List<Cell> word) {
        this.word = word;
    }

    @Override
    public String toString() {
        String str = "";
        int cnt = 0;
        for (Cell i : word){
            if(cnt == 0){
                str += i.getLetter();
            }else str += i.getLetter().toString().toLowerCase();
            cnt++;
        };
        return str;
    }

    public int size() {
        return word.size();
    }
}
