package com.tems.baldaonline.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.tems.baldaonline.domain.Accessory;
import com.tems.baldaonline.R;

import java.util.List;

public class AdapterAccessories extends BaseAdapter{

    private static final int ROWSCOUNT = 5;
    private final Activity context;
    private List<Accessory> accessories;
    public AdapterAccessories(Activity context, List<Accessory> accessories) {
        this.context = context;
        this.accessories = accessories;

    }

    @Override
    public int getCount() {
        return accessories.size();
    }

    @Override
    public Object getItem(int position) {
        return accessories.get(position).getResPreview();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"ClickableViewAccessibility", "ViewHolder"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int offset = (int) context.getResources().getDimension(R.dimen.sizeMarginGameMap);
        int heightCell = parent.getWidth() / ROWSCOUNT - offset;
        FrameLayout frameLayout  = (FrameLayout) context.getLayoutInflater().inflate(R.layout.accessory_item, parent, false);
        ImageView imageView = frameLayout.findViewById(R.id.accessory_item);
        imageView.setImageResource((Integer) getItem(position));
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(heightCell, heightCell));

        return frameLayout;
    }
}