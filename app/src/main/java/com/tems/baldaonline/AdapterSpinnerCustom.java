package com.tems.baldaonline;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdapterSpinnerCustom extends ArrayAdapter<String> {

    LayoutInflater flater;

    public AdapterSpinnerCustom(Activity context, int resouceId, int textviewId, String[] data){

        super(context,resouceId,textviewId, data);
        flater = context.getLayoutInflater();
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = flater.inflate(R.layout.spinner_item,parent, false);
        }
        String rowItem = getItem(position);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.spinner_item_tv);
        txtTitle.setText(rowItem);
        if(position%2==1) {
            txtTitle.setBackgroundResource(R.drawable.backgraund_white);
        }
        return convertView;
    }
}
