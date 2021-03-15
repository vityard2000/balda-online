package com.tems.baldaonline;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;


public class ListAdapter extends BaseAdapter{
    private List<WordCell> first;
    private List<WordCell> second;
    private Activity context;

    public ListAdapter(Activity context, List<WordCell> first, List<WordCell> second) {
        this.context = context;
        this.first = first;
        this.second = second;
    }

    @Override
    public int getCount() {
        return Math.max(first.size(), second.size())+1;
    }

    @Override
    public Object getItem(int position) {
        return first.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = context.getLayoutInflater().inflate(R.layout.list_item, parent, false);
        }
        TextView tvFirstWord = (TextView) convertView.findViewById(R.id.list_item__tv_first_word);
        TextView tvSecondWord = (TextView)  convertView.findViewById(R.id.list_item__tv_second_word);
        TextView tvFirstCount = (TextView) convertView.findViewById(R.id.list_item__tv_first_cnt);
        TextView tvSecondCount = (TextView)  convertView.findViewById(R.id.list_item__tv_second_cnt);
        if(first.size() >= position + 1){
            if(position%2==1){
                tvFirstCount.setBackgroundResource(R.drawable.style_solid_corners_pink);
            }else{
                tvFirstCount.setBackgroundResource(R.drawable.style_solid_corners_purple);
            }
            String firstWord = first.get(position).toStringNormal();
            tvFirstWord.setText(firstWord);
            tvFirstCount.setText(String.valueOf(firstWord.length()));
            tvFirstWord.setVisibility(View.VISIBLE);
            tvFirstCount.setVisibility(View.VISIBLE);

        }

        else {

            tvFirstWord.setVisibility(View.GONE);
            tvFirstCount.setVisibility(View.GONE);
        }

        if(second.size() >= position + 1){
            if(position%2==1){
                tvSecondCount.setBackgroundResource(R.drawable.style_solid_corners_pink);
            }else{
                tvSecondCount.setBackgroundResource(R.drawable.style_solid_corners_purple);
            }
            String secondWord = second.get(position).toStringNormal();
            tvSecondWord.setText(secondWord);
            tvSecondCount.setText(String.valueOf(secondWord.length()));
            tvSecondWord.setVisibility(View.VISIBLE);
            tvSecondCount.setVisibility(View.VISIBLE);

        }
        else {
            tvSecondWord.setVisibility(View.GONE);
            tvSecondCount.setVisibility(View.GONE);
        }

        return convertView;
    }
}
