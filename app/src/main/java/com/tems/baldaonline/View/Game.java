package com.tems.baldaonline.View;

import com.tems.baldaonline.Cell;
import com.tems.baldaonline.CellAdapter;
import com.tems.baldaonline.GameUser;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private static final String myTag = "debugTag";

    private final String startWord;
    private final int rowsCount;
    private static List<Cell> cells;
    private int lastFocus;
    private boolean emptyCell;
    OnTurnOverListener onTurnOverListener;

    public void setOnTurnOverListener(OnTurnOverListener onTurnOverListener) {
        this.onTurnOverListener = onTurnOverListener;
    }

    public Game() {
        this.startWord = "балда";
        this.rowsCount = 5;
        lastFocus = -1;
        emptyCell = false;
    }

    public Game(String startWord, int timeForTurn) {
        this.startWord = startWord;
        this.rowsCount = startWord.length();
        lastFocus = -1;
        emptyCell = false;
    }

    public static void startGame(Game game) {
        cells = new ArrayList<>();
        for (int i = 0; i < game.rowsCount; i++) {
            for (int j = 0; j < game.rowsCount; j++) {
                if (i == Math.floor(game.rowsCount / 2)) {
                    cells.add(new Cell(game.startWord.charAt(j), false));
                    continue;
                }
                cells.add(new Cell(' ', false));
            }
        }
    }

    public String getStartWord() {
        return startWord;
    }

    public int getRowsCount() {
        return rowsCount;
    }

    public static Cell getCell(int position) {
        return cells.get(position);
    }

    public void setCell(int position, Cell cell) {
        cells.set(position, cell);
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        Game.cells = cells;
    }

    public int getLastFocus() {
        return lastFocus;
    }

    public void setLastFocus(int position) {
        lastFocus = position;
    }

    public boolean getEmptyCell() {
        return emptyCell;
    }

    public void setEmptyCell(boolean emptyCell) {
        this.emptyCell = emptyCell;
    }

    public void startGame() {
        cells = new ArrayList<>();
        for (int i = 0; i < game.rowsCount; i++) {
            for (int j = 0; j < game.rowsCount; j++) {
                if (i == Math.floor(game.rowsCount / 2)) {
                    cells.add(new Cell(game.startWord.charAt(j), false));
                    continue;
                }
                cells.add(new Cell(' ', false));
            }
        }

        CellAdapter cellAdapter = new CellAdapter(this, );
    }

    public void skipTurn() {
    }

    public void setOnTurnOver() {

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



