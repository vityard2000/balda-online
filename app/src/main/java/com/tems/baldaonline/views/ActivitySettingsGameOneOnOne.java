package com.tems.baldaonline.views;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.tems.baldaonline.App;
import com.tems.baldaonline.adapters.AdapterSpinnerCustom;
import com.tems.baldaonline.R;
import com.tems.baldaonline.viewModels.SettingsGameOneOnOneViewModel;


public class ActivitySettingsGameOneOnOne extends AppCompatActivity implements View.OnClickListener{
    private Spinner spinnerSizePole;
    private Spinner spinnerTimeForTurn;
    private Button btBegin;
    private Button btRandom;
    private Button btMascotOne;
    private Button btMascotTwo;
    private EditText edtTxtFirstNameUser;
    private EditText edtTxtSecondNameUser;
    private EditText edtTxtStartWord;
    private ImageView backgroundBtOne;
    private ImageView backgroundBtTwo;
    private SettingsGameOneOnOneViewModel model;
    private static final String myTag = "debugTag";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setings_game_one_on_one);

        //init
        model = ViewModelProviders.of(this).get(SettingsGameOneOnOneViewModel.class);

        edtTxtFirstNameUser  = findViewById(R.id.activity_settings_game_one_on_one__edt_txt_first_name_user);
        edtTxtSecondNameUser = findViewById(R.id.activity_settings_game_one_on_one__edt_txt_second_name_user);
        edtTxtStartWord      = findViewById(R.id.activity_settings_game_one_on_one__edt_txt_start_word);
        btMascotOne          = findViewById(R.id.activity_settings_game_one_on_one__bt_mascot_one);
        btMascotTwo          = findViewById(R.id.activity_settings_game_one_on_one__bt_mascot_two);
        btBegin              = findViewById(R.id.activity_settings_game_one_on_one__bt_begin);
        btRandom             = findViewById(R.id.activity_settings_game_one_on_one__bt_random_word);
        spinnerSizePole      = findViewById(R.id.activity_settings_game_one_on_one_spinner_size_pole);
        spinnerTimeForTurn   = findViewById(R.id.activity_settings_game_one_on_one__spinner_time_for_turn);
        backgroundBtOne      = findViewById(R.id.activity_settings_game_one_on_one__img_v_background_bt_one);
        backgroundBtTwo      = findViewById(R.id.activity_settings_game_one_on_one__img_v_background_bt_two);

        //setOnClickListeners
        btBegin.setOnClickListener(this);
        btRandom.setOnClickListener(v -> model.refreshRandomWordByLength(spinnerSizePole.getSelectedItemPosition() + 3));
        btMascotOne.setOnClickListener(this);
        btMascotTwo.setOnClickListener(this);

        //init adapters
        AdapterSpinnerCustom adapterTime = new AdapterSpinnerCustom(this, R.layout.spinner_item, R.id.spinner_item_tv, getResources().getStringArray(R.array.spinner_time_ror_turn));
        spinnerTimeForTurn.setAdapter(adapterTime);
        AdapterSpinnerCustom adapterSizePole = new AdapterSpinnerCustom(this, R.layout.spinner_item, R.id.spinner_item_tv, getResources().getStringArray(R.array.spinner_size_pole));
        spinnerSizePole.setAdapter(adapterSizePole);

        //subscriptions
        App.getInstance().getSettingsRepository().getObservableData().observe(this, (o) -> model.dataChange());
        model.getStartWord().observe(this, (value) -> {
            spinnerSizePole.setSelection(value.length()-3);
            if(!value.equals(edtTxtStartWord.getText().toString()) ) {
                edtTxtStartWord.setText(value);
            }
        });
        model.getFirstUserName().observe(this, (value) -> edtTxtFirstNameUser.setText(value));
        model.getSecondUserName().observe(this, (value) -> edtTxtSecondNameUser.setText(value));
        model.getTimeForTurn().observe(this, (value) -> spinnerTimeForTurn.setSelection(value));
        model.getMascotColorOne().observe(this, integer -> backgroundBtOne.setBackgroundTintList(ColorStateList.valueOf(integer)));
        model.getMascotColorTwo().observe(this, integer -> backgroundBtTwo.setBackgroundTintList(ColorStateList.valueOf(integer)));

        //callBacks
        spinnerTimeForTurn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model.refreshTimeForTurn(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerSizePole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model.refreshWordByLength(position + 3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        edtTxtStartWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(myTag, s.toString());
                model.refreshWord(s.toString());
            }
        });
        edtTxtFirstNameUser.setOnFocusChangeListener((v, hasFocus) -> model.refreshFirstUserName(((EditText)v).getText().toString()));
        edtTxtSecondNameUser.setOnFocusChangeListener((v, hasFocus) -> model.refreshSecondUserName(((EditText)v).getText().toString()));


    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_settings_game_one_on_one__bt_mascot_one:
                Intent intentOne = new Intent(this, ActivityEditor.class).putExtra("num_mascot", false);
                startActivity(intentOne);
                break;
            case R.id.activity_settings_game_one_on_one__bt_mascot_two:
                Intent intentTwo = new Intent(this, ActivityEditor.class).putExtra("num_mascot", true);
                startActivity(intentTwo);
                break;
            case R.id.activity_settings_game_one_on_one__bt_begin:
                model.saveSettings();
                startActivity(new Intent(ActivitySettingsGameOneOnOne.this, ActivityGameOneOnOne.class));
                break;
        }
    }

}
