package com.tems.baldaonline.domain;

import java.util.ArrayList;
import java.util.List;

public class GameUser {

    private int count;
    private List<String> words;

    public GameUser(){
        words = new ArrayList<>();
        count = 0;
    }

    public void setWord(String word) {
        words.add(word);
        count += word.length();
    }

    public int getCount(){
        return count;
    }

    public List<String> getWords(){
        return words;
    }
}
