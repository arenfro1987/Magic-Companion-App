package edu.uco.rconyac1.magiccompanion;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class LifeCounter extends Activity {
    Button plus;
    Button minus;
    Button rules;
    Button search;
    Button ruleBookButton;
    Button dice;
    ImageButton poison;
    ImageButton health;
    ImageButton commander;
    TextView counter;
    GridLayout playerGrid;
    ArrayList<player> players;
    Button player1;
    Button player2;
    Button player3;
    Button player4;
    Button player5;
    Button player6;
    Button player7;
    Button player8;
    ImageView typeImage;


    int activePlayerNumber;
    String countType;

    String type;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_counter);

        counter = (TextView) findViewById(R.id.countView);
        plus = (Button) findViewById(R.id.plusButton);
        minus = (Button) findViewById(R.id.minusButton);
        rules = (Button) findViewById(R.id.ruleBookButton);
        health = (ImageButton) findViewById(R.id.healthButton);
        commander = (ImageButton) findViewById(R.id.commanderDamageButton);
        poison = (ImageButton) findViewById(R.id.infectButton);
        dice = (Button) findViewById(R.id.diceRollerButton);
        search = (Button) findViewById(R.id.cardSearchButton);
        playerGrid = (GridLayout) findViewById(R.id.playerGrid);
        typeImage = (ImageView) findViewById(R.id.imageView);
        player1 = (Button) findViewById(R.id.player1Button);
        player2 = (Button) findViewById(R.id.player2Button);
        player3 = (Button) findViewById(R.id.player3Button);
        player4 = (Button) findViewById(R.id.player4Button);
        player5 = (Button) findViewById(R.id.player5Button);
        player6 = (Button) findViewById(R.id.player6Button);
        player7 = (Button) findViewById(R.id.player7Button);
        player8 = (Button) findViewById(R.id.player8Button);
        ruleBookButton = (Button) findViewById(R.id.ruleBookButton);


        activePlayerNumber =  0;
        countType = "health";
        players = new ArrayList<>();


        type = getIntent().getStringExtra("type");
        count = getIntent().getIntExtra("count", 2);


        typeImage.setBackgroundResource(R.drawable.health);

        if(type.equals("commander"))
        {
            createPlayers(40);
            commander.setVisibility(View.VISIBLE);
        }
        else
        {
            createPlayers(20);
        }

        commander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countType = "comm";
                counter.setText(Integer.toString(players.get(activePlayerNumber).getComDamage()));
                typeImage.setBackgroundResource(R.drawable.commandermini);
            }
        });
        poison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countType = "infect";
                counter.setText(Integer.toString(players.get(activePlayerNumber).getPoison()));
                typeImage.setBackgroundResource(R.drawable.poison);
            }
        });
        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            countType = "health";
                counter.setText(Integer.toString(players.get(activePlayerNumber).getHealth()));
                typeImage.setBackgroundResource(R.drawable.health);
            }
        });

        ruleBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LifeCounter.this,RuleBook.class);
                startActivity(intent);
            }
        });

        dice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LifeCounter.this, DiceRollerActivity.class);
                startActivity(intent);
            }
        });

        player1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activePlayerNumber = 0;
                setCount();
            }
        });
        player2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activePlayerNumber = 1;
                setCount();
            }
        });
        player3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activePlayerNumber = 2;
                setCount();
            }
        });
        player4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activePlayerNumber = 3;
                setCount();
            }
        });
        player5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activePlayerNumber = 4;
                setCount();
            }
        });
        player6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activePlayerNumber = 5;
                setCount();
            }
        });
        player7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activePlayerNumber = 6;
                setCount();
            }
        });
        player8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activePlayerNumber = 7;
                setCount();
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(countType){
                    case "health" :
                        players.get(activePlayerNumber).setHealth(players.get(activePlayerNumber).getHealth()+1);
                        counter.setText(Integer.toString(players.get(activePlayerNumber).getHealth()));
                    break;
                    case "infect" :
                        players.get(activePlayerNumber).setPoison(players.get(activePlayerNumber).getPoison() + 1);
                        counter.setText(Integer.toString(players.get(activePlayerNumber).getPoison()));
                        break;
                    case "comm" :
                        players.get(activePlayerNumber).setComDamage(players.get(activePlayerNumber).getComDamage() + 1);
                        counter.setText(Integer.toString(players.get(activePlayerNumber).getComDamage()));
                        break;
                }
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(countType){
                    case "health" :
                        players.get(activePlayerNumber).setHealth(players.get(activePlayerNumber).getHealth()-1);
                        counter.setText(Integer.toString(players.get(activePlayerNumber).getHealth()));
                        break;
                    case "infect" :
                        players.get(activePlayerNumber).setPoison(players.get(activePlayerNumber).getPoison() - 1);
                        counter.setText(Integer.toString(players.get(activePlayerNumber).getPoison()));
                        break;
                    case "comm" :
                        players.get(activePlayerNumber).setComDamage(players.get(activePlayerNumber).getComDamage() - 1);
                        counter.setText(Integer.toString(players.get(activePlayerNumber).getComDamage()));
                        break;
                }
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LifeCounter.this, cardSearchActivity.class);
                startActivity(intent);

            }
        });
    }

    public void setCount()
    {
        switch(countType){
            case "health" :
                counter.setText(Integer.toString(players.get(activePlayerNumber).getHealth()));
                break;
            case "infect" :
                counter.setText(Integer.toString(players.get(activePlayerNumber).getPoison()));
                break;
            case "comm" :
                counter.setText(Integer.toString(players.get(activePlayerNumber).getComDamage()));
                break;
        }
    }

    public void createPlayers(int health)
    {
        for(int x = 0; x < count ; x++)
        {
            if(x == 0)
            {
                players.add(new player(health,0,true,0,x));
                player1.setVisibility(View.VISIBLE);
            }
            else {
                players.add(new player(health, 0, false, 0, x));
                if(x==1)
                {
                    player2.setVisibility(View.VISIBLE);
                }
                else if(x==2)
                {
                    player3.setVisibility(View.VISIBLE);
                }
                else if(x==3)
                {
                    player4.setVisibility(View.VISIBLE);
                }
                else if(x==4)
                {
                    player5.setVisibility(View.VISIBLE);
                }
                else if(x==5)
                {
                    player6.setVisibility(View.VISIBLE);
                }
                else if(x==6)
                {
                    player7.setVisibility(View.VISIBLE);
                }
                else if(x==7)
                {
                    player8.setVisibility(View.VISIBLE);
                }

            }
        }
        setCount();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //called during onPause to save data
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("activePlayerNumber", activePlayerNumber);
        savedInstanceState.putInt("count", count);
        savedInstanceState.putString("countType", countType);
        savedInstanceState.putString("type", type);
        savedInstanceState.putSerializable("players", players);
    }

    //called during onResume to retrieve data
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        type = savedInstanceState.getString("type");
        activePlayerNumber = savedInstanceState.getInt("activePlayerNumber");
        count = savedInstanceState.getInt("count");
        countType = savedInstanceState.getString("countType");
        players = (ArrayList) savedInstanceState.getSerializable("players");

        if(type.equals("commander"))
        {
            createPlayers(40);
            commander.setVisibility(View.VISIBLE);
        }
        else
        {
            createPlayers(20);
        }
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
                intent = new Intent(LifeCounter.this,LifeCounterMain.class);
                startActivity(intent);
                return true;
            case R.id.dice_roller:
                intent = new Intent(LifeCounter.this, DiceRollerActivity.class);
                startActivity(intent);
                return true;
            case R.id.rulebook:
                intent = new Intent(LifeCounter.this,RuleBook.class);
                startActivity(intent);
                return true;
            case R.id.deck_builder:
                intent = new Intent(LifeCounter.this, DeckBuilderActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
