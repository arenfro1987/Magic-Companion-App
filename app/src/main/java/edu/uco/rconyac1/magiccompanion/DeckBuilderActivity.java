package edu.uco.rconyac1.magiccompanion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ryan on 11/6/2015.
 */
public class DeckBuilderActivity extends Activity implements OnDataSendToActivity {

    private ListView cardListView;
    private Deck currentDeck;
    private String deckName, search;
    private Button saveButton, searchButton, pricingButton;
    private EditText searchField;
    private ArrayAdapter<String> adapter;
    private CardDetailFragment detailFragment;
    private TextView standardView;
    private TextView modernView;
    private TextView legacyView;
    private TextView vintageView;
    private TextView commanderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_builder);
        cardListView = (ListView)findViewById(R.id.deckBuilderListView);

        standardView =  (TextView)findViewById(R.id.standardView);
        modernView =  (TextView)findViewById(R.id.modernView);
        legacyView =  (TextView)findViewById(R.id.legacyView);
        vintageView =  (TextView)findViewById(R.id.vintageView);
        commanderView =  (TextView)findViewById(R.id.commanderView);

        saveButton = (Button)findViewById(R.id.saveButton);
        searchButton = (Button)findViewById(R.id.deckBuilderSearchButton);
        searchField = (EditText)findViewById(R.id.deckBuilderCardSearchField);
        searchField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(v.getId() == R.id.deckBuilderCardSearchField && !hasFocus) {
                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });
        deckName = getIntent().getStringExtra("deckName");
        if(getIntent().hasExtra("deck")) {
            currentDeck = (Deck) getIntent().getSerializableExtra("deck");
        }
        else {
            currentDeck = new Deck();
        }
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search = searchField.getText().toString();
                try {
                    searchCards(search);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, currentDeck.getNames());
        cardListView.setAdapter(adapter);
        cardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                detailFragment = CardDetailFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putSerializable("card", currentDeck.getCardList().get(position));
                detailFragment.setArguments(bundle);
                detailFragment.show(getFragmentManager(), "card_detail");

            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (saveDecks()) {
                    //successful save
                    Toast toast = Toast.makeText(getApplicationContext(), "Save Successful", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    //unsuccessful save
                    Toast toast = Toast.makeText(getApplicationContext(), "Save Unsuccessful", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        pricingButton = (Button)findViewById(R.id.pricingButton);
        pricingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set card pricing and throw it to the new activity so the user can see the info and prices
                Deck noPricingData = new Deck();
                double lowPrice = 0, averagePrice = 0, highPrice = 0;
                for(int i = 0; i < currentDeck.getSize(); i++) {
                    if(currentDeck.getCardList().get(i).getMyEdition().getLowPrice().compareTo("0.00") == 0) {
                        //card has no low price information add to the no price list
                        //check if it is already in the no data group
                        boolean found = false;
                        for(int n = 0; n < noPricingData.getSize(); n++) {
                            if(currentDeck.getCardList().get(i).getName() == noPricingData.getCardList().get(n).getName()) {
                                found = true;
                            }
                        }
                        if(!found) {
                            noPricingData.addCard(currentDeck.getCardList().get(i));
                        }
                        lowPrice += Double.parseDouble(currentDeck.getCardList().get(i).getMyEdition().getLowPrice());
                    }
                    else {
                        lowPrice += Double.parseDouble(currentDeck.getCardList().get(i).getMyEdition().getLowPrice());
                    }
                    if(currentDeck.getCardList().get(i).getMyEdition().getAvgPrice().compareTo("0.00") == 0) {
                        //card has no average price information add to the no price list
                        boolean found = false;
                        for(int n = 0; n < noPricingData.getSize(); n++) {
                            if(currentDeck.getCardList().get(i).getName() == noPricingData.getCardList().get(n).getName()) {
                                found = true;
                            }
                        }
                        if(!found) {
                            noPricingData.addCard(currentDeck.getCardList().get(i));
                        }
                        averagePrice += Double.parseDouble(currentDeck.getCardList().get(i).getMyEdition().getAvgPrice());
                    }
                    else {
                        averagePrice += Double.parseDouble(currentDeck.getCardList().get(i).getMyEdition().getAvgPrice());
                    }
                    if(currentDeck.getCardList().get(i).getMyEdition().getHighPrice().compareTo("0.00") == 0) {
                        //card has no high price information add to the no price list
                        boolean found = false;
                        for(int n = 0; n < noPricingData.getSize(); n++) {
                            if(currentDeck.getCardList().get(i).getName() == noPricingData.getCardList().get(n).getName()) {
                                found = true;
                            }
                        }
                        if(!found) {
                            noPricingData.addCard(currentDeck.getCardList().get(i));
                        }
                        highPrice += Double.parseDouble(currentDeck.getCardList().get(i).getMyEdition().getHighPrice());
                    }
                    else {
                        highPrice += Double.parseDouble(currentDeck.getCardList().get(i).getMyEdition().getHighPrice());
                    }
                }
                Intent intent = new Intent(DeckBuilderActivity.this, DeckPricingActivity.class);
                intent.putExtra("lowPrice", lowPrice);
                intent.putExtra("averagePrice", averagePrice);
                intent.putExtra("highPrice", highPrice);
                intent.putExtra("noInfo", noPricingData);
                startActivity(intent);
            }
        });
    }

    public void updateLegality()
    {
        boolean commander = true;
        boolean standard = true;
        boolean modern = true;
        boolean legacy = true;
        boolean vintage = true;
        boolean hasCommander = false;

        ArrayList<Card> cards = currentDeck.getCardList();
        for(int x  = 0 ; x < cards.size() ; x++)
        {
            List<String> formatList = Arrays.asList(cards.get(x).getFormats().split(","));
            boolean hadCommander = false;
            boolean hadStandard = false;
            boolean hadVintage = false;
            boolean hadLegacy = false;
            boolean hadModern = false;

            for(int y =0 ; y < formatList.size() ; y++)
            {
                String currentFormat = formatList.get(y);
                if(currentFormat.contains("commander"))
                {
                    hadCommander = true;
                    if(!(currentFormat.contains("legal")))
                    {
                        commander = false;
                    }

                }
                if(currentFormat.contains("standard"))
                {
                    hadStandard = true;
                    if(!(currentFormat.contains("legal")))
                    {
                        standard = false;
                    }

                }
                if(currentFormat.contains("vintage"))
                {
                    hadVintage = true;
                    if(!(currentFormat.contains("legal")))
                    {
                        vintage = false;
                    }

                }
                if(currentFormat.contains("legacy"))
                {
                    hadLegacy = true;
                    if(!(currentFormat.contains("legal")))
                    {
                        legacy = false;
                    }

                }
                if(currentFormat.contains("modern"))
                {
                    hadModern = true;
                    if(!(currentFormat.contains("legal")))
                    {
                        modern = false;
                    }

                }
            }
            if(!(hadCommander))
            {
                commander = false;
            }
            if(!(hadLegacy))
            {
                legacy = false;
            }
            if(!(hadModern))
            {
                modern = false;
            }
            if(!(hadStandard))
            {
                standard = false;
            }
            if(!(hadVintage))
            {
                vintage = false;
            }

            if(cards.get(x).isCommanderStatus())
            {
                hasCommander = true;
            }

        }

        if(cards.size() <= 100 && hasCommander && commander)
        {
            commanderView.setText("Commander:Legal");
        }
        else
        {
            commanderView.setText("Commander:Illegal");
        }

        if(cards.size() <= 60)
        {
            if(vintage)
            {
                vintageView.setText("Vintage:Legal");
            }
            else
            {
                vintageView.setText("Vintage:Illegal");
            }

            if(legacy)
            {
                legacyView.setText("Legacy:Legal");
            }
            else
            {
                legacyView.setText("Legacy:Illegal");
            }

            if(modern)
            {
                modernView.setText("Modern:Legal");
            }
            else
            {
                modernView.setText("Modern:Illegal");
            }

            if(standard)
            {
                standardView.setText("Standard:Legal");
            }
            else
            {
                standardView.setText("Standard:Illegal");
            }
        }



    }

    public boolean saveDecks() {
        //save the decks to the file system
        boolean successful = false;
        try {
            File file = getFileStreamPath(deckName);
            currentDeck.setDeckName(deckName);
            if(file.exists()) {
                file.delete();
                FileOutputStream fileOutput = openFileOutput(deckName, MODE_PRIVATE);
                ObjectOutput output = new ObjectOutputStream(fileOutput);
                output.writeObject(currentDeck);
                successful = true;
            }
            else {
                OutputStream fileOutput = openFileOutput(deckName, MODE_PRIVATE);
                ObjectOutput output = new ObjectOutputStream(fileOutput);
                output.writeObject(currentDeck);
                successful = true;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return successful;
    }

    public void searchCards(String search) throws IOException{
        //use the api to search and open a fragment to give a list of returned results
        new ApiConnector(DeckBuilderActivity.this).execute(new String[]{search});
    }

    public void addCard(Card selectedCard) {
        currentDeck.addCard(selectedCard);
        adapter.clear();
        adapter.addAll(currentDeck.getNames());
        adapter.notifyDataSetChanged();
        updateLegality();


    }

    public void addCard(Card seletedCard, int numberToAdd) {
        for(int i = 0; i < currentDeck.getSize(); i++) {
            if(currentDeck.getCardList().get(i).getName().compareTo(seletedCard.getName()) == 0) {
                int value = currentDeck.getCardList().get(i).getNumberInDeck() + numberToAdd;
                currentDeck.getCardList().get(i).setNumberInDeck(value);
                detailFragment.dismiss();
            }
        }
        adapter.clear();
        adapter.addAll(currentDeck.getNames());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void sendData(ArrayList<Card> cardList) {
        //launch a new fragment to let the player scroll through the returned cards and select them
        //as well as allow them to change how many are put in the deck
//        searchedcards = cardList;
//        for(int i = 0; i < searchedcards.size(); i++) {
//            currentDeck.addCard(searchedcards.get(i));
//        }
//        deckAdapter.clear();
//        ((DeckAdapter)cardListView.getAdapter()).notifiyDataSetChanged();
//        searchedcards = null;
        Bundle bundle = new Bundle();
        Deck returnedCards = new Deck();
        for(int n = 0; n < cardList.size(); n++) {
            if(cardList.get(n).getMyEdition().getLowPrice() == null) {
                cardList.get(n).getMyEdition().setLowPrice("0.00");
            }
            if(cardList.get(n).getMyEdition().getAvgPrice() == null) {
                cardList.get(n).getMyEdition().setAvgPrice("0.00");
            }
            if(cardList.get(n).getMyEdition().getHighPrice() == null) {
                cardList.get(n).getMyEdition().setHighPrice("0.00");
            }
            if(cardList.get(n).getMyEdition().getAvgFoilPrice() == null) {
                cardList.get(n).getMyEdition().setAvgFoilPrice("0.00");
            }
        }
        for(int i = 0; i < cardList.size(); i++) {
            returnedCards.addCard(cardList.get(i));
        }
        bundle.putSerializable("deck", returnedCards);
        CardSearchFragment cardSearchFragment = new CardSearchFragment();
        cardSearchFragment.setArguments(bundle);
        cardSearchFragment.show(getFragmentManager(), "returned_results");

    }

    public void analysisClick(View v) {
        Intent intent = new Intent(DeckBuilderActivity.this, DeckAnalysisActivity.class);
        intent.putExtra("deck", currentDeck);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        Intent intent;
        switch(id) {
            case R.id.life_counter:
                intent = new Intent(DeckBuilderActivity.this,LifeCounterMain.class);
                startActivity(intent);
                return true;
            case R.id.dice_roller:
                intent = new Intent(DeckBuilderActivity.this, DiceRollerActivity.class);
                startActivity(intent);
                return true;
            case R.id.rulebook:
                intent = new Intent(DeckBuilderActivity.this,RuleBook.class);
                startActivity(intent);
                return true;
            case R.id.deck_builder:
                intent = new Intent(DeckBuilderActivity.this, DeckBuilderActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
