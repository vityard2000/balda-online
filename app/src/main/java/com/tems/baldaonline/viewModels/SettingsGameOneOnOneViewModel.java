package com.tems.baldaonline.viewModels;
import android.text.Editable;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.tems.baldaonline.App;
import com.tems.baldaonline.models.SettingsRepository;
import com.tems.baldaonline.models.WordsRepository;
import java.util.HashMap;
import java.util.Map;

public class SettingsGameOneOnOneViewModel extends ViewModel {
    private static final String myTag = "debugTag";
    private final SettingsRepository settingsRepository;
    private final WordsRepository wordsRepository;
    private MutableLiveData<String> startWord;
    private MutableLiveData<Integer> timeForTurn;
    private MutableLiveData<Integer> sizePole;
    private MutableLiveData<String> firstUserName;
    private MutableLiveData<String> secondUserName;
    private MutableLiveData<String> mascotColorOne;
    private MutableLiveData<String> mascotColorTwo;
    private String[] words = new String[5];

    public SettingsGameOneOnOneViewModel(){
        wordsRepository    = App.getInstance().getWordsRepository();
        settingsRepository = App.getInstance().getSettingsRepository();
        startWord      = new MutableLiveData<>();
        timeForTurn    = new MutableLiveData<>();
        sizePole       = new MutableLiveData<>();
        firstUserName  = new MutableLiveData<>();
        secondUserName = new MutableLiveData<>();
        mascotColorOne = new MutableLiveData<>();
        mascotColorTwo = new MutableLiveData<>();
        loadSettings();
    }

    public void loadSettings(){
        if(startWord.getValue() == null){
            Map<String, Object> settings = settingsRepository.getSettingsGameOneOnOne();
            startWord.          setValue(settings.get(SettingsRepository.START_WORD).toString());
            timeForTurn.        setValue((Integer) settings.get(SettingsRepository.TIME_FOR_TURN));
            sizePole.           setValue(startWord.getValue().length() - 3);
            firstUserName.      setValue(settings.get(SettingsRepository.FIRST_NAME_USER).toString());
            secondUserName.     setValue(settings.get(SettingsRepository.SECOND_NAME_USER).toString());
            mascotColorOne.     setValue(settings.get(SettingsRepository.MASCOT_COLOR_ONE).toString());
            mascotColorTwo.     setValue(settings.get(SettingsRepository.MASCOT_COLOR_TWO).toString());
            words[sizePole.getValue()] = startWord.getValue();
        }
    }
    public void saveSettings() {
        Map<String, Object> settings = new HashMap<>();
        settings.put(SettingsRepository.START_WORD, startWord.getValue());
        settings.put(SettingsRepository.TIME_FOR_TURN, timeForTurn.getValue());
        settings.put(SettingsRepository.FIRST_NAME_USER, firstUserName.getValue());
        settings.put(SettingsRepository.SECOND_NAME_USER, secondUserName.getValue());
        settingsRepository.saveSettings(settings);
    }
    public void refreshWordByLength(int length) {
        Log.d(myTag, String.valueOf(length != startWord.getValue().length()));
        if(length != startWord.getValue().length()){
            if(words[length-3]==null){
                refreshRandomWordByLength(length);
            }else {
                startWord.setValue(words[length-3]);
            }

        }
    }
    public void refreshRandomWordByLength(int length) {
            wordsRepository.getRandomWordsByLength(length, data -> {
                String word = data.getWord();
                words[word.length() - 3] = word;
                startWord.setValue(word);
            });
    }
    public void refreshTimeForTurn(int position) {
        timeForTurn.setValue(position);
    }
    ///getters

    public LiveData<String> getStartWord() {
        if (startWord == null) {
            startWord = new MutableLiveData<>();
            //init
        }
        return startWord;
    }
    public LiveData<String> getFirstUserName() {
        if (firstUserName == null) {
            firstUserName = new MutableLiveData<>();
            //init
        }
        return firstUserName;
    }
    public LiveData<String> getSecondUserName() {
        if (secondUserName == null) {
            secondUserName = new MutableLiveData<>();
            //init
        }
        return secondUserName;
    }
    public LiveData<String> getMascotColorOne() {
        if (mascotColorOne == null) {
            mascotColorOne = new MutableLiveData<>();
            //init
        }
        return mascotColorOne;
    }
    public LiveData<String> getMascotColorTwo() {
        if (mascotColorTwo == null) {
            mascotColorTwo = new MutableLiveData<>();
            //init
        }
        return mascotColorTwo;
    }
    public LiveData<Integer> getTimeForTurn() {
        if (timeForTurn == null) {
            timeForTurn = new MutableLiveData<>();
            //init
        }
        return timeForTurn;
    }


    public void refreshNames(String first, String second) {
        firstUserName.setValue(first);
        secondUserName.setValue(second);
    }

    public void refreshWord(String value) {
        if(!value.equals(startWord.toString()) && value.length() >= 3 && value.length() <=7) {
            startWord.setValue(value);
        }
    }
}
