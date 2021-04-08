package com.tems.baldaonline;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tems.baldaonline.View.ActivityGameOneOnOne;

import java.util.ArrayList;
import java.util.Map;


public class AdapterList extends BaseAdapter{
    private Activity context;
    public GameUser firstUser;
    public GameUser secondUser;

    public AdapterList(Activity context, GameUser firstUser, GameUser secondUser) {
        this.context = context;
        this.firstUser = firstUser;
        this.secondUser = secondUser;
    }

    @Override
    public int getCount() {
        return Math.max(firstUser.getWords().size() , secondUser.getWords().size())+1;
    }

    @Override
    public Object getItem(int position) {
        return firstUser.getWords().get(position);
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

        if(firstUser.getWords().size() >= position + 1){
            if(position%2==1){
                tvFirstCount.setBackgroundResource(R.drawable.style_solid_corners_pink);
            }else{
                tvFirstCount.setBackgroundResource(R.drawable.style_solid_corners_purple);
            }
            String firstWord = firstUser.getWords().get(position);
            tvFirstWord.setText(firstWord);
            tvFirstCount.setText(String.valueOf(firstWord.length()));
            tvFirstWord.setVisibility(View.VISIBLE);
            tvFirstCount.setVisibility(View.VISIBLE);

        }

        else {

            tvFirstWord.setVisibility(View.GONE);
            tvFirstCount.setVisibility(View.GONE);
        }

        if(secondUser.getWords().size() >= position + 1){
            if(position%2==1){
                tvSecondCount.setBackgroundResource(R.drawable.style_solid_corners_pink);
            }else{
                tvSecondCount.setBackgroundResource(R.drawable.style_solid_corners_purple);
            }
            String secondWord = secondUser.getWords().get(position);
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
