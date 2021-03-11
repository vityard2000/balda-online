package com.tems.baldaonline;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
    private int numClickEmptyCell = -1;
    private int numEnterLetter =-1;
    private List<Cell> cells;
    private List<Integer> numsCells;
    private final GridView gridViewGameMap;
    private int lastNumCell = -1;
    OnAddWordInDictionaryListener onAddWordInDictionary;
    OnEnterWordListener onEnterWordListener;
    OnClickEmptyCellListener onClickEmptyCellListener;

    public Game(String startWord, int timeForTurn, Activity context, GridView gridViewGameMap) {
        this.context = context;
        this.gridViewGameMap = gridViewGameMap;
        this.startWord = startWord.toUpperCase();
    }
    @SuppressLint("ClickableViewAccessibility")
    public void startGame() {

        currentUser = FIRST_USER;
        firstGameUser = new GameUser();
        secondGameUser = new GameUser();

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
            int numCell;
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    numCell = getNumCell(event.getX(), event.getY());

                    if(numCell != -1 && cells.get(numCell).getLetter() == null && isBesideCell(numCell)) { //палец опустили на пустую cell
                        numClickEmptyCell = numCell;
                        gridViewGameMap.getChildAt(numCell).setBackgroundResource(R.color.bt_google_press);
                    }

                    break;
                case MotionEvent.ACTION_MOVE: // палец передвигают
                    numCell = getNumCell(event.getX(), event.getY());
                    if(numCell == -1) return true;

                    if(numClickEmptyCell ==-1 && cells.get(numCell).getLetter()!=null&&numEnterLetter!=-1)// условия для продолжения веделения слова
                    {
                         setFocusCell(numCell);
                    }

                    break;

                case MotionEvent.ACTION_UP:
                    numCell = getNumCell(event.getX(), event.getY());
                    if(numClickEmptyCell !=-1){ // если опустили палец на пустую cell
                        if(numCell == numClickEmptyCell) { // если подняли палец с той же пустой cell, на которую палец опустили

                            onClickEmptyCellListener.onClickEmptyCell();

                        } else {
                            gridViewGameMap.getChildAt(numClickEmptyCell).setBackgroundResource(R.color.white);
                        }

                        //numClickEmptyCell = -1;
                    } else {

                        if (lastNumCell!=-1&&lastNumCell == numCell) {
                            boolean flag = false;
                            String word = "";
                            for (int i : numsCells){
                                word += cells.get(i).getLetter();
                                if(i == numEnterLetter){
                                    flag = true;
                                }
                            };
                            if(flag){
                                numEnterLetter= -1;

                                if(onEnterWordListener.onEnterWord(word.toString())) {
                                    if(true) {//есть ли слово в словаре
                                        List<Cell> listCell = new ArrayList<>();
                                        for (int i : numsCells) listCell.add(cells.get(i));
                                        WordCell wordCell = new WordCell(listCell);
                                        // смена игрока
                                        if (currentUser == FIRST_USER) {
                                            firstGameUser.setWord(wordCell);
                                        } else {
                                            secondGameUser.setWord(wordCell);
                                        }

                                        onAddWordInDictionary.onAddWordInDictionary(word);
                                        currentUser = (currentUser == FIRST_USER)?SECOND_USER:FIRST_USER;
                                        Toast.makeText(context, word, Toast.LENGTH_LONG).show();
                                    } else{
                                        onAddWordInDictionary.onAddWordInDictionary(null);
                                    }

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
                gridViewGameMap.getChildAt(numCell).setBackgroundResource(R.color.bt_google_press);         //выделяем cell

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



