package com.tems.baldaonline;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ActivityTwo extends AppCompatActivity implements View.OnClickListener {
    Button bt_to_activity_main;
    Button bt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two2);
        bt_to_activity_main = findViewById(R.id.activity_two__bt_to_activity_main);
        bt_to_activity_main.setOnClickListener(this);
        bt1 = findViewById(R.id.activity_two__bt1);
        bt1.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_two__bt_to_activity_main:
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.activity_two__bt1:
                Intent intent2 = new Intent();
                intent2.setAction(Intent.ACTION_VIEW);
                intent2.setData(Uri.parse("geo:59.92523887808012, 30.348731229507177"));
                startActivity(intent2);
        }
    }
}