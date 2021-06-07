package com.tems.baldaonline.views;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.tems.baldaonline.App;
import com.tems.baldaonline.models.DBHelperGameData;
import com.tems.baldaonline.R;
import com.tems.baldaonline.models.TurnsRepository;

public class ActivityMenu extends AppCompatActivity implements View.OnClickListener {
    private Button buttonGameOneOnOne;
    private Button buttonOnlineGame;
    private SharedPreferences sPref;
    private String color;
    private ImageView body;
    private static final String myTag    = "debugTag";

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        buttonGameOneOnOne = findViewById(R.id.activity_menu__bt_joint_game);
        buttonOnlineGame   = findViewById(R.id.activity_menu__bt_online_game);
        body               = findViewById(R.id.activity_menu__img_v_online_mascot_backg);
        buttonGameOneOnOne.setOnClickListener(this);
        buttonOnlineGame.setOnClickListener(this);
        sPref = getSharedPreferences("settingsGameOneOnOne", MODE_PRIVATE);
        color = sPref.getString("mascot_color_online", getResources().getString(R.color.mascot_online));
        body.setColorFilter(Color.parseColor(color), android.graphics.PorterDuff.Mode.SRC_IN);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_menu__bt_joint_game:
                if(App.getInstance().getTurnsRepository().isEmpty()){
                    Intent intent = new Intent(this, ActivityGameOneOnOne.class);
                    intent.putExtra("isSaveGame", true);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(this, ActivitySettingsGameOneOnOne.class);
                    startActivity(intent);
                }
                break;
            case R.id.activity_menu__bt_online_game:
                Intent intent = new Intent(this, ActivityOnlineGames.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }
}