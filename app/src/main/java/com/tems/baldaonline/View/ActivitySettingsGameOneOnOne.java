package com.tems.baldaonline.View;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tems.baldaonline.CustomAdapterSpinner;
import com.tems.baldaonline.R;


public class ActivitySettingsGameOneOnOne extends AppCompatActivity implements View.OnClickListener{
    private static final String myTag = "debugTag";
    private Spinner spinnerSizePole;
    private Spinner spinnerTimeForTurn;
    private Button btBegin;
    private Button btRandom;
    private EditText edtTxtFirstNameUser;
    private EditText edtTxtSecondNameUser;
    private EditText edtTxtStartWord;
    private SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setings_game_one_on_one);
        initElements();
        loadSettings();
        spinnerSizePole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edtTxtStartWord.setText(getRandomWord(position + 3));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initElements() {
        edtTxtFirstNameUser  = findViewById(R.id.activity_settings_game_one_on_one__edt_txt_first_name_user);
        edtTxtSecondNameUser = findViewById(R.id.activity_settings_game_one_on_one__edt_txt_second_name_user);
        edtTxtStartWord      = findViewById(R.id.activity_settings_game_one_on_one__edt_txt_start_word);
        btBegin              = findViewById(R.id.activity_settings_game_one_on_one__bt_begin);
        btRandom             = findViewById(R.id.activity_settings_game_one_on_one__bt_random_word);
        spinnerSizePole      = findViewById(R.id.activity_settings_game_one_on_one_spinner_size_pole);
        spinnerTimeForTurn   = findViewById(R.id.activity_settings_game_one_on_one__spinner_time_for_turn);

        btBegin.setOnClickListener(this);
        btRandom.setOnClickListener(this);

        CustomAdapterSpinner adapterTime = new CustomAdapterSpinner(this, R.layout.spinner_item, R.id.spinner_item_tv, getResources().getStringArray(R.array.spinner_time_ror_turn));
        spinnerTimeForTurn.setAdapter(adapterTime);

        CustomAdapterSpinner adapterSizePole = new CustomAdapterSpinner(this, R.layout.spinner_item, R.id.spinner_item_tv, getResources().getStringArray(R.array.spinner_size_pole));
        spinnerSizePole.setAdapter(adapterSizePole);

    }

    //
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_settings_game_one_on_one__bt_begin:
                if (isValidate()){
                    saveSettings();
                    startActivity(new Intent(this, ActivityGameOneOnOne.class));
                }

                break;
            case R.id.activity_settings_game_one_on_one__bt_random_word:
                edtTxtStartWord.setText(getRandomWord(spinnerSizePole.getSelectedItemPosition() + 3));
                break;

        }
    }

    private boolean isValidate() {
        return true;
    }

    private void loadSettings() {
        sPref = getSharedPreferences("settingsGameOneOnOne", MODE_PRIVATE);
        edtTxtFirstNameUser.setText(sPref.getString("first_name_user","Первый игрок"));
        edtTxtSecondNameUser.setText(sPref.getString("second_name_user","Второй игрок"));
        spinnerTimeForTurn.setSelection(sPref.getInt("time_for_turn",2));
        String startWord = sPref.getString("start_word", getRandomWord(5));
        edtTxtStartWord.setText(startWord);
        spinnerSizePole.setSelection(startWord.length()-3);
        Log.d(myTag, "загрузилось");
    }

    private String getRandomWord(int sizeWord) {
        switch (sizeWord){
            case 3:return "сло";
            case 4:return "слов";
            case 5:return "слово";
            case 6:return "словоо";
            case 7:return "словооо";
            default: return "default";
        }
    }

    private void saveSettings() {
        sPref = getSharedPreferences("settingsGameOneOnOne", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("first_name_user",edtTxtFirstNameUser.getText().toString());
        ed.putString("second_name_user",edtTxtSecondNameUser.getText().toString());
        ed.putString("start_word",edtTxtStartWord.getText().toString());
        ed.putInt("time_for_turn",spinnerTimeForTurn.getSelectedItemPosition());
        ed.apply();
        Log.d(myTag, "сохранилось");
    }
}
