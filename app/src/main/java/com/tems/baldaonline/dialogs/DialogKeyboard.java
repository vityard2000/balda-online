package com.tems.baldaonline.dialogs;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.tems.baldaonline.R;

public class DialogKeyboard extends AppCompatDialogFragment implements View.OnClickListener {

    private static final String myTag = "debugTag";
    private Character letter;
    private OnClickKyeListener onClickKyeListener;

    public DialogKeyboard(OnClickKyeListener onClickKyeListener){
        this.onClickKyeListener = onClickKyeListener;
    }

    public void onResume() {
        super.onResume();
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        onClickKyeListener.onClickKye(letter);

    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        letter = null;
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.keyboard, null);
        Button btA =  view.findViewById(R.id.keyboard__bt_A);
        btA.setOnClickListener(this);
        Button btB =  view.findViewById(R.id.keyboard__bt_B);
        btB.setOnClickListener(this);
        Button btV =  view.findViewById(R.id.keyboard__bt_V);
        btV.setOnClickListener(this);
        Button btG =  view.findViewById(R.id.keyboard__bt_G);
        btG.setOnClickListener(this);
        Button btD =  view.findViewById(R.id.keyboard__bt_D);
        btD.setOnClickListener(this);
        Button btE =  view.findViewById(R.id.keyboard__bt_E);
        btE.setOnClickListener(this);
        Button btZH = view.findViewById(R.id.keyboard__bt_ZH);
        btZH.setOnClickListener(this);
        Button btZ =  view.findViewById(R.id.keyboard__bt_Z);
        btZ.setOnClickListener(this);
        Button btI = view.findViewById(R.id.keyboard__bt_I);
        btI.setOnClickListener(this);
        Button btII =  view.findViewById(R.id.keyboard__bt_II);
        btII.setOnClickListener(this);
        Button btK =  view.findViewById(R.id.keyboard__bt_K);
        btK.setOnClickListener(this);
        Button btL =  view.findViewById(R.id.keyboard__bt_L);
        btL.setOnClickListener(this);
        Button btM =  view.findViewById(R.id.keyboard__bt_M);
        btM.setOnClickListener(this);
        Button btN =  view.findViewById(R.id.keyboard__bt_N);
        btN.setOnClickListener(this);
        Button btO =  view.findViewById(R.id.keyboard__bt_O);
        btO.setOnClickListener(this);
        Button btP =  view.findViewById(R.id.keyboard__bt_P);
        btP.setOnClickListener(this);
        Button btR =  view.findViewById(R.id.keyboard__bt_R);
        btR.setOnClickListener(this);
        Button btS =  view.findViewById(R.id.keyboard__bt_S);
        btS.setOnClickListener(this);
        Button btT = view.findViewById(R.id.keyboard__bt_T);
        btT.setOnClickListener(this);
        Button btU =  view.findViewById(R.id.keyboard__bt_U);
        btU.setOnClickListener(this);
        Button btF =  view.findViewById(R.id.keyboard__bt_F);
        btF.setOnClickListener(this);
        Button btKH =  view.findViewById(R.id.keyboard__bt_KH);
        btKH.setOnClickListener(this);
        Button btTS=  view.findViewById(R.id.keyboard__bt_TS);
        btTS.setOnClickListener(this);
        Button btCH =  view.findViewById(R.id.keyboard__bt_CH);
        btCH.setOnClickListener(this);
        Button btSH = view.findViewById(R.id.keyboard__bt_SH);
        btSH.setOnClickListener(this);
        Button btSHCH =  view.findViewById(R.id.keyboard__bt_SHCH);
        btSHCH.setOnClickListener(this);
        Button btY =  view.findViewById(R.id.keyboard__bt_Y);
        btY.setOnClickListener(this);
        Button btSoftZnak =  view.findViewById(R.id.keyboard__bt_SOFT_ZNAK);
        btSoftZnak.setOnClickListener(this);
        Button btEE =  view.findViewById(R.id.keyboard__bt_EE);
        btEE.setOnClickListener(this);
        Button btHardZnak =  view.findViewById(R.id.keyboard__bt_HARD_ZNAK);
        btHardZnak.setOnClickListener(this);
        Button btIU = view.findViewById(R.id.keyboard__bt_IU);
        btIU.setOnClickListener(this);
        Button btIA = view.findViewById(R.id.keyboard__bt_IA);
        btIA.setOnClickListener(this);
        Button btCancel = view.findViewById(R.id.keyboard__bt_cancel);
        btCancel.setOnClickListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        return builder.create();
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.keyboard__bt_A:
                letter = '??';
                break;
            case R.id.keyboard__bt_B:
                letter = '??';
                break;
            case R.id.keyboard__bt_V:
                letter = '??';
                break;
            case R.id.keyboard__bt_G:
                letter = '??';
                break;
            case R.id.keyboard__bt_D:
                letter = '??';
                break;
            case R.id.keyboard__bt_E:
                letter = '??';
                break;
            case R.id.keyboard__bt_ZH:
                letter = '??';
                break;
            case R.id.keyboard__bt_Z:
                letter = '??';
                break;
            case R.id.keyboard__bt_I:
                letter = '??';
                break;
            case R.id.keyboard__bt_II:
                letter = '??';
                break;
            case R.id.keyboard__bt_K:
                letter = '??';
                break;
            case R.id.keyboard__bt_L:
                letter = '??';
                break;
            case R.id.keyboard__bt_M:
                letter = '??';
                break;
            case R.id.keyboard__bt_N:
                letter = '??';
                break;
            case R.id.keyboard__bt_O:
                letter = '??';
                break;
            case R.id.keyboard__bt_P:
                letter = '??';
                break;
            case R.id.keyboard__bt_R:
                letter = '??';
                break;
            case R.id.keyboard__bt_S:
                letter = '??';
                break;
            case R.id.keyboard__bt_T:
                letter = '??';
                break;
            case R.id.keyboard__bt_U:
                letter = '??';
                break;
            case R.id.keyboard__bt_F:
                letter = '??';
                break;
            case R.id.keyboard__bt_KH:
                letter = '??';
                break;
            case R.id.keyboard__bt_TS:
                letter = '??';
                break;
            case R.id.keyboard__bt_CH:
                letter = '??';
                break;
            case R.id.keyboard__bt_SH:
                letter = '??';
                break;
            case R.id.keyboard__bt_SHCH:
                letter = '??';
                break;
            case R.id.keyboard__bt_Y:
                letter = '??';
                break;
            case R.id.keyboard__bt_SOFT_ZNAK:
                letter = '??';
                break;
            case R.id.keyboard__bt_EE:
                letter = '??';
                break;
            case R.id.keyboard__bt_HARD_ZNAK:
                letter = '??';
                break;
            case R.id.keyboard__bt_IU:
                letter = '??';
                break;
            case R.id.keyboard__bt_IA:
                letter = '??';
                break;
            default:
                letter = null;
        }
        dismiss();
    }
    public interface OnClickKyeListener{
        void onClickKye(Character c);
    }
}
