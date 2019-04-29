package com.example.hendrixassassins.uipages.DialogBoxes;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.hendrixassassins.R;


public class PopupChangeAgentName extends AppCompatDialogFragment {
    private DialogListener listener;
    private TextView changeAgent;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_change_agent_name, null);
        builder.setView(view)
                .setTitle("Change Name")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String updateName = changeAgent.getText().toString();
                        listener.changeName(updateName);


                    }
                });
        changeAgent = view.findViewById(R.id.newAgentName);

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
        void changeName(String updatedName);
    }
}
