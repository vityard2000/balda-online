package com.tems.baldaonline.viewModels;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.tems.baldaonline.App;
import com.tems.baldaonline.domain.Settings;
import com.tems.baldaonline.models.SettingsRepository;
import com.tems.baldaonline.models.WordsRepository;

public class SettingsGameOneOnOneViewModel extends ViewModel {

    private static final String myTag = "debugTag";
    private final SettingsRepository settingsRepository;
    private final WordsRepository wordsRepository;
    private MutableLiveData<String> startWord;
    private MutableLiveData<Integer> timeForTurn;
    private MutableLiveData<Integer> sizePole;
    private MutableLiveData<String> firstUserName;
    private MutableLiveData<String> secondUserName;
    private MutableLiveData<Integer> mascotColorOne;
    private MutableLiveData<Integer> mascotColorTwo;
    private String[] words = new String[5];

    public SettingsGameOneOnOneViewModel(){
        wordsRepository    = App.getInstance().getWordsRepository();
        settingsRepository = App.getInstance().getSettingsRepository();
        startWord      = new MutableLiveData<>();
        timeForTurn    = new MutableLiveData<>();
        sizePole       = new MutableLiveData<>();
        firstUserName  = new MutableLiveData<>();
        secondUserName = new MutableLiveData<>();
        mascotColorOne    = new MutableLiveData<>();
        mascotColorTwo    = new MutableLiveData<>();
        loadSettings();
    }

    public void dataChange(){
        settingsRepository.getMascot(1, mascot -> mascotColorOne.setValue(mascot.getColor()));
        settingsRepository.getMascot(2, mascot -> mascotColorTwo.setValue(mascot.getColor()));
    }

    public void loadSettings(){
        settingsRepository.getSettingsGameOneOnOne(settings -> {
            startWord.          setValue(settings.getStartWord());
            timeForTurn.        setValue(settings.getTimeForTurn());
            sizePole.           setValue(startWord.getValue().length());
            firstUserName.      setValue(settings.getFirstNameUser());
            secondUserName.     setValue(settings.getSecondNameUser());
            words[sizePole.getValue()-3] = startWord.getValue();
        });
        settingsRepository.getMascot(1, mascot -> mascotColorOne.setValue(mascot.getColor()));
        settingsRepository.getMascot(2, mascot -> mascotColorTwo.setValue(mascot.getColor()));
    }
    public void saveSettings() {
        Settings settings = new Settings();
        settings.setStartWord(startWord.getValue());
        settings.setTimeForTurn(timeForTurn.getValue());
        settings.setFirstNameUser(firstUserName.getValue());
        settings.setSecondNameUser(secondUserName.getValue());
        settingsRepository.saveSettingsGameOneOnOne(settings);
    }
    public void refreshWordByLength(int length) {
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

    public LiveData<Integer> getMascotColorOne() {
        if (mascotColorOne == null) {
            mascotColorOne = new MutableLiveData<>();
            //init
        }
        return mascotColorOne;
    }
    public LiveData<Integer> getMascotColorTwo() {
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

    //refresh
    public void refreshSecondUserName(String name) {secondUserName.setValue(name);}

    public void refreshWord(String value) {
        if(!value.equals(startWord.toString()) && value.length() >= 3 && value.length() <=7) {
            startWord.setValue(value);
        }
    }

    public void refreshFirstUserName(String name) {firstUserName.setValue(name);}
}
