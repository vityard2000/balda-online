package com.tems.baldaonline.View;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.tems.baldaonline.CustomAdapterSpinner;
import com.tems.baldaonline.R;


public class ActivitySetingsGameOneOnOne extends AppCompatActivity implements View.OnClickListener{
    private Spinner spinnerSizePole;
    private Spinner spinnerTimeForTurn;
    private EditText edtTxtFirstNameUser;
    private EditText edtTxtSecondNameUser;
    private EditText edtTxtStartWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setings_game_one_on_one);

        //
        spinnerSizePole = findViewById(R.id.activity_settings_game_one_on_one_spinner_size_pole);
        CustomAdapterSpinner adapterSizePole = new CustomAdapterSpinner(this, R.layout.spinner_item, R.id.spinner_item_tv, getResources().getStringArray(R.array.spinner_size_pole));
        spinnerSizePole.setAdapter(adapterSizePole);

        //
        spinnerTimeForTurn = findViewById(R.id.activity_settings_game_one_on_one__spinner_time_for_turn);
        CustomAdapterSpinner adapterTime = new CustomAdapterSpinner(this, R.layout.spinner_item, R.id.spinner_item_tv, getResources().getStringArray(R.array.spinner_time_ror_turn));
        spinnerTimeForTurn.setAdapter(adapterTime);


    }
    //
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_settings_game_one_on_one__bt_begin:
                Intent intent = new Intent(this, ActivityGameOneOnOne.class);
                intent.putExtra("name_first_user", edtTxtFirstNameUser.getText().toString());
                intent.putExtra("name_second_user", edtTxtSecondNameUser.getText().toString());
                intent.putExtra("time_for_turn", spinnerTimeForTurn.getSelectedItemPosition());
                intent.putExtra("start_word", edtTxtStartWord.getText().toString());
                startActivity(intent);
                break;
            case R.id.activity_settings_game_one_on_one__bt_random_word:
                break;

        }
    }
}