package com.example.hendrixassassins.uipages.DialogBoxes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.hendrixassassins.R;

public class PopupChangeAgentStatus extends DialogFragment {
    private DialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        //View view = inflater.inflate(R.layout.empty_layout, null);
        builder.setTitle("Change Status")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setItems(getResources().getStringArray(R.array.popup_changeStatus), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String[] array = getResources().getStringArray(R.array.popup_changeStatus);
                        listener.changeStatus(array[which]);
                    }
                });

        return builder.create();

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
        Log.d("LISTENER:", listener.toString());

    }

    public interface DialogListener{
        void changeStatus(String updatedName);
    }
}
