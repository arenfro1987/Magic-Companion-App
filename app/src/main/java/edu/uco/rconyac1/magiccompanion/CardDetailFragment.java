package edu.uco.rconyac1.magiccompanion;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by ryan on 11/16/2015.
 */
public class CardDetailFragment extends DialogFragment {

    static CardDetailFragment newInstance() {
        return new CardDetailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        final Card card = (Card)bundle.getSerializable("card");
        View v = inflater.inflate(R.layout.card_detail_dialog, container, false);
        View cardImage = v.findViewById(R.id.webView);
        ((WebView)cardImage).loadUrl(card.getMyEdition().getImageURL());
        final View number = v.findViewById(R.id.numberToAdd);
        View addButton = v.findViewById(R.id.addToDeckButton);
        ((Button)addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberToAdd = Integer.parseInt(((EditText)number).getText().toString());
                ((DeckBuilderActivity)getActivity()).addCard(card, numberToAdd);
            }
        });
        return v;
    }
}
