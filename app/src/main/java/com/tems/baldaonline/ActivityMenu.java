package com.tems.baldaonline;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class ActivityMenu extends AppCompatActivity {
    Button bt_online_game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        bt_online_game = findViewById(R.id.activity_menu__bt_online_game);

    }
}