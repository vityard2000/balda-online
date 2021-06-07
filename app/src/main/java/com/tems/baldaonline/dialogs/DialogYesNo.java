package com.tems.baldaonline.dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.tems.baldaonline.R;

public class DialogYesNo  extends AppCompatDialogFragment {
    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //getDialog().getWindow().setLayout(-2, -2);

    }

    OnClickListener onClickListenerNo;
    OnClickListener onClickListenerYes;
    String message;
    public DialogYesNo(OnClickListener onClickListenerNo, OnClickListener onClickListenerYes , String message) {
        this.onClickListenerNo = onClickListenerNo;
        this.onClickListenerYes = onClickListenerYes;
        this.message = message;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_yes_no, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        view.findViewById(R.id.dialog_yes_no__bt_yes).setOnClickListener(v -> {
            dismiss();
            if(onClickListenerYes!=null) onClickListenerYes.onClick();
        });
        view.findViewById(R.id.dialog_yes_no__bt_no).setOnClickListener(v -> {
            dismiss();
            if(onClickListenerNo!=null) onClickListenerNo.onClick();
        });
        ((TextView)view.findViewById(R.id.dialog_yes_no__tv)).setText(message);
        builder.setView(view);
        return builder.create();
    }

    public interface OnClickListener {
        void onClick();
    }
}
