package edu.uco.rconyac1.magiccompanion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vdpotvin on 10/27/15.
 */
public class RollAdapter extends ArrayAdapter {

        public RollAdapter(Context context, ArrayList<Roll> rolls) {
            super(context, 0, rolls);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position

            Roll roll = (Roll) getItem(position);


            // Check if an existing view is being reused, otherwise inflate the view

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_roll, parent, false);
            }
            // Lookup view for data population
            TextView diceType = (TextView) convertView.findViewById(R.id.dice_type);
            TextView rollResult = (TextView) convertView.findViewById(R.id.roll_value);
            // Populate the data into the template view using the data object
            diceType.setText(roll.getDiceType());
            rollResult.setText(roll.getRoll());
            // Return the completed view to render on screen
            return convertView;
        }

}
