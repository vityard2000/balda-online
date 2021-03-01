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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint({"ResourceAsColor", "ResourceType", "ClickableViewAccessibility"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            textView = (TextView) context.getLayoutInflater().inflate(R.layout.cell_item, parent,
                    false).findViewById(R.id.cell_item_tv);
        }
        else {
            textView = (TextView) convertView;
        }

        int rowsCount= (int) Math.sqrt(cells.size());
        int offset = (int) context.getResources().getDimension(R.dimen.sizePaddingGameMap);

        int heightCell = parent.getWidth() / rowsCount - offset;
        textView.setText(Character.toString(cells.get(position).getLetter()).toUpperCase());
        textView.setHeight(heightCell);
        textView.setOnTouchListener((v, event) -> false);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, heightCell);
        textView.setId(position);

        return textView;
    }
}

