package com.tems.baldaonline.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;


import com.tems.baldaonline.DialogKeyboard;
import com.tems.baldaonline.Game;
import com.tems.baldaonline.ListAdapter;
import com.tems.baldaonline.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private TextView tvTimeForTurn;
    private GridView gridViewGameMap;
    private Game game;
    private View secondButtonClock;
    private View firstButtonClock;
    private CountDownTimer timer;
    private FragmentManager manager;
    private DialogKeyboard dialogkeyboard;
    private ListView lvWords;
    private ListAdapter lvAdapter;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_one_on_one);
        loadSettings();
        initElements();

        // массивы данных
        final String ATTRIBUTE_NAME_FIRST_WORD = "first_word";
        final String ATTRIBUTE_NAME_SECOND_WORD = "second_word";
        final String ATTRIBUTE_NAME_FIRST_COUNT = "first_count";
        final String ATTRIBUTE_NAME_SECOND_COUNT = "second_count";


        game = new Game(startWord, timeForTurn, this, gridViewGameMap);

        game.setOnClickEmptyCellListener(new Game.OnClickEmptyCellListener() {
            @Override
            public void onClickEmptyCell() {
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
                    lvAdapter.notifyDataSetChanged();
                    timer.start();
                    Log.d(myTag, "все ок");
                }else{
                    //слова в словаре нет
                }

            }
        });
        game.startGame();
        TimerInit();
        timer.start();

        lvAdapter = new ListAdapter(this, game.getFirstGameUser().getWords(), game.getSecondGameUser().getWords());
        lvWords.setAdapter(lvAdapter);

    }

    private void TimerInit() {

        long time=10000;

        switch (timeForTurn){
            case 0:
                time = 30000;
                break;
            case 1:
                time = 60000;
                break;
            case 2:
                time = 120000;
                break;
            case 3:
                time = 300000;
                break;
            case 4:
                time = 600000;
                break;
            case 5:
                time = 1200000;
                break;
            case 6:
                time = 1800000;
                break;

        }

        timer = new CountDownTimer(time, 1000) {

            //Здесь обновляем текст счетчика обратного отсчета с каждой секундой
            public void onTick(long millisUntilFinished) {
                long s = (millisUntilFinished/1000)%60;
                long m = (millisUntilFinished/1000)/60;
                tvTimeForTurn.setText(String.format("%02d:%02d", m, s));
            }
            //Задаем действия после завершения отсчета (высвечиваем надпись "Бабах!"):
            public void onFinish() {
                setFocusNameUser(CHANGE_USER);
                game.skipTurn();
                timer.start();
            }
        };


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
                timer.start();

                break;
        }
    }

    private void setFocusNameUser(int mode) {
        switch (mode) {
            case 0:
                tvFirstName.setBackgroundResource(R.drawable.style_focus_name);
                tvSecondName.setBackgroundResource(R.drawable.style_solid_corners_dark);
                firstButtonClock.setVisibility(View.INVISIBLE);
                secondButtonClock.setVisibility(View.VISIBLE);
                break;
            case 1:
                tvSecondName.setBackgroundResource(R.drawable.style_focus_name);
                tvFirstName.setBackgroundResource(R.drawable.style_solid_corners_dark);
                firstButtonClock.setVisibility(View.VISIBLE);
                secondButtonClock.setVisibility(View.INVISIBLE);
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
        tvFirstName        = findViewById(R.id.activity_game_one_on_one__tv_first_user_name);
        tvSecondName       = findViewById(R.id.activity_game_one_on_one__tv_second_user_name);
        ImgBtHome          = findViewById(R.id.activity_game_one_on_one__img_bt_home);
        gridViewGameMap    = findViewById(R.id.activity_game_one_on_one__gv_game_map);
        ImgBtSkipTurn      = findViewById(R.id.activity_game_one_on_one__img_bt_skip_turn);
        tvPointsFirstUser  = findViewById(R.id.activity_game_one_on_one__tv_count_first_user);
        tvPointsSecondUser = findViewById(R.id.activity_game_one_on_one__tv_count_second_user);
        tvTimeForTurn      = findViewById(R.id.activity_game_one_on_one__tv_time_for_turn);
        secondButtonClock  = findViewById(R.id.activity_game_one_on_one__v_second_button_clock);
        firstButtonClock   = findViewById(R.id.activity_game_one_on_one__v_first_button_clock);
        lvWords            = findViewById(R.id.activity_game_one_on_one__lv_words);
        tvPointsFirstUser.setText(String.valueOf(0));
        tvPointsSecondUser.setText(String.valueOf(0));
        tvSecondName.setText(secondUserName);
        tvFirstName.setText(firstUserName);
        ImgBtHome.setOnClickListener(this);
        ImgBtSkipTurn.setOnClickListener(this);
        manager = getSupportFragmentManager();
        dialogkeyboard = new DialogKeyboard();
        setFocusNameUser(FIRST_USER);
    }
    private void loadSettings() {
        sPref = getSharedPreferences("settingsGameOneOnOne", MODE_PRIVATE);
        firstUserName = sPref.getString("first_name_user","Первый игрок");
        secondUserName =sPref.getString("second_name_user","Второй игрок");
        timeForTurn = sPref.getInt("time_for_turn",2);
        startWord = sPref.getString("start_word", "слово");
    }


}




