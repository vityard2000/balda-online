package com.tems.baldaonline.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.tems.baldaonline.Game;
import com.tems.baldaonline.R;

public class ActivityGameOneOnOne extends AppCompatActivity implements View.OnClickListener {

    private static final String myTag    = "debugTag";
    private static final int FIRST_USER  = 1;
    private static final int SECOND_USER = 2;
    private static final int CHANGE_USER = 0;
    private SharedPreferences sPref;
    private int timeForTurn;
    private String startWord;
    private String firstUserName;
    private String secondUserName;
    private ImageButton ImgBtHome;
    private ImageButton ImgBtSkipTurn;
    private TextView tvFirstName;
    private TextView tvSecondName;
    private GridView gridViewGameMap;
    private Game game;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_one_on_one);
        loadSettings();
        initElements();
        setFocusNameUser(FIRST_USER);
        game = new Game(startWord, timeForTurn, this, gridViewGameMap);
        game.setOnTurnOverListener(new Game.OnTurnOverListener() {
            @Override
            public void onTurnOver() {
                setFocusNameUser(CHANGE_USER);
            }
        });
        game.startGame();

    }
    ///мое
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_game_one_on_one__img_bt_home:
                startActivity(new Intent(this, ActivityMenu.class));
                break;
            case R.id.activity_game_one_on_one__img_bt_skip_turn:
                game.skipTurn();
                break;
        }
    }

    private void setFocusNameUser(int mode) {
        switch (mode) {
            case 1:

                break;
            case 2:

                break;
            case 0:

        }
    }

    private void initElements() {
        tvFirstName     = findViewById(R.id.activity_game_one_on_one__tv_first_user_name);
        tvSecondName    = findViewById(R.id.activity_game_one_on_one__tv_second_user_name);
        ImgBtHome       = findViewById(R.id.activity_game_one_on_one__img_bt_home);
        gridViewGameMap = findViewById(R.id.activity_game_one_on_one__gv_game_map);
        ImgBtSkipTurn   = findViewById(R.id.activity_game_one_on_one__img_bt_skip_turn);
        tvFirstName.setText(firstUserName);
        tvSecondName.setText(secondUserName);
        ImgBtHome.setOnClickListener(this);
        ImgBtSkipTurn.setOnClickListener(this);
    }
    private void loadSettings() {
        sPref = getSharedPreferences("settingsGameOneOnOne", MODE_PRIVATE);
        firstUserName = sPref.getString("first_name_user","Первый игрок");
        secondUserName =sPref.getString("second_name_user","Второй игрок");
        timeForTurn = sPref.getInt("time_for_turn",2);
        startWord = sPref.getString("start_word", "слово");
    }
    //мое закончилось
}




