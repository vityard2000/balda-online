package com.tems.baldaonline.models;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DBHelperGameData extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "dataGameBD";
    public static final String TABLE_TURNS ="turns";
    public static final String TABLE_WORDS ="words";
    public static final String KEY_CELLS = "cells";
    public static final String KEY_ID = "ID";
    public static final String KEY_WORD = "word";
    public static final String KEY_LETTER = "letter";
    public static final String KEY_NUM_CELL = "num_cell";
    public static final String KEY_USER = "user";

    public DBHelperGameData(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_WORDS + "(" + KEY_WORD + " text )");
        db.execSQL("create table " + TABLE_TURNS + "(" + KEY_ID   + " integer primary key, "
                                                            //+ KEY_CELLS + "text, "
                                                            + KEY_WORD + " text, "
                                                            + KEY_LETTER + " text, "
                                                            + KEY_NUM_CELL + " integer, "
                                                            + KEY_USER + " integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_TURNS);
        onCreate(db);
    }

    public static ContentValues createContentValuesTurns(String word, String letter, int num_cell, int user){
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_WORD, word);
        contentValues.put(KEY_LETTER, letter);
        contentValues.put(KEY_NUM_CELL, num_cell);
        contentValues.put(KEY_USER, user);
        return contentValues;
    }
    public static ContentValues createContentValuesWords(String word){
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_WORD, word);
        return contentValues;
    }
}