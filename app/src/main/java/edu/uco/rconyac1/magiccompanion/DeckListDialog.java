package edu.uco.rconyac1.magiccompanion;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by ryan on 11/9/2015.
 */
public class DeckListDialog extends DialogFragment {
    public DeckListDialog() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        final int position = getArguments().getInt("position");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Delete Deck");
        alertDialogBuilder.setMessage("Press OK to delete.");
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((DeckListActivity)getActivity()).deleteDeck(position);
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", null);
        return alertDialogBuilder.create();
    }
}
