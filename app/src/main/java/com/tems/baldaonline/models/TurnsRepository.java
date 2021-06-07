package com.tems.baldaonline.models;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tems.baldaonline.App;
import com.tems.baldaonline.domain.Turn;

import java.util.ArrayList;
import java.util.List;


public class TurnsRepository implements RepositoryTurns {
    private final SQLiteDatabase databaseGameData;

    public TurnsRepository(SQLiteDatabase dataBaseGameData) {
        databaseGameData = dataBaseGameData;
    }

    @Override
    public void getAll(OnReadyTurnData onReadyTurnData) {
        new Thread(() -> {
            List<Turn> turns = new ArrayList<>();
            Cursor cursor = databaseGameData.query(DBHelperGameData.TABLE_TURNS, null, null, null, null, null , null);

            int wordIndex = cursor.getColumnIndex(DBHelperGameData.KEY_WORD);
            int letterIndex = cursor.getColumnIndex(DBHelperGameData.KEY_LETTER);
            int numCellIndex = cursor.getColumnIndex(DBHelperGameData.KEY_NUM_CELL);
            int userIndex = cursor.getColumnIndex(DBHelperGameData.KEY_USER);
            if(cursor.moveToFirst()){
                do {
                    Turn turn= new Turn();
                    turn.setWord(cursor.getString(wordIndex));
                    turn.setLetter(cursor.getString(letterIndex).charAt(0));
                    turn.setNumCel(cursor.getInt(numCellIndex));
                    turn.setUser(cursor.getInt(userIndex));
                    turns.add(turn);
                }while(cursor.moveToNext());
            }
            cursor.close();
            onReadyTurnData.onReadyData(turns);
        }).start();
    }

    @Override
    public void removeAll() {
        databaseGameData.delete(DBHelperGameData.TABLE_TURNS, null, null);
    }

    @Override
    public void insert(Turn turn) {
        databaseGameData.insert(DBHelperGameData.TABLE_TURNS , null , DBHelperGameData.createContentValuesTurns(turn.getWord(), String.valueOf(turn.getLetter()), turn.getNumCel() , turn.getUser()));
    }

    @Override
    public boolean isEmpty() {
        Cursor cursor = databaseGameData.query(DBHelperGameData.TABLE_TURNS, null, null, null, null, null , null);
        boolean isEmpty = cursor.moveToFirst();
        cursor.close();
        return isEmpty;
    }
    public interface OnReadyTurnData {
        void onReadyData(List<Turn> data);
    }
}

