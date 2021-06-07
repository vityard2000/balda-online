package com.tems.baldaonline.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.tems.baldaonline.App;
import com.tems.baldaonline.domain.Accessory;
import com.tems.baldaonline.adapters.AdapterAccessories;
import com.tems.baldaonline.adapters.AdapterColors;
import com.tems.baldaonline.R;
import com.tems.baldaonline.domain.Mascot;
import com.tems.baldaonline.domain.Outfit;

import java.util.ArrayList;
import java.util.List;

public class ActivityEditor extends AppCompatActivity {
    private static final String myTag = "debugTag";
    private boolean num_mascot = false;
    private Button button;
    private GridView gVColors;
    private GridView gVAccessories;
    private ImageView mascot;
    private String[] colors;
    private List<Accessory> accessories;
    private String color;
    private SharedPreferences sPref;
    private ImageView imageView;
    private Integer[] look;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        look = new Integer[]{0, 0, 0, 0};
        accessories = new ArrayList<>();
        num_mascot = getIntent().getBooleanExtra("num_mascot", false);

        mascot        = findViewById(R.id.activity_editor__img_v_mascot_backg);
        button        = findViewById(R.id.activity_editor__bt);
        gVAccessories = findViewById(R.id.activity_editor__gv_accessories);
        gVColors      = findViewById(R.id.activity_editor__gv_colors);
        sPref = getSharedPreferences("settingsGameOneOnOne", MODE_PRIVATE);


        if(num_mascot) {
            color = sPref.getString("mascot_color_2", getResources().getString(R.color.mascot_2));
            look[0] = sPref.getInt("mascot_two_index_0", 0);
            look[1] = sPref.getInt("mascot_two_index_1", 0);
            look[2] = sPref.getInt("mascot_two_index_2", 0);
            look[3] = sPref.getInt("mascot_two_index_3", 0);
        }else {
            color = sPref.getString("mascot_color_1", getResources().getString(R.color.mascot_1));
            look[0] = sPref.getInt("mascot_one_index_0", 0);
            look[1] = sPref.getInt("mascot_one_index_1", 0);
            look[2] = sPref.getInt("mascot_one_index_2", 0);
            look[3] = sPref.getInt("mascot_one_index_3", 0);
        }
        mascot.setColorFilter(Color.parseColor(color), android.graphics.PorterDuff.Mode.SRC_IN);
        ((ImageView)findViewById(R.id.activity_editor__img_v_z_index_0)).setImageResource(look[0]);
        ((ImageView)findViewById(R.id.activity_editor__img_v_z_index_1)).setImageResource(look[1]);
        ((ImageView)findViewById(R.id.activity_editor__img_v_z_index_2)).setImageResource(look[2]);
        ((ImageView)findViewById(R.id.activity_editor__img_v_z_index_3)).setImageResource(look[3]);
        button.setOnClickListener(v -> {

            Outfit outfit = new Outfit();
            outfit.setzIndex0(look[0]);
            outfit.setzIndex1(look[1]);
            outfit.setzIndex2(look[2]);
            outfit.setzIndex3(look[3]);
            Mascot mascot = new Mascot(num_mascot? 2:1, Color.parseColor(color), outfit);
            App.getInstance().getSettingsRepository().saveMascot(mascot);

            onBackPressed();
            finish();
        });
        accessories.add(new Accessory(R.drawable.ic_accessory_skirt,R.drawable.ic_accessory_skirt_preview, 1));
        accessories.add(new Accessory(R.drawable.ic_accessory_corona,R.drawable.ic_accessory_corona_preview, 3));
        accessories.add(new Accessory(R.drawable.ic_accessory_shirt,R.drawable.ic_accessory_shirt_preview, 1));
        accessories.add(new Accessory(R.drawable.ic_accessory_hat,R.drawable.ic_accessory_hat_preview, 3));
        gVAccessories.setAdapter(new AdapterAccessories(this, accessories));
        gVAccessories.setOnItemClickListener((parent, view, position, id) -> {
            int res = accessories.get(position).getRes();
            int z_index = accessories.get(position).getZ_index();
            switch (z_index){
                case 0:
                    imageView = findViewById(R.id.activity_editor__img_v_z_index_0);
                    break;
                case 1:
                    imageView = findViewById(R.id.activity_editor__img_v_z_index_1);
                    break;
                case 2:
                    imageView = findViewById(R.id.activity_editor__img_v_z_index_2);
                    break;
                case 3:
                    imageView = findViewById(R.id.activity_editor__img_v_z_index_3);
                    break;
            }

            if(look[z_index] == res) {
                imageView.setImageResource(0);
                look[z_index] = 0;
            }
            else{
                imageView.setImageResource(res);
                look[z_index] = res;
            }
        });
        colors = getResources().getStringArray(R.array.editor_colors);
        gVColors.setAdapter(new AdapterColors(this, colors));
        gVColors.setOnItemClickListener((parent, view, position, id) -> {
            mascot.setColorFilter(Color.parseColor(colors[position]), android.graphics.PorterDuff.Mode.SRC_IN);
            color = colors[position];
        });
    }
}