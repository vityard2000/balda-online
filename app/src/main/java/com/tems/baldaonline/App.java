package com.tems.baldaonline;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.tems.baldaonline.models.DBHelper;
import com.tems.baldaonline.models.DBHelperGameData;
import com.tems.baldaonline.models.SettingsRepository;
import com.tems.baldaonline.models.TurnsRepository;
import com.tems.baldaonline.models.WordsRepository;

public class App extends Application {

    private static App instance;
    private SQLiteDatabase dataBaseWords;
    private SQLiteDatabase dataBaseGameData;
    private WordsRepository wordsRepository;
    private TurnsRepository turnsRepository;
    private SettingsRepository settingsRepository;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        DBHelper dbHelperWords = new DBHelper(this);
        DBHelperGameData dbHelperGameData = new DBHelperGameData(this);
        dataBaseWords = dbHelperWords.getReadableDatabase();
        dataBaseGameData = dbHelperGameData.getWritableDatabase();
    }

    public static App getInstance() {
        return instance;
    }

    public WordsRepository getWordsRepository() {
        if(wordsRepository == null){
            wordsRepository = new WordsRepository(dataBaseWords, dataBaseGameData);
        }
        return wordsRepository;
    }

    public TurnsRepository getTurnsRepository() {
        if(turnsRepository == null){
            turnsRepository = new TurnsRepository(dataBaseGameData);
        }
        return turnsRepository;
    }

    public SettingsRepository getSettingsRepository() {
        if(settingsRepository == null){
            settingsRepository = new SettingsRepository(this);
        }
        return settingsRepository;
    }
}
