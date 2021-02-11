package com.tems.baldaonline;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private static final String myTag = "debugTag";

    private final String startWord;
    private final int rowsCount;
    private static List<Cell> cells;

    public Game() {
        this.startWord = "балда";
        this.rowsCount = 5;
    }
    public Game(String startWord, int rowsCount) {
        this.startWord = startWord;
        this.rowsCount = rowsCount;
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
        this.cells = cells;
    }
}
