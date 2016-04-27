package edu.uco.rconyac1.magiccompanion;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class cardDetail extends Activity {

    ImageView cardImage;
    TextView cardName;
    TextView set;
    TextView desc;
    TextView rarity;
    TextView types;
    TextView cost;
    TextView flavor;
    TextView formats;
    TextView lowPrice;
    TextView avgPrice;
    TextView highPrice;
    TextView foilPrice;
    Card theCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);

        cardImage =(ImageView) findViewById(R.id.cardImage);
        cardName = (TextView) findViewById(R.id.cardNameView);
        set = (TextView) findViewById(R.id.setView);
        desc = (TextView) findViewById(R.id.cardDescView);
        rarity = (TextView) findViewById(R.id.rarityView);
        types = (TextView) findViewById(R.id.typesView);
        cost = (TextView) findViewById(R.id.costView);
        flavor = (TextView) findViewById(R.id.flavorView);
        formats = (TextView) findViewById(R.id.formatsView);
        lowPrice = (TextView) findViewById(R.id.lowCost);
        avgPrice = (TextView) findViewById(R.id.avgCost);
        highPrice = (TextView) findViewById(R.id.highCost);
        foilPrice = (TextView) findViewById(R.id.foilCost);

        theCard = (Card)getIntent().getSerializableExtra("card");

        cardName.setText(theCard.getName());
        set.setText("Set:"+theCard.getMyEdition().getSet());
        desc.setText("Description\n--------------------\n"+theCard.getText());
        rarity.setText("Rarity:"+theCard.getMyEdition().getRarity());
        for(int x = 0; x < theCard.getTypes().size() ; x++)
        {
            types.append(" "+theCard.getTypes().get(x));
            if(x+1 < theCard.getTypes().size())
            {
                types.append(",");
            }
        }
        if(theCard.getCost() != null)
        {
            cost.setText("Cost:"+theCard.getCost());
        }
        else
        {
            cost.setText("");
        }

        if(theCard.getMyEdition().getFlavor() != null)
        {
            flavor.setText("Flavor Text\n--------------------\n"+theCard.getMyEdition().getFlavor());
        }
        else
        {
            flavor.setText("");
        }
        if(theCard.getMyEdition().getLowPrice() != null)
        {
            lowPrice.setText("Low:$"+theCard.getMyEdition().getLowPrice());
        }
        else
        {
            lowPrice.setText("");
        }
        if(theCard.getMyEdition().getAvgPrice() != null)
        {
            avgPrice.setText("Avg:$"+theCard.getMyEdition().getAvgPrice());
        }
        else
        {
            avgPrice.setText("");
        }
        if(theCard.getMyEdition().getHighPrice() != null)
        {
            highPrice.setText("High:$"+theCard.getMyEdition().getHighPrice());
        }
        else
        {
            highPrice.setText("");
        }
        if(theCard.getMyEdition().getAvgFoilPrice() != null)
        {
            foilPrice.setText("Foil:$"+theCard.getMyEdition().getAvgFoilPrice());
        }
        else
        {
            foilPrice.setText("");
        }

        String format = theCard.getFormats();
        format = format.replace("{","");
        format = format.replace("}","");
        format = format.replace(",","\n");
        formats.setText(format);

        new ImageLoadTask(theCard.getMyEdition().getImageURL(), cardImage).execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }

    }
}
