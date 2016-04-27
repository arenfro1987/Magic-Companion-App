package edu.uco.rconyac1.magiccompanion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class cardSearchActivity extends Activity implements OnDataSendToActivity{

    private EditText searchField;
    private ImageButton searchButton;
    private ListView searchResults;
    private ApiConnector theConnector;
    private ArrayList<Card> cardList;
    ArrayList<String> cardNames;
    //private String[] cardNames = new String[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_search);

        searchButton = (ImageButton) findViewById(R.id.searchButton);
        searchField = (EditText) findViewById(R.id.cardSearchField);
        searchResults = (ListView) findViewById(R.id.searchResults);


        cardList = new ArrayList();


        searchField.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    try {
                        searchCards();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    searchCards();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        searchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(cardSearchActivity.this, cardDetail.class);
                i.putExtra("card", cardList.get(position));
                startActivity(i);
            }
        });

    }


    public void searchCards() throws IOException {

       new ApiConnector(this).execute(new String[]{searchField.getText().toString()});
    }

    public void updateResults()
    {
        cardNames = new ArrayList<>();
        for(int x = 0; x < cardList.size() ; x++)
        {
            cardNames.add("Card:" + cardList.get(x).getName() + " Set:" + cardList.get(x).getMyEdition().getSet());

        }

        searchResults.setAdapter(new ArrayAdapter<>(cardSearchActivity.this,
                R.layout.listview_textview,cardNames));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card_search, menu);
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

    @Override
    public void sendData(ArrayList<Card> cardList) {
        this.cardList = cardList;

        updateResults();
    }
}
