package com.tems.baldaonline.domain;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;

import com.tems.baldaonline.App;
import com.tems.baldaonline.R;
import com.tems.baldaonline.adapters.AdapterCell;
import com.tems.baldaonline.dialogs.DialogFinish;
import com.tems.baldaonline.dialogs.DialogInfo;
import com.tems.baldaonline.dialogs.DialogYesNo;

import com.tems.baldaonline.models.TurnsRepository;
import com.tems.baldaonline.models.WordsRepository;
import com.tems.baldaonline.views.ActivityGameOneOnOne;
import com.tems.baldaonline.views.ActivityMenu;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static final String myTag = "debugTag";
    private final Activity context;
    private int currentUser;
    private static final int FIRST_USER  = 0;
    private static final int SECOND_USER = 1;
    private final String startWord;
    private int rowsCount;
    private int numDownEmptyCell = -1;
    private int numClickEmptyCell = -1;
    private int numEnterLetter =-1;
    private ArrayList<Character> pole;
    private List<Integer> numsCells;
    private final GridView gridViewGameMap;
    private int lastNumCell = -1;
    private FragmentManager manager;
    OnAddWordInDictionaryListener onAddWordInDictionary;
    OnEnterWordListener onEnterWordListener;
    OnClickEmptyCellListener onClickEmptyCellListener;
    TurnsRepository turnsRepository;
    WordsRepository wordsRepository;
    private GameUser firstUser;
    private GameUser secondUser;
    private boolean isSaveGame;
    private int countTurns = 0;

    public Game(Activity context, GridView gridViewGameMap, String startWord, boolean isSaveGame) {
        this.context = context;
        this.gridViewGameMap = gridViewGameMap;
        this.startWord = startWord.toUpperCase();
        this.isSaveGame = isSaveGame;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void startGame() {
        //?????????????????????????? ????
        manager = ((ActivityGameOneOnOne) context).getSupportFragmentManager();
        wordsRepository = App.getInstance().getWordsRepository();
        turnsRepository = App.getInstance().getTurnsRepository();
        // ?????????????????????????? ??????????????????????????
        firstUser = new GameUser();
        secondUser = new GameUser();
        //???????????????????? ???????? ?? ?????????????????? ????????????
        rowsCount = startWord.length();
        numsCells = new ArrayList<>();

        pole = new ArrayList<>();
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < rowsCount; j++) {
                if (i == Math.round(rowsCount / 2.)-1) {
                    pole.add(startWord.charAt(j));
                    continue;
                }
                pole.add(null);
            }
        }
        //???????????????????? ???????? ??????????
        //???????????????? ?????????????????????? ???????? ????????????????
        if(isSaveGame){
            turnsRepository.getAll(data -> {
                data.forEach(turn -> {
                    currentUser = turn.getUser();
                    if(currentUser == 0){
                        firstUser.setWord(turn.getWord());
                    }else{
                        secondUser.setWord(turn.getWord());
                    }
                    pole.set(turn.getNumCel(), turn.getLetter());
                    countTurns++;
                });
                currentUser = (currentUser == FIRST_USER)? SECOND_USER: FIRST_USER;// ?????????? ????????
                new DialogYesNo(() -> {
                    turnsRepository.removeAll();
                    ((ActivityGameOneOnOne) context).createNewGame();
                }, null, "???????????????????? ?????????").show(manager, "tag");

            });

        }else{
            turnsRepository.removeAll();
            currentUser = FIRST_USER;//?????????????????????????? ???????????????? ????????????????????????
        }
        initGridViewGameMap();

    }
    @SuppressLint("ClickableViewAccessibility")
    private void initGridViewGameMap() {
        gridViewGameMap.setNumColumns(rowsCount);
        gridViewGameMap.setOnTouchListener((v, event) -> {

            int numCell = getNumCell(event.getX(), event.getY());

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    if(numCell != -1 && pole.get(numCell) == null && isBesideCell(numCell)) { //?????????? ???????????????? ???? ???????????? cell
                        numDownEmptyCell = numCell; // ?????????????????? ?????????? ???????????? cell, ???? ?????????????? ???????????????? ??????????
                        gridViewGameMap.getChildAt(numCell).setBackgroundResource(R.color.cell_press);
                    }
                    break;
                case MotionEvent.ACTION_MOVE: // ?????????? ??????????????????????
                    if(numCell != -1&& numDownEmptyCell ==-1 && pole.get(numCell)!=null&&numEnterLetter!=-1)// ?????????????? ?????? ???????????? ?? ?????????????????????? ?????????????????? ??????????
                    {
                        setFocusCell(numCell);
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:

                    if(numEnterLetter == numCell && numsCells.size()==1){
                        numClickEmptyCell = numCell;
                        gridViewGameMap.getChildAt(numCell).setBackgroundResource(R.color.cell_press);
                        onClickEmptyCellListener.onClickEmptyCell();
                    }
                    if(numDownEmptyCell !=-1){ // ???????? ???????????????? ?????????? ???? ???????????? cell
                        if(numCell == numDownEmptyCell ) { // ???????? ?????????????? ?????????? ?? ?????? ???? ???????????? cell, ???? ?????????????? ?????????? ????????????????
                            numClickEmptyCell = numCell;
                            onClickEmptyCellListener.onClickEmptyCell();
                        } else {
                            gridViewGameMap.getChildAt(numDownEmptyCell).setBackgroundResource(R.color.white);
                        }
                    } else if (lastNumCell!=-1&&numsCells.size()!=1&&lastNumCell == numCell) {//???????? ???????????????? ?????????? ???? cell, ?????????????? ???????? ?????????????????? ???????????????????? ?? ??????????
                        boolean flag = false; // ???????? ?????? ???????????????? ???????? ???? ?????????????????? ?????????? ?? ?????????????????? ??????????

                        StringBuilder stringBuilder = new StringBuilder();

                        for (int i : numsCells) stringBuilder.append(pole.get(i)); // ?????????????????? ??????????
                        String word = stringBuilder.toString();
                        for (int i : numsCells)  flag = (i == numEnterLetter) || flag; //???????????????? ???????? ???? ?????????????????? ?????????? ?? ??????????

                        if(flag){

                            if(!firstUser.getWords().contains(word) && !secondUser.getWords().contains(word)&& !startWord.equalsIgnoreCase(word)){// ???????????????? ???? ????????????????????
                                new DialogYesNo(null , () -> {
                                    wordsRepository.getWord(word, data -> {
                                        if (data.getWord() != null) {  //???????? ???? ?????????? ?? ??????????????// ?????????????????? ?????????????????? ??????????///////////////////////////////////////////////////////////////////////////////////
                                            if (currentUser == FIRST_USER) {
                                                firstUser.setWord(word);
                                            } else {
                                                secondUser.setWord(word);
                                            }
                                            turnsRepository.insert(new Turn(word, pole.get(numEnterLetter), numEnterLetter, currentUser));
                                            onAddWordInDictionary.onAddWordInDictionary(); //??????????????, ?????? ?????????? ???????? ?? ????
                                            currentUser = (currentUser == FIRST_USER) ? SECOND_USER : FIRST_USER; // ???????????? ????????????????????????
                                            numEnterLetter = -1; // ???????????????? ?????????? ?????????????? ????????????
                                            countTurns++;

                                            if (countTurns == rowsCount * (rowsCount - 1)) {
                                                if (firstUser.getCount() != secondUser.getCount()) {
                                                    boolean winUser = firstUser.getCount() < secondUser.getCount();
                                                    new DialogFinish(() -> new DialogYesNo(null, () -> context.startActivity(new Intent(context, ActivityMenu.class)), "?????????? ?? ?????????").show(manager, "chase_dialog"), winUser).show(manager, "finish_dialog");
                                                } else {
                                                    Log.d(myTag, "??????????");
                                                }
                                            }
                                        } else {
                                            new DialogYesNo(null, () -> {
                                                if (currentUser == FIRST_USER) {
                                                    firstUser.setWord(word);
                                                } else {
                                                    secondUser.setWord(word);
                                                }

                                                wordsRepository.insert(word);
                                                turnsRepository.insert(new Turn(word, pole.get(numEnterLetter), numEnterLetter, currentUser));
                                                onAddWordInDictionary.onAddWordInDictionary(); //??????????????, ?????? ?????????? ???????? ?? ????
                                                currentUser = (currentUser == FIRST_USER) ? SECOND_USER : FIRST_USER; // ???????????? ????????????????????????
                                                numEnterLetter = -1; // ???????????????? ?????????? ?????????????? ????????????
                                            }, "C???????? " + word + " ?????? ?? ??????????????, ???????????? ?????????????????").show(manager, "tag");
                                        }
                                    });
                                },  "???????????? ?????????? "+word+"?").show(manager, "tag");
                            }else{
                                if (startWord.equalsIgnoreCase(word)) new DialogInfo( "???????????????? ?????????? ?????????????? ????????????").show(manager, "tag");
                                else new DialogInfo("C???????? "+word+" ?????? ??????????????" ).show(manager, "tag");
                            }

                        }
                    }
                    removeFocusCells();
                    break;
            }
            return true;
        });
        gridViewGameMap.setAdapter(new AdapterCell(context, pole));
    }

    //private boolean isWordInDirectory(String word){
    //    boolean flag = false;
    //    String selection = "word = ?";
    //    String[] selectionArg = {word.toLowerCase()};
    //    Cursor cursor = databaseWords.query(DBHelperWords.TABLE_DICTIONARY, null, selection, selectionArg, null, null , null);
    //    Cursor cursorGameData = databaseGameData.query(DBHelperGameData.TABLE_WORDS, null, selection, selectionArg, null, null , null);
    //    if(cursor.moveToFirst() || cursorGameData.moveToFirst()){
    //        flag = true;
    //    }
    //    cursor.close();
    //    cursorGameData.close();
    //    return flag;
    //}

    private boolean isBesideCell(int cell) { //???????? ???? ?????????? cell ?? ????????????
        int[] besideCells = {
                cell - rowsCount,
                cell + rowsCount,
                (cell+1)%rowsCount == 0? -1: cell+1,
                cell%rowsCount == 0? -1: cell-1
        };
        for(int i = 0; i<4; i++){
            int besideCell = besideCells[i];
            if(besideCell >= 0 && besideCell < rowsCount*rowsCount&&pole.get(besideCell)!=null&&besideCell!=numEnterLetter){
                return true;
            }
        }
        return false;
    }

    private int getNumCell(float x, float y) {
        float offset = context.getResources().getDimension(R.dimen.sizeMarginGameMap);                                                                                 // ???????????????????? ?????????? cell
        float widthCell = gridViewGameMap.getChildAt(0).getWidth() + offset;
        int xCell = (int) (x / widthCell);
        int yCell = (int) (y / widthCell);
        if((x >= 0 && x < rowsCount*widthCell) && (y >= 0 && y < rowsCount*widthCell)) return  xCell + yCell * rowsCount;
        else return -1;
    }
    // ?????????????? ?????????? ???? ???????? ??????????. ?????????????? ?? game ?????????????????? ?????????? ?? ?????????????? ???????????????????? ???????????? ????????????
    private void removeFocusCells() {
        numDownEmptyCell = -1;
        lastNumCell = -1;
        for (int i:numsCells) gridViewGameMap.getChildAt(i).setBackgroundResource(R.color.white);
        numsCells.clear();
    }

    private void setFocusCell(int numCell) {
        if (numCell != lastNumCell)                                                                    // ???????? ?????????? ???? ?????????? cell
        {
            if (numsCells.contains(numCell)) {                                                              // ???????? ???? ?????? ???????????????? ?????? cell
                List<Integer> sub = numsCells.subList(numsCells.indexOf(numCell)+1, numsCells.size());      // sub ???????? ?????????????? ?? ???????????????????????????? cell ?? ???? ?????????? ??????????
                for(int i: sub)gridViewGameMap.getChildAt(i).setBackgroundResource(R.color.white);          // ?????????? ?????????????????? ?????????????? ?? ???????????????????????????? cell ???? ???????????????????? ?????????????????????? cell
                sub.clear();                                                                                //?????????????????? sub ????????
                lastNumCell = numCell;
            } else if(
                               numCell == lastNumCell + rowsCount                                 //?????????? ???? cell ?????????? ?? lastCell
                            || numCell == lastNumCell - rowsCount
                            || numCell == ((lastNumCell + 1)%rowsCount == 0? -1: lastNumCell+1)
                            || numCell == (lastNumCell%rowsCount == 0? -1: lastNumCell-1)
                            || numsCells.isEmpty())                                                //???????? ???? ?????? ???????????? ???? ????????????????
            {
                numsCells.add(numCell);
                lastNumCell = numCell;
                gridViewGameMap.getChildAt(numCell).setBackgroundResource(R.color.cell_press);         //???????????????? cell

            }
        }
    }

    public void skipTurn(){
        if(numEnterLetter!=-1){
            ((TextView)gridViewGameMap.getChildAt(numEnterLetter)).setText(null);
            pole.set(numEnterLetter, null);
        }
        currentUser = (currentUser == FIRST_USER)?SECOND_USER:FIRST_USER;


    }

    public void destroyGame() {

    }

    public int getCurrentUser() {
        return currentUser;
    }

    public void stopGame() {

    }

    public void setLetter(Character letter) {

        TextView textView = ((TextView) gridViewGameMap.getChildAt(numClickEmptyCell));

        if(letter != null) {
            if(numEnterLetter!=-1){
                ((TextView)gridViewGameMap.getChildAt(numEnterLetter)).setText(null);
                pole.set(numEnterLetter, null);
            }
            numEnterLetter = numClickEmptyCell;

            pole.set(numClickEmptyCell, letter);
            textView.setText(letter.toString());
        }
        textView.setBackgroundResource(R.color.white);
        numClickEmptyCell =-1;
    }

    public GameUser getFirstUser() {
        return firstUser;
    }

    public GameUser getSecondUser() {
        return secondUser;
    }

    //  --- set listeners ---

    public void setOnClickEmptyCellListener(OnClickEmptyCellListener onClickEmptyCellListener){
        this.onClickEmptyCellListener = onClickEmptyCellListener;
    }

    public void setOnEnterWordListener(OnEnterWordListener onEnterWordListener){
        this.onEnterWordListener = onEnterWordListener;
    }

    public void setOnAddWordInDictionary(OnAddWordInDictionaryListener onAddWordInDictionary){
        this.onAddWordInDictionary = onAddWordInDictionary;
    }

    // --- interfaces ---

    public interface OnClickEmptyCellListener{
        void onClickEmptyCell();
    }

    public  interface OnEnterWordListener{
        boolean onEnterWord(String word);
    }

    public interface OnAddWordInDictionaryListener {
        void onAddWordInDictionary();
    }
}