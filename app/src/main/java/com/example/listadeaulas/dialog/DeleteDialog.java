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
import com.example.listadeaulas.data.Class;

public class DeleteDialog extends DialogFragment implements DialogInterface.OnClickListener {

    private Class aClass;
    private OnDeleteListener listener;

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which == DialogInterface.BUTTON_POSITIVE){
            listener.onDelete(aClass);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getResources().getString(R.string.deleteMessage) + aClass.getTitle() + "?");
        builder.setPositiveButton("Sim", this);
        builder.setNegativeButton("Não", this);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (!(context instanceof OnDeleteListener)){
            throw new RuntimeException("Must implement DialogFragment.OnDeleteListener interface.");
        }
        this.listener = (OnDeleteListener) context;
    }

    public void setaClass(Class aClass){
        this.aClass = aClass;
    }

    public interface OnDeleteListener {
        public void onDelete(Class aClass);
    }
}
