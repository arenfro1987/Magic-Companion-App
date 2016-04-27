package edu.uco.rconyac1.magiccompanion;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class LifeCounterMain extends Activity {

    ImageButton regular;
    ImageButton commander;
    Button plus;
    Button minus;
    TextView count;

    int currentCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_counter_main);
        currentCount = 2;
        regular = (ImageButton) findViewById(R.id.regularCounterButton);
        commander = (ImageButton) findViewById(R.id.commanderCounterButton);
        plus = (Button) findViewById(R.id.countPlus);
        minus = (Button) findViewById(R.id.countMinus);
        count = (TextView) findViewById(R.id.playerCountView);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentCount < 8)
                {
                    currentCount++;
                }
                count.setText(Integer.toString(currentCount));
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentCount> 1)
                {
                    currentCount--;
                }

                count.setText(Integer.toString(currentCount));
            }
        });

        regular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LifeCounterMain.this,LifeCounter.class);
                intent.putExtra("type","regular");
                intent.putExtra("count",currentCount);
                startActivity(intent);
            }
        });

        commander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LifeCounterMain.this, LifeCounter.class);
                intent.putExtra("type", "commander");
                intent.putExtra("count", currentCount);
                startActivity(intent);
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
                intent = new Intent(LifeCounterMain.this,LifeCounterMain.class);
                startActivity(intent);
                return true;
            case R.id.dice_roller:
                intent = new Intent(LifeCounterMain.this, DiceRollerActivity.class);
                startActivity(intent);
                return true;
            case R.id.rulebook:
                intent = new Intent(LifeCounterMain.this,RuleBook.class);
                startActivity(intent);
                return true;
            case R.id.deck_builder:
                intent = new Intent(LifeCounterMain.this, DeckBuilderActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
