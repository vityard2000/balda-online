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

public class ActivityGameOneOnOne extends AppCompatActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_one_on_one);

    }
}
