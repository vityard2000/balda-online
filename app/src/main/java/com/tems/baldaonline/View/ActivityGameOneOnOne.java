package com.tems.baldaonline.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.tems.baldaonline.DBHelperGameData;
import com.tems.baldaonline.DialogKeyboard;
import com.tems.baldaonline.DialogYesNo;
import com.tems.baldaonline.Game;
import com.tems.baldaonline.ListAdapter;
import com.tems.baldaonline.R;

public class ActivityGameOneOnOne extends AppCompatActivity implements View.OnClickListener{

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

        dialogkeyboard = new DialogKeyboard(new DialogKeyboard.OnClickKyeListener() {
            @Override
            public void onClickKye(Character c) {
                game.setLetter(c);
            }
        });

        game = new Game(this,gridViewGameMap, startWord, getIntent().getBooleanExtra("isSaveGame", false));

        game.setOnClickEmptyCellListener(new Game.OnClickEmptyCellListener() {
            @Override
            public void onClickEmptyCell() {
                dialogkeyboard.show(manager, "dialog");
            }
        });

        game.setOnAddWordInDictionary(new Game.OnAddWordInDictionaryListener() {
            public void onAddWordInDictionary() {
                    setFocusNameUser(CHANGE_USER);
                    tvPointsFirstUser.setText(String.valueOf(game.getFirstUser().getCount()));
                    tvPointsSecondUser.setText(String.valueOf(game.getSecondUser().getCount()));
                    lvAdapter.notifyDataSetChanged();
                    timer.start();
            }
        });


        game.startGame();
        setFocusNameUser(game.getCurrentUser());
        tvPointsFirstUser.setText(String.valueOf(game.getFirstUser().getCount()));
        tvPointsSecondUser.setText(String.valueOf(game.getSecondUser().getCount()));
        TimerInit();
        timer.start();

        lvAdapter = new ListAdapter(this, game.getFirstUser(), game.getSecondUser());
        lvWords.setAdapter(lvAdapter);

    }

    @Override
    protected void onDestroy() {
        game.destroyGame();
        super.onDestroy();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_game_one_on_one__img_bt_home:

                Intent intent= new Intent(this, ActivityMenu.class);
                new DialogYesNo(null, new DialogYesNo.OnClickListener() {
                    @Override
                    public void onClick() {
                        game.destroyGame();
                        startActivity(intent);
                    }
                }, "Выйти в главное меню?").show(manager, "tagp");

                break;
            case R.id.activity_game_one_on_one__img_bt_skip_turn:
                new DialogYesNo(null, new DialogYesNo.OnClickListener() {
                    @Override
                    public void onClick() {
                        setFocusNameUser(CHANGE_USER);
                        game.skipTurn();
                        timer.start();
                    }
                }, "Пропустить ход?").show(manager, "tagp");
                break;
        }
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


            public void onTick(long millisUntilFinished) {
                long s = (millisUntilFinished/1000)%60;
                long m = (millisUntilFinished/1000)/60;
                tvTimeForTurn.setText(String.format("%02d:%02d", m, s));
            }

            public void onFinish() {
                setFocusNameUser(CHANGE_USER);
                game.skipTurn();
                timer.start();
            }
        };


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

        tvSecondName.setText(secondUserName);
        tvFirstName.setText(firstUserName);
        ImgBtHome.setOnClickListener(this);
        ImgBtSkipTurn.setOnClickListener(this);

        manager = getSupportFragmentManager();



    }
    private void loadSettings() {
        sPref = getSharedPreferences("settingsGameOneOnOne", MODE_PRIVATE);
        firstUserName = sPref.getString("first_name_user","Первый игрок");
        secondUserName =sPref.getString("second_name_user","Второй игрок");
        timeForTurn = sPref.getInt("time_for_turn",2);
        startWord = sPref.getString("start_word", "слово");
    }

    public void createNewGame() {
        game.destroyGame();
        Intent intent = new Intent(this, ActivitySettingsGameOneOnOne.class);
        startActivity(intent);
    }
}




