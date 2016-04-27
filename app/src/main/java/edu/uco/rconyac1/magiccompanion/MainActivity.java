package edu.uco.rconyac1.magiccompanion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button lifeCounterLauncher, cardInfoLauncher, diceRollerLauncher, rulebookLauncher, cardPricingLauncher, deckBuilderLauncher, sharedDecksLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //associate buttons
        lifeCounterLauncher = (Button)findViewById(R.id.lifeCounterButton);
        cardInfoLauncher = (Button)findViewById(R.id.cardInfoButton);
        diceRollerLauncher = (Button)findViewById(R.id.diceRollerButton);
        rulebookLauncher = (Button)findViewById(R.id.rulebookButton);
        //ardPricingLauncher = (Button)findViewById(R.id.cardPricingButton);
        deckBuilderLauncher = (Button)findViewById(R.id.deckBuilderButton);
        //sharedDecksLauncher = (Button)findViewById(R.id.sharedDecksButton);

        //set onClickListeners
        lifeCounterLauncher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LifeCounterMain.class);
                startActivity(intent);
            }
        });

        cardInfoLauncher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, cardSearchActivity.class);
                startActivity(intent);
            }
        });

        diceRollerLauncher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DiceRollerActivity.class);
                startActivity(intent);
            }
        });

        rulebookLauncher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RuleBook.class);
                startActivity(intent);

            }
        });
/*
        cardPricingLauncher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
*/
        deckBuilderLauncher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeckListActivity.class);
                startActivity(intent);
            }
        });
/*
        sharedDecksLauncher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }

    public void rulingButtonClick(View v) {
        Intent intent = new Intent(MainActivity.this, RulingSearchActivity.class);
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
                intent = new Intent(MainActivity.this,LifeCounterMain.class);
                startActivity(intent);
                return true;
            case R.id.dice_roller:
                intent = new Intent(MainActivity.this, DiceRollerActivity.class);
                startActivity(intent);
                return true;
            case R.id.rulebook:
                intent = new Intent(MainActivity.this,RuleBook.class);
                startActivity(intent);
                return true;
            case R.id.deck_builder:
                intent = new Intent(MainActivity.this, DeckBuilderActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
