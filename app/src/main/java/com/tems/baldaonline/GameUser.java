package com.tems.baldaonline;

import java.util.ArrayList;
import java.util.List;

public class GameUser {
    private int count;
    private List<List<Cell>> words = new ArrayList<List<Cell>>();
    public GameUser(){
        count = 0;
    }

    public void setWord(List<Cell> wordCells) {
        words.add(wordCells);
        count += wordCells.size();
    }
    public int getCount(){
        return count;
    }
}
