package com.tems.baldaonline;

import java.util.ArrayList;
import java.util.List;

public class GameUser {

    private int count;
    private List<WordCell> words;

    public GameUser(){
        words = new ArrayList<WordCell>();
        count = 0;
    }

    public void setWord(WordCell wordCell) {
        words.add(wordCell);
        count += wordCell.size();
    }

    public int getCount(){
        return count;
    }

    public List<WordCell> getWords(){
        return words;
    }
}
