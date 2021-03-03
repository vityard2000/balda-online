package com.tems.baldaonline;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.tems.baldaonline.Cell;
import com.tems.baldaonline.CellAdapter;
import com.tems.baldaonline.GameUser;
import com.tems.baldaonline.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
    private static final String myTag = "debugTag";
    private Activity context;
    private final String startWord;
    private int rowsCount;
    private List<Cell> cells;
    private List<Integer> numsCells;
    private GridView gridViewGameMap;
    private int lastNumCell;
    OnTurnOverListener onTurnOverListener;
    public void setOnTurnOverListener(OnTurnOverListener onTurnOverListener) {
        this.onTurnOverListener = onTurnOverListener;
    }
    public Game(String startWord, int timeForTurn, Activity context, GridView gridViewGameMap) {
        this.context = context;
        this.gridViewGameMap = gridViewGameMap;
        this.startWord = startWord.toUpperCase();

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("ClickableViewAccessibility")
    public void startGame() {
        lastNumCell = -1000;
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
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE: // палец передвигают
                    setFocusCell(event.getX(), event.getY());
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    StringBuilder strBldr =  new StringBuilder();
                    numsCells.forEach(i ->strBldr.append(cells.get(i).getLetter()));
                    Toast.makeText(context, strBldr, Toast.LENGTH_LONG).show();
                    removeFocusCells(gridViewGameMap);
                    break;
            }
            return true;
        });
        gridViewGameMap.setAdapter(new CellAdapter(context, cells));
    }




    // снимаем фокус со всех ячеек. убираем в game последний фокус и наличае выделенной пустой ячейки
    private void removeFocusCells(GridView gridViewGameMap) {
        lastNumCell = -1000;
        for (int i:numsCells) gridViewGameMap.getChildAt(i).setBackgroundResource(R.color.white);
        numsCells.clear();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setFocusCell(float x, float y) {
        float offset = context.getResources().getDimension(R.dimen.sizePaddingGameMap);                                                                                 // расстояние между cell
        float widthCell = gridViewGameMap.getChildAt(0).getWidth() + offset;                         // ширина cell
        int numCell;                                                                                        // номер cell, на которой наведен палец
        numCell = (int) (x / widthCell) + (int) (y / widthCell) * rowsCount;                                // расчет номера cell, на которой наведен палец
        if (numCell < 0 || numCell > rowsCount * rowsCount-1 || cells.get(numCell).getLetter() == null){
            removeFocusCells(gridViewGameMap);                                                              // снять выделение, если палец вне gridview
        }
        else if (numCell != lastNumCell)                                                                    // если палец на новой cell
        {
            if (numsCells.contains(numCell)) {                                                              // если мы уже виделили эту cell
                List<Integer> sub = numsCells.subList(numsCells.indexOf(numCell)+1, numsCells.size());      // sub лист начиная с повторяющегося cell и до конца листа
                sub.forEach(i -> gridViewGameMap.getChildAt(i).setBackgroundResource(R.color.white));       // снять выделение начиная с повторяющегося cell до последнего выделенного cell
                sub.clear();                                                                                //отчистить sub лист
                lastNumCell = numCell;
            } else if(
                           numsCells.isEmpty()                                                              //если мы еще ничего не выделили
                        || numCell == lastNumCell + 1                                                       // рядом ли cell
                        || numCell == lastNumCell - 1
                        || numCell == lastNumCell + rowsCount
                        || numCell == lastNumCell - rowsCount)
            {
                numsCells.add(numCell);                                                                     // добавляем номер cell в лист
                gridViewGameMap.getChildAt(numCell).setBackgroundResource(R.color.bt_google_press);         //выделяем cell
                lastNumCell = numCell;
            }
        }

    }

    public void skipTurn() {
    }

    public void destroyGame() {
    }

    public GameUser getFirstGameUser() {
        return new GameUser();
    }

    public GameUser getSecondGameUser() {
        return new GameUser();
    }

    public void stopGame() {
    }

    public interface OnTurnOverListener {
        void onTurnOver();
    }
}



