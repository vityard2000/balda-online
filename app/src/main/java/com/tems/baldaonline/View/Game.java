package com.tems.baldaonline.View;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.GridView;

import com.tems.baldaonline.Cell;
import com.tems.baldaonline.CellAdapter;
import com.tems.baldaonline.GameUser;
import com.tems.baldaonline.R;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private static final String myTag = "debugTag";
    private final Activity context;
    private final String startWord;
    private int rowsCount;
    private static List<Cell> cells;
    private final GridView gridViewGameMap;
    private int lastFocus;
    private boolean emptyCell;
    OnTurnOverListener onTurnOverListener;

    public void setOnTurnOverListener(OnTurnOverListener onTurnOverListener) {
        this.onTurnOverListener = onTurnOverListener;
    }

    public Game(String startWord, int timeForTurn, Activity context, GridView gridViewGameMap) {
        this.context = context;
        this.gridViewGameMap = gridViewGameMap;
        this.startWord = startWord;
        lastFocus = -1;
        emptyCell = false;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void startGame() {
        this.rowsCount = startWord.length();
        gridViewGameMap.setNumColumns(rowsCount);

        //заполнение поля
        cells = new ArrayList<>();
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < rowsCount; j++) {
                if (i == Math.floor(rowsCount / 2)) {
                    cells.add(new Cell(startWord.charAt(j), false));
                    continue;
                }
                cells.add(new Cell(' ', false));
            }
        }


        //консольное отображение поля
        StringBuilder tmp;
            for (int i = 0; i < rowsCount; i++) {
            tmp = new StringBuilder();
            for (int j = 0; j < rowsCount; j++) {
                tmp.append("[").append(cells.get((i * rowsCount) + j).getLetter()).append("]");
            }
            Log.d(myTag, String.valueOf(tmp));
        }


        gridViewGameMap.setOnTouchListener((v, event) -> {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                setFocusCell(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                removeFocusCells(gridViewGameMap);
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(myTag, "CANCELGv");
                break;
        }
        return true;
        });

        Log.d(myTag, "нашли gridView игрового поля");

        gridViewGameMap.setAdapter(new CellAdapter(context, cells));
    }




    // снимаем фокус со всех ячеек. убираем в game последний фокус и наличае выделенной пустой ячейки
    private void removeFocusCells(GridView gridViewGameMap) {
        this.emptyCell = false;
        lastFocus = -1;

        for (int i = 0; i < cells.size(); i++) {
            if (cells.get(i).getFocus()) {
                cells.get(i).setFocus(false);
                gridViewGameMap.getChildAt(i).setBackgroundResource(R.color.white);
            }
        }
    }

    // задаём фокус ячейке
    private void setFocusCell(float x, float y) {
        // узнаём смещение(зависит от ширины линий, разделяющих ячейки) и получаем ширину одной ячейки
        float offset = 2;
        float widthCell = gridViewGameMap.getChildAt(0).getWidth() + offset;

        // палец юзера как по Х так и по У находиться на ячейке
        boolean flagFingerX = false;
        boolean flagFingerY = false;
        int numCell = 0;

        // проверка на какой ячейке находиться палец

        for (int i = 0; i < rowsCount; i++) {
            if (widthCell * i < x && widthCell * (i + 1) > x) {
                numCell += i;
                flagFingerX = true;
            }
            if (widthCell * i < y && widthCell * (i + 1) > y) {
                numCell += i * rowsCount;
                flagFingerY = true;
            }
        }

        // если всё ок и можно использовать выбранную ячейку, то она становиться в фокусе
        if (flagFingerX && flagFingerY && numCell != lastFocus) {
            // проверяем не вернулись ли мы назад на педыдущую ячейку
            if (checkMoveBackCell(numCell)) {
                gridViewGameMap.getChildAt(lastFocus).setBackgroundResource(R.color.white); // перекрашиваем ячейку в цвет отсутствия фокуса на ней
                lastFocus = numCell; // указываем, что она в фокусе
                cells.get(numCell).setFocus(true); // ставим фокус у ячейки
            } else {
                // указываем, что она в фокусе
                if (checkFocusCell(numCell)) {
                    gridViewGameMap.getChildAt(numCell).setBackgroundResource(R.color.bt_google_press); // перекрашиваем ячейку в цвет фокуса на ней
                    lastFocus =numCell; // указываем, что она в фокусе
                    cells.get(numCell).setFocus(true); // ставим фокус у ячейки
                }
            }
        }
    }

    // проверяем не вернулся ли палец игрока назад, что бы убрать выбранную им ранее ячейку
    private boolean checkMoveBackCell(int position) {
        boolean lastFocusAbout; // наличаем рядом ячейки, которая была последней выбрана пользователем

        // если ячейка, в которую попали вообще вне фокуса, то выходим
        if (!cells.get(position).getFocus()) {
            return false;
        }

        // получаем
        lastFocusAbout = checkLastFocusAboutCell(position, lastFocus);

        // проверяем
        if (lastFocusAbout) {
            // если уьранная с поля ячейка была пустой, то false пустая ячейка
            if (cells.get(lastFocus).getLetter() == ' ') {
                this.emptyCell = false;
            }
            // убираем фокус у ячейки, котроая убираемся с поля игры
            cells.get(lastFocus).setFocus(false);
            return true;
        } else {
            return false;
        }
    }

    // проверка возможности выбора ячейки
    private boolean checkFocusCell(int position) {
        boolean letterAbout; // буква рядом с ячейкий
        boolean letterInCell; // буква в ячейке
        boolean lastFocusAbout; // рядом последняя выбранная ячейка

        // если она уже в фокусе, то просто выходим
        if (cells.get(position).getFocus()) {
            return false;
        }

        // получаем
        lastFocusAbout = checkLastFocusAboutCell(position, lastFocus);
        letterAbout = checkLetterAboutCell(cells, position);
        letterInCell = cells.get(position).getLetter() != ' ';

        // проверка выбрана ли хоть одна ячейка (нужно для выбора дальнейшего алгоритам обработки)
        if (lastFocus > 0) {
            // если рядом есть ячейка в фокусе и или сама ячейка содержит букву или буква есть рядом и нет в выбранных пустой ячейки
            if (lastFocusAbout && ((letterInCell) || (letterAbout && !emptyCell))) {
                // если ячейка пуста, то указываем это, что бы нельзя было выбрать несколько пустых ячеек
                if (!letterInCell) {
                    this.emptyCell = true;
                }
                return true;
            } else {
                return false;
            }
        } else {
            // проверка есть ли рядом хоть одна ячейка с буквой или быть может сама ячейка содержит букву
            if (letterInCell || letterAbout) {
                // если ячейка пустая, то указываем, что уже выбрана ячейка без буквы
                if (!letterInCell) {
                    this.emptyCell = true;
                }
                return true;
            } else {
                return false;
            }
        }
    }

    // проверка на наличае букв в ячейках справа, слева, сверху и снизу от выбранной
    private boolean checkLetterAboutCell(List<Cell> cells, int position) {
        // справа

        if (position < (rowsCount * (position / rowsCount)) + rowsCount - 1) {
            if (cells.get(position + 1).getLetter() != ' ') {
                return true;
            }
        }
        // слева
        if (position > rowsCount * (position / rowsCount)) {
            if (cells.get(position - 1).getLetter() != ' ') {
                return true;
            }
        }
        // снизу
        if (position + rowsCount < rowsCount * rowsCount) {
            if (cells.get(position + rowsCount).getLetter() != ' ') {
                return true;
            }
        }
        // сверху
        if (position - rowsCount > 0) {
            if (cells.get(position - rowsCount).getLetter() != ' ') {
                return true;
            }
        }
        return false;
    }

    // проверка. находиться ли выбранная ячейка справа, слева, сверху или снизу от предыдущей выбранной
    private boolean checkLastFocusAboutCell(int position, int lastFocusPosition) {
        // справа
        if (position + 1 == lastFocusPosition) {
            return true;
        }
        // слева
        if (position - 1 == lastFocusPosition) {
            return true;
        }
        // снизу
        if (position + rowsCount == lastFocusPosition) {
            return true;
        }
        // сверху
        if (position - rowsCount == lastFocusPosition) {
            return true;
        }
        return false;
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

    interface OnTurnOverListener {
        void onTurnOver();
    }
}



