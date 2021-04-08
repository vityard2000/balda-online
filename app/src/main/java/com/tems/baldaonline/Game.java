package com.tems.baldaonline;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.GridView;
import android.widget.TextView;
import androidx.fragment.app.FragmentManager;
import com.tems.baldaonline.View.ActivityGameOneOnOne;
import com.tems.baldaonline.View.ActivityMenu;

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
    SQLiteDatabase databaseGameData;
    SQLiteDatabase databaseWords;
    DBHelper dbHelper;
    DBHelperGameData dbHelperGameData;
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

    public void startGame() {
        //инициализация бд
        manager = ((ActivityGameOneOnOne) context).getSupportFragmentManager();
        dbHelperGameData = new DBHelperGameData(context);
        databaseGameData = dbHelperGameData.getWritableDatabase();
        dbHelper = new DBHelper(context);
        databaseWords = dbHelper.getReadableDatabase();
        // инициализация пользователей
        firstUser = new GameUser();
        secondUser = new GameUser();
        //заполнение поля с начальным словом
        rowsCount = startWord.length();
        numsCells = new ArrayList<>();

        pole = new ArrayList<Character>();
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < rowsCount; j++) {
                if (i == Math.round(rowsCount / 2.)-1) {
                    pole.add(startWord.charAt(j));
                    continue;
                }
                pole.add(null);
            }
        }
        //заполнение поля конец
        //загрузка сохраненной игры еслинадо
        if(isSaveGame){
            Cursor cursor = databaseGameData.query(DBHelperGameData.TABLE_TURNS, null, null, null, null, null , null);
            cursor.moveToFirst();
            int wordIndex = cursor.getColumnIndex(DBHelperGameData.KEY_WORD);
            int letterIndex = cursor.getColumnIndex(DBHelperGameData.KEY_LETTER);
            int numCellIndex = cursor.getColumnIndex(DBHelperGameData.KEY_NUM_CELL);
            int userIndex = cursor.getColumnIndex(DBHelperGameData.KEY_USER);

            do {
                currentUser = cursor.getInt(userIndex);
                if(currentUser == 0){
                    firstUser.setWord(cursor.getString(wordIndex));
                }else{
                    secondUser.setWord(cursor.getString(wordIndex));
                }
                pole.set(cursor.getInt(numCellIndex), cursor.getString(letterIndex).charAt(0));
                countTurns++;
            }while(cursor.moveToNext());

            currentUser = (currentUser == FIRST_USER)? SECOND_USER: FIRST_USER;// смена хода
            cursor.close();

            new DialogYesNo(new DialogYesNo.OnClickListener() {
                @Override
                public void onClick() {
                    databaseGameData.delete(DBHelperGameData.TABLE_TURNS, null, null);
                    ((ActivityGameOneOnOne) context).createNewGame();
                }
            }, null, "Продолжить игру?").show(manager, "tag");

        }else{
            databaseGameData.delete(DBHelperGameData.TABLE_TURNS, null, null);
            currentUser = FIRST_USER;//устанавливаем текущего пользователя
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
                    if(numCell != -1 && pole.get(numCell) == null && isBesideCell(numCell)) { //палец опустили на пустую cell
                        numDownEmptyCell = numCell; // сохраняем номер пустой cell, на которую опустили палец
                        gridViewGameMap.getChildAt(numCell).setBackgroundResource(R.color.cell_press);
                    }
                    break;
                case MotionEvent.ACTION_MOVE: // палец передвигают
                    if(numCell != -1&& numDownEmptyCell ==-1 && pole.get(numCell)!=null&&numEnterLetter!=-1)// условия для начала и продолжения веделения слова
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
                    if(numDownEmptyCell !=-1){ // если опустили палец на пустую cell
                        if(numCell == numDownEmptyCell ) { // если подняли палец с той же пустой cell, на которую палец опустили
                            numClickEmptyCell = numCell;
                            onClickEmptyCellListener.onClickEmptyCell();
                        } else {
                            gridViewGameMap.getChildAt(numDownEmptyCell).setBackgroundResource(R.color.white);
                        }
                    } else if (lastNumCell!=-1&&numsCells.size()!=1&&lastNumCell == numCell) {//если опустили палец на cell, которая была последняя выделенная в слове
                        boolean flag = false; // флаг для проверки есть ли введенная буква в введенном слове

                        StringBuilder stringBuilder = new StringBuilder();

                        for (int i : numsCells) stringBuilder.append(pole.get(i)); // формируем слово
                        String word = stringBuilder.toString();
                        for (int i : numsCells)  flag = (i == numEnterLetter) || flag; //проверка есть ли введенная буква в слове

                        if(flag){

                            if(!firstUser.getWords().contains(word) && !secondUser.getWords().contains(word)&& !startWord.equalsIgnoreCase(word)){// проверка на повторение
                                new DialogYesNo(null , new DialogYesNo.OnClickListener() {
                                    @Override
                                    public void onClick() {
                                        if(isWordInDirectory(word)) {  //есть ли слово в словаре// сохраняем введенное слово///////////////////////////////////////////////////////////////////////////////////
                                            if (currentUser == FIRST_USER) { firstUser.setWord(word);
                                            } else {secondUser.setWord(word);}
                                            databaseGameData.insert(DBHelperGameData.TABLE_TURNS , null , DBHelperGameData.createContentValuesTurns(word, pole.get(numEnterLetter).toString(), numEnterLetter , currentUser));
                                            onAddWordInDictionary.onAddWordInDictionary(); //говорим, что слово есть в бд
                                            currentUser = (currentUser == FIRST_USER)?SECOND_USER:FIRST_USER; // меняем пользователя
                                            numEnterLetter= -1; // обнуляем номер нажатой ячейки
                                            countTurns++;

                                            if(countTurns == rowsCount*(rowsCount - 1)){
                                                if(firstUser.getCount() != secondUser.getCount()){
                                                    boolean winUser = firstUser.getCount() < secondUser.getCount();
                                                    new DialogFinish(new DialogFinish.OnCloseListener() {
                                                        @Override
                                                        public void onClose() {
                                                            new DialogYesNo(null, new DialogYesNo.OnClickListener() {
                                                                @Override
                                                                public void onClick() {
                                                                    context.startActivity(new Intent(context, ActivityMenu.class));

                                                                }
                                                            }, "Выйти в меню?").show(manager, "chase_dialog");
                                                        }
                                                    }, winUser).show(manager, "finish_dialog");
                                                }else {
                                                    Log.d(myTag, "ничья");
                                                }
                                            }
                                        } else{
                                            new DialogYesNo(null , new DialogYesNo.OnClickListener() {
                                                @Override
                                                public void onClick() {
                                                    if (currentUser == FIRST_USER) { firstUser.setWord(word);
                                                    } else {secondUser.setWord(word);}
                                                    databaseGameData.insert(DBHelperGameData.TABLE_WORDS , null , DBHelperGameData.createContentValuesWords(word.toLowerCase()));
                                                    databaseGameData.insert(DBHelperGameData.TABLE_TURNS , null , DBHelperGameData.createContentValuesTurns(word, pole.get(numEnterLetter).toString(), numEnterLetter , currentUser));
                                                    onAddWordInDictionary.onAddWordInDictionary(); //говорим, что слово есть в бд
                                                    currentUser = (currentUser == FIRST_USER)?SECOND_USER:FIRST_USER; // меняем пользователя
                                                    numEnterLetter= -1; // обнуляем номер нажатой ячейки
                                                }
                                            },  "Cлова "+word+" нет в словаре, хотите добавить?").show(manager, "tag");
                                        }
                                    }
                                },  "Ввести слово "+word+"?").show(manager, "tag");
                            }else{
                                if (startWord.equalsIgnoreCase(word)) new DialogInfo( "Начально слово вводить нельзя").show(manager, "tag");
                                else new DialogInfo("Cлово "+word+" уже вводили" ).show(manager, "tag");
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

    private boolean isWordInDirectory(String word){
        boolean flag = false;
        String selection = "word = ?";
        String[] selectionArg = {word.toLowerCase()};
        Cursor cursor = databaseWords.query(DBHelper.TABLE_DICTIONARY, null, selection, selectionArg, null, null , null);
        Cursor cursorGameData = databaseGameData.query(DBHelperGameData.TABLE_WORDS, null, selection, selectionArg, null, null , null);
        if(cursor.moveToFirst() || cursorGameData.moveToFirst()){
            flag = true;
        }
        cursor.close();
        cursorGameData.close();
        return flag;
    }

    private boolean isBesideCell(int cell) { //есть ли рядом cell с буквой
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
        float offset = context.getResources().getDimension(R.dimen.sizeMarginGameMap);                                                                                 // расстояние между cell
        float widthCell = gridViewGameMap.getChildAt(0).getWidth() + offset;
        int xCell = (int) (x / widthCell);
        int yCell = (int) (y / widthCell);
        if((x >= 0 && x < rowsCount*widthCell) && (y >= 0 && y < rowsCount*widthCell)) return  xCell + yCell * rowsCount;
        else return -1;
    }
    // снимаем фокус со всех ячеек. убираем в game последний фокус и наличае выделенной пустой ячейки
    private void removeFocusCells() {
        numDownEmptyCell = -1;
        lastNumCell = -1;
        for (int i:numsCells) gridViewGameMap.getChildAt(i).setBackgroundResource(R.color.white);
        numsCells.clear();
    }

    private void setFocusCell(int numCell) {
        if (numCell != lastNumCell)                                                                    // если палец на новой cell
        {
            if (numsCells.contains(numCell)) {                                                              // если мы уже виделили эту cell
                List<Integer> sub = numsCells.subList(numsCells.indexOf(numCell)+1, numsCells.size());      // sub лист начиная с повторяющегося cell и до конца листа
                for(int i: sub)gridViewGameMap.getChildAt(i).setBackgroundResource(R.color.white);          // снять выделение начиная с повторяющегося cell до последнего выделенного cell
                sub.clear();                                                                                //отчистить sub лист
                lastNumCell = numCell;
            } else if(
                               numCell == lastNumCell + rowsCount                                 //лежит ли cell рядом с lastCell
                            || numCell == lastNumCell - rowsCount
                            || numCell == ((lastNumCell + 1)%rowsCount == 0? -1: lastNumCell+1)
                            || numCell == (lastNumCell%rowsCount == 0? -1: lastNumCell-1)
                            || numsCells.isEmpty())                                                //если мы еще ничего не выделили
            {
                numsCells.add(numCell);
                lastNumCell = numCell;
                gridViewGameMap.getChildAt(numCell).setBackgroundResource(R.color.cell_press);         //выделяем cell

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
        dbHelperGameData.close();
        dbHelper.close();
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