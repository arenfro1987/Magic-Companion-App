package edu.uco.rconyac1.magiccompanion;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by ryan on 11/24/2015.
 */
public class DeckPricingActivity extends Activity{
    private TextView lowPriceText, averagePriceText, highPriceText;
    private double lowPrice, averagePrice, highPrice;
    private Deck noPricingInfo;
    private ListView noPricingListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priced_deck);
        lowPriceText = (TextView)findViewById(R.id.lowPriceTextView);
        averagePriceText = (TextView)findViewById(R.id.averagePriceTextView);
        highPriceText = (TextView)findViewById(R.id.highPriceTextView);
        lowPrice = getIntent().getDoubleExtra("lowPrice", 0);
        averagePrice = getIntent().getDoubleExtra("averagePrice", 0);
        highPrice = getIntent().getDoubleExtra("highPrice", 0);
        lowPriceText.setText(String.valueOf(lowPrice));
        averagePriceText.setText(String.valueOf(averagePrice));
        highPriceText.setText(String.valueOf(highPrice));
        noPricingInfo = (Deck)getIntent().getSerializableExtra("noInfo");
        noPricingListView = (ListView)findViewById(R.id.noPricingInfoListView);
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, noPricingInfo.getNames());
        noPricingListView.setAdapter(adapter);
    }
}
