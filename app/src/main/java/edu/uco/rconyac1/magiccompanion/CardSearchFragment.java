package edu.uco.rconyac1.magiccompanion;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by ryan on 11/16/2015.
 */
public class CardSearchFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        Bundle bundle = getArguments();
        final Deck deck = (Deck)bundle.getSerializable("deck");
        String [] cardNames = new String[deck.getSize()];
        for(int i = 0; i < deck.getSize(); i++) {
            cardNames[i] = deck.getCardList().get(i).getName() + " | " + deck.getCardList().get(i).getMyEdition().getSet();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Results");
//        builder.setMessage("Select a card to add it to the deck");
        builder.setItems(cardNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((DeckBuilderActivity)getActivity()).addCard(deck.getCardList().get(which));
            }
        });
        return builder.create();
    }
}
