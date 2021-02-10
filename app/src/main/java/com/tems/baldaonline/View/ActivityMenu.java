package com.tems.baldaonline.View;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tems.baldaonline.R;
import com.tems.baldaonline.View.ActivityGameOneOnOne;

public class ActivityMenu extends AppCompatActivity implements View.OnClickListener {
    Button buttonGameOneOnOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        buttonGameOneOnOne = findViewById(R.id.activity_menu__bt_joint_game);
        buttonGameOneOnOne.setOnClickListener(this);
    }

    @SuppressLint({"NonConstantResourceId", "ShowToast"})
    @Override
    public void onClick(View v) {
        Toast.makeText(this, "вот так вот", Toast.LENGTH_SHORT);

        switch (v.getId()) {
            case R.id.activity_menu__bt_joint_game:
                Intent intent = new Intent(this, ActivityGameOneOnOne.class);
                startActivity(intent);
                break;
        }
    }
}