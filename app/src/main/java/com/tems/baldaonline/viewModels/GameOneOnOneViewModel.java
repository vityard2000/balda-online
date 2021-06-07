package com.tems.baldaonline.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tems.baldaonline.App;
import com.tems.baldaonline.R;
import com.tems.baldaonline.domain.Mascot;
import com.tems.baldaonline.domain.Turn;
import com.tems.baldaonline.models.SettingsRepository;
import com.tems.baldaonline.models.WordsRepository;

import java.util.List;
import java.util.Map;

public class GameOneOnOneViewModel extends ViewModel {

    private static final String myTag = "debugTag";
    private final SettingsRepository settingsRepository;
    private final WordsRepository wordsRepository;
    private String startWord;
    private String firstUserName;
    private String secondUserName;
    private int sizePole;
    private int timeForTurn;
    private Mascot mascotOne;
    private Mascot mascotTwo;
    private MutableLiveData<Integer> time;
    private MutableLiveData<Integer> countFirstUser;
    private MutableLiveData<Integer> countSecondUser;
    private MutableLiveData<Boolean> isFirstPlayerTurn;
    private MutableLiveData<Boolean> isGameOver;
    private MutableLiveData<List<Turn>> turns;


    public GameOneOnOneViewModel(){
        wordsRepository    = App.getInstance().getWordsRepository();
        settingsRepository = App.getInstance().getSettingsRepository();
        time  = new MutableLiveData<>();
        isFirstPlayerTurn  = new MutableLiveData<>();
        countFirstUser = new MutableLiveData<>();
        countSecondUser  = new MutableLiveData<>();
        isGameOver  = new MutableLiveData<>();
        turns  = new MutableLiveData<>();
        loadSettings();
    }
    private void loadSettings() {
        settingsRepository.getSettingsGameOneOnOne(settings -> {
            startWord = settings.getStartWord();
            timeForTurn = settings.getTimeForTurn();
            sizePole = startWord.length();
            firstUserName = settings.getFirstNameUser();
            secondUserName = settings.getSecondNameUser();
        });
        settingsRepository.getMascot(1, mascot -> mascotOne = mascot);
        settingsRepository.getMascot(2, mascot -> mascotTwo = mascot);
    }
}
