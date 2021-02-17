package com.tems.baldaonline.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.tems.baldaonline.Game;
import com.tems.baldaonline.R;

public class ActivityGameOneOnOne extends AppCompatActivity implements View.OnClickListener {

    private static final String myTag = "debugTag";
    private int timeForTurn;
    private String startWord;
    private String firstUserName;
    private String secondUserName;
    private GridView gridViewGameMap;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_one_on_one);
        initValues(); //инициализируем необходимые переменные



    }

    private void initValues() {
        Intent intent = getIntent();
        timeForTurn = intent.getIntExtra("time_for_turn" , 1);
        startWord = intent.getStringExtra("start_word");
        firstUserName = intent.getStringExtra("name_first_user");
        secondUserName = intent.getStringExtra("name_first_user");
        gridViewGameMap = findViewById(R.id.activity_game_one_on_one__gv_game_map);
        game = new Game(startWord, timeForTurn, gridViewGameMap);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }
}

        //gridViewGameMap = findViewById(R.id.activity_game_one_on_one__gv_game_map);
        ////конструктор Game создает экземпляр класса Game и задает начальные значения для логики игры,
        ////а именно: стартовое слово, на основе которого производитя отрисовка поля игры;
        ////время на ход; GridView, в котором будет отрисовываться поле игры
        //game = new Game(startWord, timeForTurn, gridViewGameMap);     Log.d(myTag, "Мы создались");
//
        ////метод startGame вызывается один раз и отрисовывает поле и запускает первый ход
        //game.startGame();
        ////метод destroyGame вызывется при закрытии игры
        //game.destroyGame();
        ////метод stopGame сохраняет состояние игры
        //game.stopGame();
        ////метод skipTurn вызывается, если игрок пожелал пропустить ход
        //game.skipTurn();
        ////метод getFirstGameUser возращает первого игрока
        ////класс GameUser нужен только для хранения набранных слов и подсчета очков
        ////для хранения имени игрока есть класс OfflineUser
        //GameUser gu1 = game.getFirstGameUser();
        ////метод getFirstGameUser возращает второго игрока
        //GameUser gu2 = game.getSecondGameUser();
        ////метод обратного вызова принимает на вход сласс реализуюший интерфейс OnTurnOven необходимый
        //// для задания логики после хода пользователя
        ////интерфейс OnTurnOver имеет единственный метод TurnOver
        //game.setOnTurnOver();


//        StringBuilder tmp;
//        for (int i = 0; i < game.getRowsCount(); i++) {
//            tmp = new StringBuilder();
//            for (int j = 0; j < game.getRowsCount(); j++) {
//                tmp.append("[").append(Game.getCell((i * game.getRowsCount()) + j).getLetter()).append("]");
//            }
//            Log.d(myTag, String.valueOf(tmp));
//        }
//
//
//
//        gridViewGameMap.setNumColumns(game.getRowsCount());
//
//        gridViewGameMap.setOnTouchListener((v, event) -> {
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                    setFocusCell(event.getX(), event.getY());
//                    break;
//                case MotionEvent.ACTION_UP:
//                    removeFocusCells(gridViewGameMap);
//                    break;
//                case MotionEvent.ACTION_MOVE:
//                    setFocusCell(event.getX(), event.getY());
//                    break;
//                case MotionEvent.ACTION_CANCEL:
//                    Log.d(myTag, "CANCELgv");
//                    break;
//            }
//            return true;
//        });
//        Log.d(myTag, "нашли gridView игрового поля");
//
//        gridViewGameMap.setAdapter(new CellAdapter(this, game.getCells()));
//    }
//
//
//
//    // снимаем фокус со всех ячеек. убираем в game последний фокус и наличае выделенной пустой ячейки
//    private void removeFocusCells(GridView gridViewGameMap) {
//        List<Cell> cells;
//        cells = game.getCells();
//
//        game.setEmptyCell(false);
//        game.setLastFocus(-1);
//
//        for (int i = 0; i < cells.size(); i++) {
//            if (cells.get(i).getFocus()) {
//                cells.get(i).setFocus(false);
//                gridViewGameMap.getChildAt(i).setBackgroundResource(R.color.white);
//            }
//        }
//    }
//
//    // задаём фокус ячейке
//    private void setFocusCell(float x, float y) {
//        // узнаём смещение(зависит от ширины линий, разделяющих ячейки) и получаем ширину одной ячейки
//        float offset = getResources().getDimension(R.dimen.sizePaddingGameMap);
//        float widthCell = gridViewGameMap.getChildAt(0).getWidth() + offset;
//
//        // палец юзера как по Х так и по У находиться на ячейке
//        boolean flagFingerX = false;
//        boolean flagFingerY = false;
//        int numCell = 0;
//
//        // проверка на какой ячейке находиться палец
//        for (int i = 0; i < rowsCount; i++) {
//            if (widthCell * i < x && widthCell * (i + 1) > x) {
//                numCell += i;
//                flagFingerX = true;
//            }
//            if (widthCell * i < y && widthCell * (i + 1) > y) {
//                numCell += i * rowsCount;
//                flagFingerY = true;
//            }
//        }
//
//        // если всё ок и можно использовать выбранную ячейку, то она становиться в фокусе
//        if (flagFingerX && flagFingerY && numCell != game.getLastFocus()) {
//            // проверяем не вернулись ли мы назад на педыдущую ячейку
//            if (checkMoveBackCell(numCell)) {
//                gridViewGameMap.getChildAt(game.getLastFocus()).setBackgroundResource(R.color.white); // перекрашиваем ячейку в цвет отсутствия фокуса на ней
//                game.setLastFocus(numCell); // указываем, что она в фокусе
//                game.getCells().get(numCell).setFocus(true); // ставим фокус у ячейки
//            } else {
//                // указываем, что она в фокусе
//                if (checkFocusCell(numCell)) {
//                    gridViewGameMap.getChildAt(numCell).setBackgroundResource(R.color.bt); // перекрашиваем ячейку в цвет фокуса на ней
//                    game.setLastFocus(numCell); // указываем, что она в фокусе
//                    game.getCells().get(numCell).setFocus(true); // ставим фокус у ячейки
//                }
//            }
//        }
//    }
//
//    // проверяем не вернулся ли палец игрока назад, что бы убрать выбранную им ранее ячейку
//    private boolean checkMoveBackCell(int position) {
//        List<Cell> cells;
//        cells = game.getCells(); // список игровых букв
//        boolean lastFocusAbout; // наличаем рядом ячейки, которая была последней выбрана пользователем
//
//        // если ячейка, в которую попали вообще вне фокуса, то выходим
//        if (!cells.get(position).getFocus()) {
//            return false;
//        }
//
//        // получаем
//        lastFocusAbout = checkLastFocusAboutCell(position, game.getLastFocus());
//
//        // проверяем
//        if (lastFocusAbout) {
//            // если уьранная с поля ячейка была пустой, то false пустая ячейка
//            if (cells.get(game.getLastFocus()).getLetter() == ' ') {
//                game.setEmptyCell(false);
//            }
//            // убираем фокус у ячейки, котроая убираемся с поля игры
//            cells.get(game.getLastFocus()).setFocus(false);
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    // проверка возможности выбора ячейки
//    private boolean checkFocusCell(int position) {
//        List<Cell> cells;
//        cells = game.getCells();
//        boolean letterAbout; // буква рядом с ячейкий
//        boolean letterInCell; // буква в ячейке
//        boolean lastFocusAbout; // рядом последняя выбранная ячейка
//
//        // если она уже в фокусе, то просто выходим
//        if (cells.get(position).getFocus()) {
//            return false;
//        }
//
//        // получаем
//        lastFocusAbout = checkLastFocusAboutCell(position, game.getLastFocus());
//        letterAbout = checkLetterAboutCell(cells, position);
//        letterInCell = cells.get(position).getLetter() != ' ';
//
//        // проверка выбрана ли хоть одна ячейка (нужно для выбора дальнейшего алгоритам обработки)
//        if (game.getLastFocus() > 0) {
//            // если рядом есть ячейка в фокусе и или сама ячейка содержит букву или буква есть рядом и нет в выбранных пустой ячейки
//            if (lastFocusAbout && ((letterInCell) || (letterAbout && !game.getEmptyCell()))) {
//                // если ячейка пуста, то указываем это, что бы нельзя было выбрать несколько пустых ячеек
//                if (!letterInCell) {
//                    game.setEmptyCell(true);
//                }
//                return true;
//            } else {
//                return false;
//            }
//        } else {
//            // проверка есть ли рядом хоть одна ячейка с буквой или быть может сама ячейка содержит букву
//            if (letterInCell || letterAbout) {
//                // если ячейка пустая, то указываем, что уже выбрана ячейка без буквы
//                if (!letterInCell) {
//                    game.setEmptyCell(true);
//                }
//                return true;
//            } else {
//                return false;
//            }
//        }
//    }
//
//    // проверка на наличае букв в ячейках справа, слева, сверху и снизу от выбранной
//    private boolean checkLetterAboutCell(List<Cell> cells, int position) {
//        // справа
//        if (position < (rowsCount * (position / rowsCount)) + rowsCount - 1) {
//            if (cells.get(position + 1).getLetter() != ' ') {
//                return true;
//            }
//        }
//        // слева
//        if (position > rowsCount * (position / rowsCount)) {
//            if (cells.get(position - 1).getLetter() != ' ') {
//                return true;
//            }
//        }
//        // снизу
//        if (position + rowsCount < rowsCount * rowsCount) {
//            if (cells.get(position + rowsCount).getLetter() != ' ') {
//                return true;
//            }
//        }
//        // сверху
//        if (position - rowsCount > 0) {
//            if (cells.get(position - rowsCount).getLetter() != ' ') {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    // проверка. находиться ли выбранная ячейка справа, слева, сверху или снизу от предыдущей выбранной
//    private boolean checkLastFocusAboutCell(int position, int lastFocusPosition) {
//        // справа
//        if (position + 1 == lastFocusPosition) {
//            return true;
//        }
//        // слева
//        if (position - 1 == lastFocusPosition) {
//            return true;
//        }
//        // снизу
//        if (position + rowsCount == lastFocusPosition) {
//            return true;
//        }
//        // сверху
//        if (position - rowsCount == lastFocusPosition) {
//            return true;
//        }
//        return false;
//    }
//


