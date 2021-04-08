package com.tems.baldaonline.View;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import com.tems.baldaonline.AdapterSpinnerCustom;
import com.tems.baldaonline.DBHelper;
import com.tems.baldaonline.R;


public class ActivitySettingsGameOneOnOne extends AppCompatActivity implements View.OnClickListener{
    private int timeForTurn;
    private String startWord;
    private String firstUserName;
    private String secondUserName;
    private String colorBtOne;
    private String colorBtTwo;
    SQLiteDatabase databaseWords;
    DBHelper dbHelper;
    private static final String myTag = "debugTag";
    private Spinner spinnerSizePole;
    private Spinner spinnerTimeForTurn;
    private Button btBegin;
    private Button btRandom;
    private Button btMascotOne;
    private Button btMascotTwo;
    private EditText edtTxtFirstNameUser;
    private EditText edtTxtSecondNameUser;
    private EditText edtTxtStartWord;
    private SharedPreferences sPref;
    private ImageView backgroundBtOne;
    private ImageView backgroundBtTwo;
    private boolean flag = false;

    @Override
    protected void onResume() {
        super.onResume();
        loadSettings();
        backgroundBtOne.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(colorBtOne)));
        backgroundBtTwo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(colorBtTwo)));
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setings_game_one_on_one);
        loadSettings();
        initElements();
        spinnerSizePole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(flag) edtTxtStartWord.setText(getRandomWord(position + 3));
                else flag = true;
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
        btMascotOne          = findViewById(R.id.activity_settings_game_one_on_one__bt_mascot_one);
        btMascotTwo          = findViewById(R.id.activity_settings_game_one_on_one__bt_mascot_two);
        btBegin              = findViewById(R.id.activity_settings_game_one_on_one__bt_begin);
        btRandom             = findViewById(R.id.activity_settings_game_one_on_one__bt_random_word);
        spinnerSizePole      = findViewById(R.id.activity_settings_game_one_on_one_spinner_size_pole);
        spinnerTimeForTurn   = findViewById(R.id.activity_settings_game_one_on_one__spinner_time_for_turn);
        backgroundBtOne      = findViewById(R.id.activity_settings_game_one_on_one__img_v_background_bt_one);
        backgroundBtTwo      = findViewById(R.id.activity_settings_game_one_on_one__img_v_background_bt_two);
        backgroundBtOne.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(colorBtOne)));
        backgroundBtTwo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(colorBtTwo)));
        btBegin.setOnClickListener(this);
        btRandom.setOnClickListener(this);
        btMascotOne.setOnClickListener(this);
        btMascotTwo.setOnClickListener(this);

        AdapterSpinnerCustom adapterTime = new AdapterSpinnerCustom(this, R.layout.spinner_item, R.id.spinner_item_tv, getResources().getStringArray(R.array.spinner_time_ror_turn));
        spinnerTimeForTurn.setAdapter(adapterTime);
        AdapterSpinnerCustom adapterSizePole = new AdapterSpinnerCustom(this, R.layout.spinner_item, R.id.spinner_item_tv, getResources().getStringArray(R.array.spinner_size_pole));
        spinnerSizePole.setAdapter(adapterSizePole);

        spinnerSizePole.setSelection((startWord.length()<=7)? startWord.length()-3: 2);

        edtTxtFirstNameUser.setText(firstUserName);
        edtTxtSecondNameUser.setText(secondUserName);
        edtTxtStartWord.setText(startWord);
        spinnerTimeForTurn.setSelection(timeForTurn);


    }

    //
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_settings_game_one_on_one__bt_mascot_one:
                Intent intentOne = new Intent(this, ActivityEditor.class)
                    .putExtra("num_mascot", false);
                startActivity(intentOne);
                break;
            case R.id.activity_settings_game_one_on_one__bt_mascot_two:
                Intent intentTwo = new Intent(this, ActivityEditor.class)
                    .putExtra("num_mascot", true);
                startActivity(intentTwo);
                break;
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

    @SuppressLint("ResourceType")
    private void loadSettings() {
        sPref = getSharedPreferences("settingsGameOneOnOne", MODE_PRIVATE);
        firstUserName  = sPref.getString("first_name_user","Первый игрок");
        secondUserName = sPref.getString("second_name_user","Второй игрок");
        timeForTurn    = sPref.getInt("time_for_turn",2);
        startWord      = sPref.getString("start_word", getRandomWord(5));
        colorBtOne     = sPref.getString("mascot_color_one" , getResources().getString(R.color.mascot_one));
        colorBtTwo     = sPref.getString("mascot_color_two" , getResources().getString(R.color.mascot_two));
    }

    private String getRandomWord(int sizeWord) {
        dbHelper = new DBHelper(this);
        databaseWords = dbHelper.getReadableDatabase();
        String selection = "LENGTH(word) = " + String.valueOf(sizeWord);
        Cursor cursor = databaseWords.query(DBHelper.TABLE_DICTIONARY, null, selection, null, null, null , null);
        if (cursor.moveToFirst()){
            cursor.moveToPosition((int)( Math.random() * cursor.getCount()));
            String word = cursor.getString(cursor.getColumnIndex("word"));
            cursor.close();
            dbHelper.close();
            return word;
        }
        switch (sizeWord){
            case 3:return "кот";
            case 4:return "пень";
            case 5:return "слово";
            case 6:return "золото";
            case 7:return "семечко";
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

    }
}
