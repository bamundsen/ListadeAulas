package com.example.listadeaulas.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.listadeaulas.R;

public class LinkDialog extends DialogFragment implements DialogInterface.OnClickListener {

    private String title;
    private String link;
    private OnConfirmListener listener;

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which == DialogInterface.BUTTON_POSITIVE){
            listener.onConfirm(title, link);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getResources().getString(R.string.linkMessage) + title + "?");
        builder.setPositiveButton("Sim", this);
        builder.setNegativeButton("Não", this);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (!(context instanceof LinkDialog.OnConfirmListener)){
            throw new RuntimeException("Must implement DialogFragment.OnConfirmListener interface.");
        }
        this.listener = (LinkDialog.OnConfirmListener) context;
    }

    public void setAttributes(String title, String link){
        this.title = title;
        this.link = link;
    }

    public interface OnConfirmListener {
       public void onConfirm(String title, String link);
    }
}
