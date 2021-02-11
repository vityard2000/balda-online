package com.tems.baldaonline.View;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.tems.baldaonline.Cell;
import com.tems.baldaonline.CellAdapter;
import com.tems.baldaonline.Game;
import com.tems.baldaonline.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityGameOneOnOne extends AppCompatActivity {

    private static final String myTag = "debugTag";
    private GridView gridViewGameMap;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_one_on_one);

        Log.d(myTag, "Мы создались");

        String startWord = "слово";
        int rowsCount = startWord.length();

        Game game = new Game(startWord, rowsCount);
        //Game game = new Game();
        Game.startGame(game);
        StringBuilder tmp;
        for (int i = 0; i < game.getRowsCount(); i++) {
            tmp = new StringBuilder();
            for (int j = 0; j < game.getRowsCount(); j++) {
                tmp.append("[").append(Game.getCell((i * game.getRowsCount()) + j).getLetter()).append("]");
            }
            Log.d(myTag, String.valueOf(tmp));
        }

        gridViewGameMap = findViewById(R.id.activity_game_one_on_one__gv_game_map);

        gridViewGameMap.setNumColumns(game.getRowsCount());

        gridViewGameMap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        int offset = (int) getResources().getDimension(R.dimen.sizePaddingGameMap);
                        int widthCell = v.getWidth() / rowsCount - offset;
                        float coordX = event.getX();
                        float coordY = event.getY();
                        int numEl = 0;
                        for (int i = 0; i < rowsCount; i++) {
                            if (widthCell * i < coordX && widthCell * (i + 1) > coordX) {
                                numEl += i;
                            }
                            if (widthCell * i < coordY && widthCell * (i + 1) > coordY) {
                                numEl += i * rowsCount;
                            }
                        }
                        gridViewGameMap.getChildAt(numEl).setBackgroundResource(R.color.bt);

                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d(myTag, "UPgv");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        offset = (int) getResources().getDimension(R.dimen.sizePaddingGameMap);
                        widthCell = v.getWidth() / rowsCount - offset;
                        coordX = event.getX();
                        coordY = event.getY();
                        numEl = 0;
                        for (int i = 0; i < rowsCount; i++) {
                            if (widthCell * i < coordX && widthCell * (i + 1) > coordX) {
                                numEl += i;
                            }
                            if (widthCell * i < coordY && widthCell * (i + 1) > coordY) {
                                numEl += i * rowsCount;
                            }
                        }
                        gridViewGameMap.getChildAt(numEl).setBackgroundResource(R.color.bt);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        Log.d(myTag, "CANCELgv");
                        break;
                }
                return true;
            }
        });
        Log.d(myTag, "нашли gridView игрового поля");

        gridViewGameMap.setAdapter(new CellAdapter(this, game.getCells()));

    }
}
