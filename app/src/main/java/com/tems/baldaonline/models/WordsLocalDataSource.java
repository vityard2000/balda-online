package com.tems.baldaonline.models;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tems.baldaonline.App;
import com.tems.baldaonline.domain.Word;

import java.util.ArrayList;
import java.util.List;

public class WordsLocalDataSource implements RepositoryWords {

    private SQLiteDatabase databaseWords;
    private SQLiteDatabase databaseGameData;

    public WordsLocalDataSource(SQLiteDatabase dataBaseWords, SQLiteDatabase dataBaseGameData) {
        this.databaseWords  = dataBaseWords;
        this.databaseGameData = dataBaseGameData;
    }


    @Override
    public void getWord(String wordStr, OnReadyWordData onReadyDataCallback) {
        Word word = new Word();
        String selection = "word = ?";
        String[] selectionArg = {wordStr.toLowerCase()};
        Cursor cursorWord = databaseWords.query(DBHelper.TABLE_DICTIONARY, null, selection, selectionArg, null, null , null);
        Cursor cursorGameData = databaseGameData.query(DBHelperGameData.TABLE_WORDS, null, selection, selectionArg, null, null , null);
        if(cursorWord.moveToFirst()){
           word =  cursorToWord(cursorWord);

        }else if(cursorGameData.moveToFirst()) {
            int indexWord = cursorGameData.getColumnIndex(DBHelperGameData.KEY_WORD);
            word.setWord(cursorGameData.getString(indexWord));
        }
        cursorWord.close();
        cursorGameData.close();
        onReadyDataCallback.onReadyData(word);

    }

    @Override
    public void getRandomWordsByLength(int length, OnReadyWordData onReadyDataCallback) {
        Word word = new Word();
        String selection = "LENGTH(word) = " + length;
        Cursor cursorWord = databaseWords.query(DBHelper.TABLE_DICTIONARY, null, selection, null, null, null , null);
        if (cursorWord.moveToFirst()){
            cursorWord.moveToPosition((int)( Math.random() * cursorWord.getCount()));
            word = cursorToWord(cursorWord);
        }
        cursorWord.close();
        onReadyDataCallback.onReadyData(word);
    }

    @Override
    public void insert(String word) {
        databaseGameData.insert(DBHelperGameData.TABLE_WORDS , null , DBHelperGameData.createContentValuesWords(word.toLowerCase()));
    }


    Word cursorToWord(Cursor cursor){
        int indexIDD        = cursor.getColumnIndex(DBHelper.KEY_ID);
        int indexWord       = cursor.getColumnIndex(DBHelper.KEY_WORD);
        int indexCode       = cursor.getColumnIndex(DBHelper.KEY_CODE);
        int indexCodeParent = cursor.getColumnIndex(DBHelper.KEY_CODE_PARENT);
        int indexGender     = cursor.getColumnIndex(DBHelper.KEY_GENDER);
        int indexSoul       = cursor.getColumnIndex(DBHelper.KEY_SOUL);
        return new Word(
                cursor.getInt(indexIDD),
                cursor.getString(indexWord),
                cursor.getInt(indexCode),
                cursor.getInt(indexCodeParent),
                cursor.getString(indexGender),
                cursor.getInt(indexSoul));
    }
}