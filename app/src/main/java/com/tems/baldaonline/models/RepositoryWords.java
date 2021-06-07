package com.tems.baldaonline.models;

public interface RepositoryWords {
    void getWord(String word, WordsRepository.OnReadyWordData onReadyDataCallback);
    void getRandomWordsByLength(int length, WordsRepository.OnReadyWordData onReadyDataCallback);
    void insert(String word);
}
