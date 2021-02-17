package com.tems.baldaonline;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.List;


public class CellAdapter extends BaseAdapter {

    private static final String myTag = "debugTag";

    private final Context context;
    private final List<Cell> cells;

    public CellAdapter(Context context, List<Cell> cells) {
        this.context = context;
        this.cells = cells;
        Log.d(myTag, "попали в конструктор адаптера и передали в класс контекст с списком");
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
            // количество столбцов у поля игрового
            int rowsCount;
            rowsCount = (int) Math.sqrt(cells.size());

            // параметры textView
            textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, context.getResources().getDimension(R.dimen.sizeTextOnMapeMap));
            textView.setBackgroundResource(R.color.white);
            textView.setTextColor(R.color.black);
            textView.setText(Character.toString(cells.get(position).getLetter()).toUpperCase());

            // смещение из-за padding у ячеек поля
            int offset = (int) context.getResources().getDimension(R.dimen.sizePaddingGameMap);

            // из-за того, что без ячеек GridView имеет ширину 0, приходиться изъебываться
            // когда задаём параметры с 1 по n ячейки, то у первой (второй по счёту) задача задать такую же ширину и у нулевой
            if (position != 0) {
                if (position == 1) {
                    TextView firstTextView = (TextView) parent.getChildAt(0);
                    firstTextView.setHeight(parent.getWidth() / rowsCount - offset);
                }
                textView.setHeight(parent.getWidth() / rowsCount - offset);
            }

            // не обрабатываем касания у ячеек
            textView.setOnTouchListener((v, event) -> false);
        } else {
            textView = (TextView) convertView;
        }
        textView.setId(position);
        return textView;
    }
}

