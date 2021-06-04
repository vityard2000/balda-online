package com.tems.baldaonline.models;

import android.database.Cursor;

import com.tems.baldaonline.domain.Turn;

import java.util.List;

public interface OnReadyTurnData {
    void onReadyData(List<Turn> data);
}
