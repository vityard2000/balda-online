package com.tems.baldaonline.models;

import android.database.sqlite.SQLiteDatabase;

import com.tems.baldaonline.App;
import com.tems.baldaonline.domain.Word;

public class WordsRepository implements RepositoryWords{

    WordsLocalDataSource localDataSource;

    public WordsRepository(SQLiteDatabase dataBaseWords, SQLiteDatabase dataBaseGameData) {
        this.localDataSource = new WordsLocalDataSource(dataBaseWords, dataBaseGameData);
    }

    @Override
    public void getWord(String word, OnReadyWordData onReadyDataCallback) {
        if(!isInternet()) {
            localDataSource.getWord(word, onReadyDataCallback);
        }
    }

    @Override
    public void getRandomWordsByLength(int length, OnReadyWordData onReadyDataCallback) {
        if(!isInternet()) {
            localDataSource.getRandomWordsByLength(length, onReadyDataCallback);
        }
    }

    boolean isInternet(){
        return false;
    }

    @Override
    public void insert(String word) {
        localDataSource.insert(word);
    }

    public interface OnReadyWordData {
        void onReadyData(Word data);
    }
}


