package com.tems.baldaonline.models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tems.baldaonline.R;
import com.tems.baldaonline.domain.Mascot;
import com.tems.baldaonline.domain.Outfit;
import com.tems.baldaonline.domain.Settings;

public class SettingsRepository implements RepositorySettings {

    private SharedPreferences sPref;
    private Context context;
    public static final String FIRST_NAME_USER = "first_name_user";
    public static final String SECOND_NAME_USER = "second_name_user";
    public static final String TIME_FOR_TURN = "time_for_turn";
    public static final String START_WORD = "start_word";
    public static final String MASCOT_COLOR_ONE = "mascot_color_1";
    public static final String MASCOT_COLOR_TWO = "mascot_color_2";
    MutableLiveData<Object> ObservableData;


    public SettingsRepository(Context context){
        this.context = context;
        sPref = context.getSharedPreferences("settingsGameOneOnOne", Context.MODE_PRIVATE);
    }
    public LiveData<Object> getObservableData() {
        if (ObservableData == null) {
            ObservableData = new MutableLiveData<>();
        }
        return ObservableData;
    }
    @Override
    public void getSettingsGameOneOnOne(OnSettingsReadyListener onSettingsReadyListener){

        Settings settings = new Settings();
        settings.setFirstNameUser(sPref.getString(FIRST_NAME_USER,"Первый игрок"));
        settings.setSecondNameUser(sPref.getString(SECOND_NAME_USER,"Второй игрок"));
        settings.setTimeForTurn(sPref.getInt(TIME_FOR_TURN,2));
        settings.setStartWord(sPref.getString(START_WORD, "cлово"));
        onSettingsReadyListener.onDataReady(settings);
    }

    @SuppressLint("ResourceType")
    @Override
    public void getMascot(int id, OnMascotReadyListener onMascotReadyListener){
        //1 - first local mascot
        //2 - second local mascot
        //3 - online mascot
        Outfit outfit = new Outfit();
        String strColor = null;
        int color;
        outfit.setzIndex0(sPref.getInt("mascot_"+ id +"_index_0", 0));
        outfit.setzIndex1(sPref.getInt("mascot_"+ id +"_index_1", 0));
        outfit.setzIndex2(sPref.getInt("mascot_"+ id +"_index_2", 0));
        outfit.setzIndex3(sPref.getInt("mascot_"+ id +"_index_3", 0));
        switch (id){
            case 1:
                strColor = sPref.getString(MASCOT_COLOR_ONE,context.getResources().getString(R.color.mascot_1));
                break;
            case 2:
                strColor = sPref.getString(MASCOT_COLOR_TWO,context.getResources().getString(R.color.mascot_2));
                break;
        }
        if(strColor != null){
            color = Color.parseColor(strColor);
            onMascotReadyListener.onDataReady(new Mascot(id, color, outfit));
        } else {
            onMascotReadyListener.onDataReady(null);
        }
    }

    @Override
    public void saveMascot(Mascot mascot) {
        ObservableData.setValue(new Object());
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("mascot_color_" + mascot.getId() , mascot.getStringColor());
        ed.putInt("mascot_"+ mascot.getId() +"_index_0", mascot.getOutfit().getzIndex0());
        ed.putInt("mascot_"+ mascot.getId() +"_index_1", mascot.getOutfit().getzIndex1());
        ed.putInt("mascot_"+ mascot.getId() +"_index_2", mascot.getOutfit().getzIndex2());
        ed.putInt("mascot_"+ mascot.getId() +"_index_3", mascot.getOutfit().getzIndex3());
        ed.apply();
    }

    @Override
    public void saveSettingsGameOneOnOne(Settings settings) {
        ObservableData.setValue(new Object());
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("first_name_user",  settings.getFirstNameUser());
        ed.putString("second_name_user", settings.getSecondNameUser());
        ed.putString("start_word",       settings.getStartWord());
        ed.putInt("time_for_turn",       settings.getTimeForTurn());
        ed.apply();
    }
    public interface OnMascotReadyListener{
        void onDataReady(Mascot mascot);
    }
    public interface OnSettingsReadyListener{
        void onDataReady(Settings settings);
    }
}
