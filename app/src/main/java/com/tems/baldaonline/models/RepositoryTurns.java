package com.tems.baldaonline.models;

import android.database.Cursor;

import com.tems.baldaonline.domain.Turn;

import java.util.List;

public interface RepositoryTurns {
    void getAll(OnReadyTurnData onReadyTurnData);
    void removeAll();
    void insert(Turn turn);
    boolean isEmpty();
}
