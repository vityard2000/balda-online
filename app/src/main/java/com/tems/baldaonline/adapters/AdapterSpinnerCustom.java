package com.tems.baldaonline.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tems.baldaonline.R;

import org.jetbrains.annotations.NotNull;

public class AdapterSpinnerCustom extends ArrayAdapter<String> {

    LayoutInflater flater;

    public AdapterSpinnerCustom(Activity context, int resouceId, int textviewId, String[] data){
        super(context,resouceId,textviewId, data);
        flater = context.getLayoutInflater();
    }
    @Override
    public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
        if(convertView == null){
            convertView = flater.inflate(R.layout.spinner_item,parent, false);
        }
        String rowItem = getItem(position);
        TextView txtTitle = convertView.findViewById(R.id.spinner_item_tv);
        txtTitle.setText(rowItem);
        if(position%2==1) {
            txtTitle.setBackground(new ColorDrawable(Color.argb(77,255,255,255)));
        }

        return convertView;
    }
}
