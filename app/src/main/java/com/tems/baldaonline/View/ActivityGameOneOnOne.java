package com.tems.baldaonline.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;


import com.tems.baldaonline.DialogKeyboard;
import com.tems.baldaonline.Game;
import com.tems.baldaonline.R;

public class ActivityGameOneOnOne extends AppCompatActivity implements View.OnClickListener {

    private static final String myTag    = "debugTag";
    private static final int FIRST_USER  = 0;
    private static final int SECOND_USER = 1;
    private static final int CHANGE_USER = -1;
    private SharedPreferences sPref;
    private int timeForTurn;
    private String startWord;
    private String firstUserName;
    private String secondUserName;
    private ImageButton ImgBtHome;
    private ImageButton ImgBtSkipTurn;
    private TextView tvFirstName;
    private TextView tvSecondName;
    private TextView tvPointsFirstUser;
    private TextView tvPointsSecondUser;
    private GridView gridViewGameMap;
    private Game game;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_one_on_one);
        loadSettings();
        initElements();
        setFocusNameUser(FIRST_USER);
        game = new Game(startWord, timeForTurn, this, gridViewGameMap);



        game.setOnClickEmptyCellListener(new Game.OnClickEmptyCellListener() {
            @Override
            public void onClickEmptyCell() {
                FragmentManager manager = getSupportFragmentManager();
                DialogKeyboard dialogkeyboard = new DialogKeyboard();
                dialogkeyboard.show(manager, "dialog");
            }
        });
        game.setOnEnterWordListener(new Game.OnEnterWordListener() {
            @Override
            public boolean onEnterWord(String word) {
                if (true) {//спросить оставить слово или нет
                    return true;
                } else return false;

            }
        });
        game.setOnAddWordInDictionary(new Game.OnAddWordInDictionaryListener() {
            public void onAddWordInDictionary(String word) {
                if(word != null){
                    setFocusNameUser(CHANGE_USER);
                    tvPointsFirstUser.setText(String.valueOf(game.getFirstGameUser().getCount()));
                    tvPointsSecondUser.setText(String.valueOf(game.getSecondGameUser().getCount()));
                }else{
                    //слова в словаре нет
                }

            }
        });
        game.startGame();

    }
    public void setLetter(Character c){
        game.setLetter(c);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_game_one_on_one__img_bt_home:
                startActivity(new Intent(this, ActivityMenu.class));
                break;
            case R.id.activity_game_one_on_one__img_bt_skip_turn:

                setFocusNameUser(CHANGE_USER);
                game.skipTurn();

                break;
        }
    }

    private void setFocusNameUser(int mode) {
        switch (mode) {
            case 0:
                tvFirstName.setBackgroundResource(R.drawable.style_focus_name);
                tvSecondName.setBackground(null);
                break;
            case 1:
                tvSecondName.setBackgroundResource(R.drawable.style_focus_name);
                tvFirstName.setBackground(null);
                break;
            case -1:

                if(game.getCurrentUser() == FIRST_USER){
                    setFocusNameUser(SECOND_USER);
                }else{
                    setFocusNameUser(FIRST_USER);
                }

                break;
        }
    }

    private void initElements() {
        tvFirstName     = findViewById(R.id.activity_game_one_on_one__tv_first_user_name);
        tvSecondName    = findViewById(R.id.activity_game_one_on_one__tv_second_user_name);
        ImgBtHome       = findViewById(R.id.activity_game_one_on_one__img_bt_home);
        gridViewGameMap = findViewById(R.id.activity_game_one_on_one__gv_game_map);
        ImgBtSkipTurn   = findViewById(R.id.activity_game_one_on_one__img_bt_skip_turn);
        tvPointsFirstUser = findViewById(R.id.activity_game_one_on_one__tv_count_first_user);
        tvPointsSecondUser = findViewById(R.id.activity_game_one_on_one__tv_count_second_user);
        tvPointsFirstUser.setText(String.valueOf(0));
        tvPointsSecondUser.setText(String.valueOf(0));
        tvSecondName.setText(secondUserName);
        tvFirstName.setText(firstUserName);
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


}




