package com.tems.baldaonline.View;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tems.baldaonline.DBHelperGameData;
import com.tems.baldaonline.R;

public class ActivityMenu extends AppCompatActivity implements View.OnClickListener {
    Button buttonGameOneOnOne;
    Button buttonOnlineGame;
    private static final String myTag    = "debugTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        buttonGameOneOnOne = findViewById(R.id.activity_menu__bt_joint_game);
        buttonOnlineGame = findViewById(R.id.activity_menu__bt_online_game);
        buttonGameOneOnOne.setOnClickListener(this);
        buttonOnlineGame.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_menu__bt_joint_game:
                SQLiteDatabase database;
                DBHelperGameData dbHelperGameData;
                dbHelperGameData = new DBHelperGameData(this);
                database = dbHelperGameData.getReadableDatabase();
                Cursor cursor = database.query(DBHelperGameData.TABLE_TURNS, null, null, null, null, null , null);
                if(cursor.moveToFirst()){
                    cursor.close();
                    dbHelperGameData.close();
                    Intent intent = new Intent(this, ActivityGameOneOnOne.class);
                    intent.putExtra("isSaveGame", true);
                    startActivity(intent);
                }else{
                    cursor.close();
                    dbHelperGameData.close();
                    Intent intent = new Intent(this, ActivitySettingsGameOneOnOne.class);
                    startActivity(intent);
                }
                break;
            case R.id.activity_menu__bt_online_game:

                break;
        }
    }
}