package com.tems.baldaonline.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.tems.baldaonline.R;

public class DialogFinish extends AppCompatDialogFragment {
    private boolean winUser;
    private OnCloseListener onCloseListener;
    private String firstUserName;
    private String secondUserName;
    private String colorOne;
    private String colorTwo;
    private Integer[] firstLook= new Integer[]{0, 0, 0, 0};
    private Integer[] secondLook= new Integer[]{0, 0, 0, 0};
    private SharedPreferences sPref;

    @Override
    public void dismiss() {
        super.dismiss();
        onCloseListener.onClose();
    }

    public DialogFinish(OnCloseListener onCloseListener, boolean winUser) {
        this.onCloseListener = onCloseListener;
        this.winUser = winUser;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }


    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.finish_dialog, null);
        sPref = getContext().getSharedPreferences("settingsGameOneOnOne", 0);
        firstUserName = sPref.getString("first_name_user","Первый игрок");
        secondUserName = sPref.getString("second_name_user","Второй игрок");
        colorOne = sPref.getString("mascot_color_one", getResources().getString(R.color.mascot_one));
        colorTwo = sPref.getString("mascot_color_two", getResources().getString(R.color.mascot_two));
        firstLook[0] = sPref.getInt("mascot_one_index_0", 0);
        firstLook[1] = sPref.getInt("mascot_one_index_1", 0);
        firstLook[2] = sPref.getInt("mascot_one_index_2", 0);
        firstLook[3] = sPref.getInt("mascot_one_index_3", 0);
        secondLook[0] = sPref.getInt("mascot_two_index_0", 0);
        secondLook[1] = sPref.getInt("mascot_two_index_1", 0);
        secondLook[2] = sPref.getInt("mascot_two_index_2", 0);
        secondLook[3] = sPref.getInt("mascot_two_index_3", 0);
        if(winUser){
            ((ImageView)view.findViewById(R.id.finish_dialog__img_v_first_mascot_backg)).setColorFilter(Color.parseColor(colorOne), android.graphics.PorterDuff.Mode.SRC_IN);
            ((ImageView)view.findViewById(R.id.finish_dialog__img_v_second_mascot_backg)).setColorFilter(Color.parseColor(colorTwo), android.graphics.PorterDuff.Mode.SRC_IN);
            ((TextView)view.findViewById(R.id.finish_dialog__tv_first_user_name)).setText(firstUserName);
            ((TextView)view.findViewById(R.id.finish_dialog__tv_second_user_name)).setText(secondUserName);
            ((ImageView)view.findViewById(R.id.finish_dialog__img_v_first_mascot_index_0)).setImageResource(firstLook[0]);
            ((ImageView)view.findViewById(R.id.finish_dialog__img_v_first_mascot_index_1)).setImageResource(firstLook[1]);
            ((ImageView)view.findViewById(R.id.finish_dialog__img_v_first_mascot_index_3)).setImageResource(firstLook[3]);
            ((ImageView)view.findViewById(R.id.finish_dialog__img_v_second_mascot_index_0)).setImageResource(secondLook[0]);
            ((ImageView)view.findViewById(R.id.finish_dialog__img_v_second_mascot_index_1)).setImageResource(secondLook[1]);
            ((ImageView)view.findViewById(R.id.finish_dialog__img_v_second_mascot_index_3)).setImageResource(secondLook[3]);
        }else{
            ((ImageView)view.findViewById(R.id.finish_dialog__img_v_first_mascot_backg)).setColorFilter(Color.parseColor(colorTwo), android.graphics.PorterDuff.Mode.SRC_IN);
            ((ImageView)view.findViewById(R.id.finish_dialog__img_v_second_mascot_backg)).setColorFilter(Color.parseColor(colorOne), android.graphics.PorterDuff.Mode.SRC_IN);
            ((TextView)view.findViewById(R.id.finish_dialog__tv_first_user_name)).setText(secondUserName);
            ((TextView)view.findViewById(R.id.finish_dialog__tv_second_user_name)).setText(firstUserName);
            ((ImageView)view.findViewById(R.id.finish_dialog__img_v_first_mascot_index_0)).setImageResource(secondLook[0]);
            ((ImageView)view.findViewById(R.id.finish_dialog__img_v_first_mascot_index_1)).setImageResource(secondLook[1]);
            ((ImageView)view.findViewById(R.id.finish_dialog__img_v_first_mascot_index_3)).setImageResource(secondLook[3]);
            ((ImageView)view.findViewById(R.id.finish_dialog__img_v_second_mascot_index_0)).setImageResource(firstLook[0]);
            ((ImageView)view.findViewById(R.id.finish_dialog__img_v_second_mascot_index_1)).setImageResource(firstLook[1]);
            ((ImageView)view.findViewById(R.id.finish_dialog__img_v_second_mascot_index_3)).setImageResource(firstLook[3]);
        }


        view.findViewById(R.id.dialog_finish__bt_close).setOnClickListener(v -> dismiss());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        return builder.create();
    }
    public interface OnCloseListener {
        void onClose();
    }
}
