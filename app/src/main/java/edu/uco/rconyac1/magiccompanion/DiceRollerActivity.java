package edu.uco.rconyac1.magiccompanion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DiceRollerActivity extends Activity {

    private TextView currentRoll;
    private ArrayList<Roll> history;
    private RollAdapter adapter;

    private ListView historyView;
    private ListView diceList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_roller);

        currentRoll = (TextView) findViewById(R.id.diceRollValue);

        history = new ArrayList<>();

        adapter = new RollAdapter(this, history);

        historyView = (ListView) findViewById(R.id.history_view);

        historyView.setAdapter(adapter);

        diceList = (ListView) findViewById(R.id.dice_type_list_view);

        ArrayAdapter diceAdapter = new ArrayAdapter(this, R.layout.item_list_dice,
                getResources().getStringArray(R.array.dice_types_strings));

        diceList.setAdapter(diceAdapter);

        diceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value;
                String diceType;

                Dice dice = new Dice();

                switch (position) {
                    case 0:
                        dice.setDiceCount(2);
                        diceType = getResources().getString(R.string.coin_flip);
                        break;
                    case 1:
                        dice.setDiceCount(3);
                        diceType = "D3";
                        break;
                    case 2:
                        dice.setDiceCount(4);
                        diceType = "D4";
                        break;
                    case 3:
                        dice.setDiceCount(5);
                        diceType = "D5";
                        break;
                    case 4:
                        dice.setDiceCount(6);
                        diceType = "D6";
                        break;
                    case 5:
                        dice.setDiceCount(8);
                        diceType = "D8";
                        break;
                    case 6:
                        dice.setDiceCount(10);
                        diceType = "D10";
                        break;
                    case 7:
                        dice.setDiceCount(12);
                        diceType = "D12";
                        break;
                    case 8:
                        dice.setDiceCount(20);
                        diceType = "20";
                        break;
                    case 9:
                        dice.setDiceCount(100);
                        diceType = "D100";
                        break;
                    default:
                        dice.setDiceCount(6);
                        diceType = "D6";
                        break;
                }

                int diceRoll = dice.rollDice();

                if(position == 0) {
                    if(diceRoll == 1) value = "Tails";
                    else value = "Heads";
                }
                else value = Integer.toString(diceRoll);

                Roll roll = new Roll(diceType, value);

                adapter.add(roll);

                currentRoll.setText(value);
            }
        });
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
                intent = new Intent(DiceRollerActivity.this,LifeCounterMain.class);
                startActivity(intent);
                return true;
            case R.id.dice_roller:
                intent = new Intent(DiceRollerActivity.this, DiceRollerActivity.class);
                startActivity(intent);
                return true;
            case R.id.rulebook:
                intent = new Intent(DiceRollerActivity.this,RuleBook.class);
                startActivity(intent);
                return true;
            case R.id.deck_builder:
                intent = new Intent(DiceRollerActivity.this, DeckBuilderActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("history", history);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        history = (ArrayList)savedInstanceState.getSerializable("history");
    }
}
