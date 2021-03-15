package com.tems.baldaonline;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.MotionEvent;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Game {
    private static final String myTag = "debugTag";
    private final Activity context;
    private int currentUser;
    private static final int FIRST_USER  = 0;
    private static final int SECOND_USER = 1;
    private final String startWord;
    private GameUser firstGameUser;
    private GameUser secondGameUser;
    private int rowsCount;
    private int numDownEmptyCell = -1;
    private int numClickEmptyCell = -1;
    private int numEnterLetter =-1;
    private List<Cell> cells;
    private List<Integer> numsCells;
    private final GridView gridViewGameMap;
    private int lastNumCell = -1;
    OnAddWordInDictionaryListener onAddWordInDictionary;
    OnEnterWordListener onEnterWordListener;
    OnClickEmptyCellListener onClickEmptyCellListener;
    private static final String WORD = "word";
    private static final String LETTER = "letter";
    private static final String NUM_CELL = "num_cell";
    private static final String USER = "user";
    ArrayList<Map<String, Object>> data;

    public Game(String startWord, int timeForTurn, Activity context, GridView gridViewGameMap) {
        this.context = context;
        this.gridViewGameMap = gridViewGameMap;
        this.startWord = startWord.toUpperCase();
    }
    @SuppressLint("ClickableViewAccessibility")
    public void startGame() {

        currentUser = FIRST_USER;//устанавливаем текущего пользователя
        firstGameUser = new GameUser(); //инициализируем игровый пользователей
        secondGameUser = new GameUser();
        data = new ArrayList<>();

        WordCell start_word = new WordCell();
       // for (int i = 0; i < startWord.length(); i ++) start_word.add(startWord.charAt(i)); // формируем слово

       // data.add(createMapData(startWord, null, null , null));
        rowsCount = startWord.length();
        gridViewGameMap.setNumColumns(rowsCount);
        numsCells = new LinkedList<>();
        //заполнение поля
        cells = new ArrayList<>();
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < rowsCount; j++) {
                if (i == Math.round(rowsCount / 2.)-1) {
                    cells.add(new Cell(startWord.charAt(j)));
                    continue;
                }
                cells.add(new Cell());
            }
        }
        //заполнение поля конец
        gridViewGameMap.setOnTouchListener((v, event) -> {
            int numCell = getNumCell(event.getX(), event.getY());
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    if(numCell != -1 && cells.get(numCell).getLetter() == null && isBesideCell(numCell)) { //палец опустили на пустую cell
                        numDownEmptyCell = numCell; // сохраняем номер пустой cell, на которую опустили палец
                        gridViewGameMap.getChildAt(numCell).setBackgroundResource(R.color.cell_press);
                    }
                    break;
                case MotionEvent.ACTION_MOVE: // палец передвигают
                    if(numCell != -1&& numDownEmptyCell ==-1 && cells.get(numCell).getLetter()!=null&&numEnterLetter!=-1)// условия для начала и продолжения веделения слова
                    {
                         setFocusCell(numCell);
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    if(numDownEmptyCell !=-1){ // если опустили палец на пустую cell
                        if(numCell == numDownEmptyCell) { // если подняли палец с той же пустой cell, на которую палец опустили
                            numClickEmptyCell = numDownEmptyCell;
                            onClickEmptyCellListener.onClickEmptyCell();
                        } else {
                            gridViewGameMap.getChildAt(numDownEmptyCell).setBackgroundResource(R.color.white);
                        }
                    } else if (lastNumCell!=-1&&numsCells.size()!=1&&lastNumCell == numCell) {//если опустили палец на cell, которая была последняя выделенная в слове
                            boolean flag = false; // флаг для проверки есть ли введенная буква в введенном слове

                            WordCell word = new WordCell();
                            for (int i : numsCells) word.add(cells.get(i)); // формируем слово

                            for (int i : numsCells)  flag = (i == numEnterLetter) || flag; //проверка есть ли введенная буква в слове

                            if(flag){
                                numEnterLetter= -1; // обнуляем номер нажатой ячейки
                                if(onEnterWordListener.onEnterWord(word.toString())) { // спрашиваем нравится ли слово нашему активити
                                    if(true) {  //есть ли слово в словаре
                                        if (currentUser == FIRST_USER) {// сохраняем введенное слово///////////////////////////////////////////////////////////////////////////////////
                                            firstGameUser.setWord(word);
                                            data.add(createMapData(word, null, null , currentUser));

                                        } else {
                                            secondGameUser.setWord(word);
                                        }
                                        onAddWordInDictionary.onAddWordInDictionary(word.toString()); //говорим, что слово есть в бд
                                        currentUser = (currentUser == FIRST_USER)?SECOND_USER:FIRST_USER; // меняем пользователя
                                    } else{
                                        onAddWordInDictionary.onAddWordInDictionary(null); // говорим, что слова нет в бд
                                    }
                                }
                            }
                        }
                    removeFocusCells();
                    break;
            }
            return true;
        });
        gridViewGameMap.setAdapter(new CellAdapter(context, cells));
    }

    private Map<String, Object> createMapData(WordCell word, Character letter, Integer num_cell, Integer user) {
        Map<String, Object> turn = new HashMap<>(); //
        turn.put(WORD, word);
        turn.put(LETTER, letter);
        turn.put(NUM_CELL, num_cell);
        turn.put(USER, user);
        return  turn;
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
            if(besideCell >= 0 && besideCell < rowsCount*rowsCount&&cells.get(besideCell).getLetter()!=null&&besideCell!=numEnterLetter){
                return true;
            }
        }
        return false;
    }

    private int getNumCell(float x, float y) {
        float offset = context.getResources().getDimension(R.dimen.sizePaddingGameMap);                                                                                 // расстояние между cell
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
            cells.get(numEnterLetter).setLetter(null);
        }
        currentUser = (currentUser == FIRST_USER)?SECOND_USER:FIRST_USER;


    }

    public void destroyGame() {
    }

    public GameUser getFirstGameUser() {
        return firstGameUser;
    }

    public GameUser getSecondGameUser() {
        return secondGameUser;
    }

    public int getCurrentUser() {
        return currentUser;
    }

    public void stopGame() {
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

    public void setLetter(Character letter) {

        Map<String, Object> turn = new HashMap<>();
        turn.put(WORD, startWord);
        turn.put(LETTER, null);
        turn.put(NUM_CELL, null);
        turn.put(USER, null);
        data.add(turn);

        TextView textView = ((TextView) gridViewGameMap.getChildAt(numClickEmptyCell));

        if(letter != null) {
            if(numEnterLetter!=-1){
                ((TextView)gridViewGameMap.getChildAt(numEnterLetter)).setText(null);
                cells.get(numEnterLetter).setLetter(null);
            }
            numEnterLetter = numClickEmptyCell;

            cells.get(numClickEmptyCell).setLetter(letter);
            textView.setText(letter.toString());
        }
        textView.setBackgroundResource(R.color.white);
        numClickEmptyCell =-1;
    }

    // --- interfaces ---

    public interface OnClickEmptyCellListener{
        void onClickEmptyCell();
    }

    public  interface OnEnterWordListener{
        boolean onEnterWord(String word);
    }

    public interface OnAddWordInDictionaryListener {
        void onAddWordInDictionary(String word);
    }
}



