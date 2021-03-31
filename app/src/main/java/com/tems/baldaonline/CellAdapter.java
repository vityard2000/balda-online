package com.tems.baldaonline;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class CellAdapter extends BaseAdapter {

    private final Activity context;
    private final List<Character> pole;

    public CellAdapter(Activity context, List<Character> pole) {
        this.context = context;
        this.pole = pole;
    }

    @Override
    public int getCount() {
        return pole.size();
    }

    @Override
    public Object getItem(int position) {
        return pole.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @SuppressLint({"ClickableViewAccessibility", "ViewHolder"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        int rowsCount= (int) Math.sqrt(pole.size());
        int offset = (int) context.getResources().getDimension(R.dimen.sizeMarginGameMap);
        int heightCell = parent.getWidth() / rowsCount - offset;

        textView = (TextView) context.getLayoutInflater().inflate(R.layout.cell_item, parent, false).findViewById(R.id.cell_item_tv);
        Character letter = pole.get(position);
        textView.setHeight(heightCell);
        textView.setOnTouchListener((v, event) -> false);
        if(letter!=null){
            textView.setText(pole.get(position).toString());

        }
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, heightCell);
        textView.setId(position);
        return textView;
    }
}

