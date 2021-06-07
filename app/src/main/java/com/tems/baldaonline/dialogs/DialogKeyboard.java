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
                letter = 'А';
                break;
            case R.id.keyboard__bt_B:
                letter = 'Б';
                break;
            case R.id.keyboard__bt_V:
                letter = 'В';
                break;
            case R.id.keyboard__bt_G:
                letter = 'Г';
                break;
            case R.id.keyboard__bt_D:
                letter = 'Д';
                break;
            case R.id.keyboard__bt_E:
                letter = 'Е';
                break;
            case R.id.keyboard__bt_ZH:
                letter = 'Ж';
                break;
            case R.id.keyboard__bt_Z:
                letter = 'З';
                break;
            case R.id.keyboard__bt_I:
                letter = 'И';
                break;
            case R.id.keyboard__bt_II:
                letter = 'Й';
                break;
            case R.id.keyboard__bt_K:
                letter = 'К';
                break;
            case R.id.keyboard__bt_L:
                letter = 'Л';
                break;
            case R.id.keyboard__bt_M:
                letter = 'М';
                break;
            case R.id.keyboard__bt_N:
                letter = 'Н';
                break;
            case R.id.keyboard__bt_O:
                letter = 'О';
                break;
            case R.id.keyboard__bt_P:
                letter = 'П';
                break;
            case R.id.keyboard__bt_R:
                letter = 'Р';
                break;
            case R.id.keyboard__bt_S:
                letter = 'С';
                break;
            case R.id.keyboard__bt_T:
                letter = 'Т';
                break;
            case R.id.keyboard__bt_U:
                letter = 'У';
                break;
            case R.id.keyboard__bt_F:
                letter = 'Ф';
                break;
            case R.id.keyboard__bt_KH:
                letter = 'Х';
                break;
            case R.id.keyboard__bt_TS:
                letter = 'Ц';
                break;
            case R.id.keyboard__bt_CH:
                letter = 'Ч';
                break;
            case R.id.keyboard__bt_SH:
                letter = 'Ш';
                break;
            case R.id.keyboard__bt_SHCH:
                letter = 'Щ';
                break;
            case R.id.keyboard__bt_Y:
                letter = 'Ы';
                break;
            case R.id.keyboard__bt_SOFT_ZNAK:
                letter = 'Ь';
                break;
            case R.id.keyboard__bt_EE:
                letter = 'Э';
                break;
            case R.id.keyboard__bt_HARD_ZNAK:
                letter = 'Ъ';
                break;
            case R.id.keyboard__bt_IU:
                letter = 'Ю';
                break;
            case R.id.keyboard__bt_IA:
                letter = 'Я';
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
