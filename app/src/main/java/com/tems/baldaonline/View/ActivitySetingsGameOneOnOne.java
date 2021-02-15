package com.tems.baldaonline.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Spinner;

import com.tems.baldaonline.CustomAdapterSpinner;
import com.tems.baldaonline.R;


public class ActivitySetingsGameOneOnOne extends AppCompatActivity {
    private Spinner spinnerSizePole;
    private Spinner spinnerTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setings_game_one_on_one);


        spinnerSizePole = findViewById(R.id.spinner_size_pole);
        CustomAdapterSpinner adapterSizePole = new CustomAdapterSpinner(this, R.layout.spinner_item, R.id.spinner_item_tv, getResources().getStringArray(R.array.spinner_size_pole));
        spinnerSizePole.setAdapter(adapterSizePole);


        spinnerTime = findViewById(R.id.spinner_time);
        CustomAdapterSpinner adapterTime = new CustomAdapterSpinner(this, R.layout.spinner_item, R.id.spinner_item_tv, getResources().getStringArray(R.array.spinner_time));
        spinnerTime.setAdapter(adapterTime);

    }
}