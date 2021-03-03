package com.tems.baldaonline;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.List;


public class CellAdapter extends BaseAdapter {

    private static final String myTag = "debugTag";

    private final Activity context;
    private final List<Cell> cells;

    public CellAdapter(Activity context, List<Cell> cells) {
        this.context = context;
        this.cells = cells;
    }

    @Override
    public int getCount() {
        return cells.size();
    }

    @Override
    public Object getItem(int position) {
        return cells.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @SuppressLint({"ClickableViewAccessibility", "ViewHolder"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        int rowsCount= (int) Math.sqrt(cells.size());
        int offset = (int) context.getResources().getDimension(R.dimen.sizePaddingGameMap);
        int heightCell = parent.getWidth() / rowsCount - offset;

        textView = (TextView) context.getLayoutInflater().inflate(R.layout.cell_item, parent, false).findViewById(R.id.cell_item_tv);
        Character letter = cells.get(position).getLetter();
        textView.setHeight(heightCell);
        textView.setOnTouchListener((v, event) -> false);
        if(letter!=null){
            textView.setText(cells.get(position).getLetter().toString());
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, heightCell);
        }

        textView.setId(position);
        return textView;
    }
}

