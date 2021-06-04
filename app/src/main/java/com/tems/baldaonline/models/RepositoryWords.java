package com.tems.baldaonline.models;

public interface RepositoryWords {
    void getWord(String word, OnReadyWordData onReadyDataCallback);
    void getRandomWordsByLength(int length, OnReadyWordData onReadyDataCallback);
    void insert(String word);
}
