package com.tems.baldaonline;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.List;

public class AdapterColors extends BaseAdapter {
    private static final int ROWSCOUNT = 8;
    private final Activity context;
    private String colors[];


    public AdapterColors(Activity context, String[] colors) {
        this.context = context;
        this.colors = colors;

    }

    @Override
    public int getCount() {
        return colors.length;
    }

    @Override
    public Object getItem(int position) {
        return colors[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"ClickableViewAccessibility", "ViewHolder"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = new View(context);
        int offset = (int) context.getResources().getDimension(R.dimen.sizeMarginGameMap);
        int heightCell = parent.getWidth() / ROWSCOUNT - offset;
        view.setBackgroundColor(Color.parseColor((String) getItem(position)));
        view.setLayoutParams(new ViewGroup.LayoutParams(heightCell, heightCell));
        view.setId((int) getItemId(position));
        return view;
    }
}