package edu.uco.rconyac1.magiccompanion;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ryan on 11/6/2015.
 */
public class DeckAdapter extends ArrayAdapter {
    private ArrayList<DataSetObserver> observers = new ArrayList<>();

    public DeckAdapter(Context context, ArrayList<Card> cards) {
        super(context, 0, cards);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Card card = (Card) getItem(position);


        // Check if an existing view is being reused, otherwise inflate the view
//        TextView name = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_item, parent, false);
            TextView name = (TextView) convertView.findViewById(R.id.card_name);
//            TextView number = (TextView) convertView.findViewById(R.id.card_number);
            // Populate the data into the template view using the data object
            name.setText(card.getName());
//            number.setText(String.valueOf(card.getNumberInDeck()));
        }
        // Lookup view for data population

        // Return the completed view to render on screen
        return convertView;
    }

    public void registerDataSetObserver(DataSetObserver observer) {
        observers.add(observer);
    }

    public void notifiyDataSetChanged() {
        for(DataSetObserver observer: observers) {
            observer.onChanged();
        }
    }
}
