package com.example.hendrixassassins.uipages.DialogBoxes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;

import com.example.hendrixassassins.R;

// https://developer.android.com/guide/topics/ui/dialogs
public class ChangeNameDialogFragment extends DialogFragment {
    NoticeDialogListener listener;
    EditText newName;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException( " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_change_agent_name, null))
                .setPositiveButton("save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String name = newName.getText().toString();
                        Log.d("NAME: ", name);
                        listener.onDialogPositiveClick(ChangeNameDialogFragment.this,
                                newName.getText().toString());
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogNegativeClick(ChangeNameDialogFragment.this);
                    }
                });
        Dialog dialog = builder.create();
        dialog.setContentView(R.layout.dialog_change_agent_name);
        newName = dialog.findViewById(R.id.newAgentName);
        return  builder.create();
    }

    public interface NoticeDialogListener {
         void onDialogPositiveClick(DialogFragment dialog, String updatedName);
         void onDialogNegativeClick(DialogFragment dialog);
    }
}
