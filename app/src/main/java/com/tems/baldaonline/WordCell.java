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
        StringBuilder str = new StringBuilder();
        for (Cell i : word){str.append(i.getLetter());};
        return str.toString();
    }
    public String toStringNormal() {
        StringBuilder str = new StringBuilder();
        int cnt = 0;
        for (Cell i : word){
            if(cnt == 0){
                str.append(i.getLetter());
            }else str.append(i.getLetter().toString().toLowerCase());
            cnt++;
        };
        return str.toString();
    }

    public int size() {
        return word.size();
    }
    public void add(Cell cell){
        word.add(cell);
    }
}
