package com.tems.baldaonline.View;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tems.baldaonline.Cell;
import com.tems.baldaonline.CellAdapter;
import com.tems.baldaonline.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityGameOneOnOne extends AppCompatActivity {

    private static final String myTag = "debugTag";
    private RecyclerView recyclerViewGameMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_one_on_one);

        Log.d(myTag, "Мы создались");
        recyclerViewGameMap = findViewById(R.id.activity_game_one_on_one__rv_game_map);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        CellAdapter cellAdapter = new CellAdapter(gen());
        recyclerViewGameMap.setLayoutManager(gridLayoutManager);
        recyclerViewGameMap.setAdapter(cellAdapter);

    }
    public List<Cell> gen() {
        List<Cell> cells = new ArrayList<>();
        cells.add(new Cell(' '));
        cells.add(new Cell('b'));
        cells.add(new Cell('c'));
        cells.add(new Cell('d'));
        cells.add(new Cell('e'));
        cells.add(new Cell('f'));
        cells.add(new Cell('g'));
        cells.add(new Cell('h'));
        cells.add(new Cell('i'));
        return cells;
    }
}
