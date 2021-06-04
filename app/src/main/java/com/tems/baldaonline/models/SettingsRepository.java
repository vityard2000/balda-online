package com.tems.baldaonline.models;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.tems.baldaonline.R;

import java.util.HashMap;
import java.util.Map;

public class SettingsRepository {

    private SharedPreferences sPref;
    private Context context;
    public static final String FIRST_NAME_USER = "first_name_user";
    public static final String SECOND_NAME_USER = "second_name_user";
    public static final String TIME_FOR_TURN = "time_for_turn";
    public static final String START_WORD = "start_word";
    public static final String MASCOT_COLOR_ONE = "mascot_color_one";
    public static final String MASCOT_COLOR_TWO = "mascot_color_two";

    public SettingsRepository(Context context){
        this.context = context;
        sPref = context.getSharedPreferences("settingsGameOneOnOne", Context.MODE_PRIVATE);
    }
    @SuppressLint("ResourceType")
    public Map<String, Object> getSettingsGameOneOnOne(){
        Map<String, Object> settings = new HashMap<>();
        settings.put(FIRST_NAME_USER, sPref.getString(FIRST_NAME_USER,"Первый игрок"));
        settings.put(SECOND_NAME_USER, sPref.getString(SECOND_NAME_USER,"Второй игрок"));
        settings.put(TIME_FOR_TURN, sPref.getInt(TIME_FOR_TURN,2));
        settings.put(START_WORD, sPref.getString(START_WORD, "cлово"));
        settings.put(MASCOT_COLOR_ONE,  sPref.getString(MASCOT_COLOR_ONE , context.getResources().getString(R.color.mascot_one)));
        settings.put(MASCOT_COLOR_TWO, sPref.getString(MASCOT_COLOR_TWO , context.getResources().getString(R.color.mascot_two)));
        return settings;
    }

    public void saveSettings(Map<String, Object> settings) {
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("first_name_user",(String) settings.get(FIRST_NAME_USER));
        ed.putString("second_name_user",(String)settings.get(SECOND_NAME_USER));
        ed.putString("start_word",(String)settings.get(START_WORD));
        ed.putInt("time_for_turn",(Integer) settings.get(TIME_FOR_TURN));
        ed.apply();
    }
}
