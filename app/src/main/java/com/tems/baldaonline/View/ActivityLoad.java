package com.tems.baldaonline.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.tems.baldaonline.R;

public class ActivityLoad extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        Intent intent = new Intent(this, ActivityMenu.class);
        startActivity(intent);
        finish();
    }
}
