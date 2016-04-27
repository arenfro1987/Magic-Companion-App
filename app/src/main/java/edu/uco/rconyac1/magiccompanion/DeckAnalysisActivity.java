package edu.uco.rconyac1.magiccompanion;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class DeckAnalysisActivity extends Activity {

    Deck deck;
    int greenMana;
    int redMana;
    int blackMana;
    int blueMana;
    int whiteMana;
    int colorless;
    int land;
    int creature;
    int nonCreature;
    int[] manacounts;


    ArrayList<BarEntry> manaCurveEntries = new ArrayList<>();
    ArrayList<String> manaCurveLabels = new ArrayList<>();
    ArrayList<String> colorDistLabels = new ArrayList<>();
    ArrayList<Entry> colorDistEntries = new ArrayList<>();
    ArrayList<String> typeDistLabels = new ArrayList<>();
    ArrayList<Entry> typeDistEntries = new ArrayList<>();
    ArrayList<Integer> colorCounts = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_analysis);

        manaCurveLabels.add("1");
        manaCurveLabels.add("2");
        manaCurveLabels.add("3");
        manaCurveLabels.add("4");
        manaCurveLabels.add("5");
        manaCurveLabels.add("6");
        manaCurveLabels.add("7");

        typeDistLabels.add("Land");
        typeDistLabels.add("Creature");
        typeDistLabels.add("Non-Creature");

        greenMana = 0;
        redMana = 0;
        blackMana = 0;
        blueMana = 0;
        whiteMana = 0;
        colorless = 0;
        land = 0;
        creature = 0;
        nonCreature = 0;
        manacounts = new int[] {0, 0, 0, 0, 0, 0, 0};



        deck = (Deck) getIntent().getSerializableExtra("deck");

        ((TextView) findViewById(R.id.deckTitle)).setText(deck.getDeckName());

        for (Card card:deck.getCardList()
             ) {
                String costString = card.getCost();

            ArrayList<String> types = card.getTypes();
            boolean landOrCreature=false;
            for (String type: types) {
                if(type.equals("land")) land += card.getNumberInDeck();
                if(type.equals("creature")) creature += card.getNumberInDeck();
                landOrCreature = true;
            }

            String colorLabel = "";
            ArrayList<String> colors = card.getColors();
            if(!colors.isEmpty()) {
                for (String color : colors) {
                    if (colorLabel.length() == 0) colorLabel = color;
                    else colorLabel = colorLabel + "-" + color;
                }

                if (!colorDistLabels.contains(colorLabel)) {
                    colorDistLabels.add(colorLabel);
                    colorCounts.add(card.getNumberInDeck());
                } else {
                    colorCounts.set(colorDistLabels.indexOf(colorLabel),
                            colorCounts.get(colorDistLabels.indexOf(colorLabel)) +
                                    card.getNumberInDeck());
                }
            }



            if(!landOrCreature) nonCreature += card.getNumberInDeck();

            int loopcount = costString.length()/3;
            int manacount = 0;

            for(int i = 1; i <= loopcount; i++) {
                String temp = costString.substring(1, 2);
                if(temp.equals("W")) whiteMana += card.getNumberInDeck();
                else if(temp.equals("R")) redMana += card.getNumberInDeck();
                else if(temp.equals("G")) greenMana += card.getNumberInDeck();
                else if(temp.equals("B")) blackMana += card.getNumberInDeck();
                else if (temp.equals("U")) blueMana += card.getNumberInDeck();
                else try {
                        colorless += Integer.parseInt(temp) * card.getNumberInDeck();
                    } catch (Exception e) {
                        System.out.println(e.getStackTrace());
                    }
                manacount++;

                costString = costString.substring(3);
            }

            switch(manacount) {
                case 1:
                    manacounts[0] += card.getNumberInDeck();
                    break;
                case 2:
                    manacounts[1] += card.getNumberInDeck();
                    break;
                case 3:
                    manacounts[2] += card.getNumberInDeck();
                    break;
                case 4:
                    manacounts[3] += card.getNumberInDeck();
                    break;
                case 5:
                    manacounts[4] += card.getNumberInDeck();
                    break;
                case 6:
                    manacounts[5] += card.getNumberInDeck();
                    break;
                case 7:
                    manacounts[6] += card.getNumberInDeck();
                    break;

            }
        }


        manaCurveEntries.add(new BarEntry(manacounts[0], 0));
        manaCurveEntries.add(new BarEntry(manacounts[1], 1));
        manaCurveEntries.add(new BarEntry(manacounts[2], 2));
        manaCurveEntries.add(new BarEntry(manacounts[3], 3));
        manaCurveEntries.add(new BarEntry(manacounts[4], 4));
        manaCurveEntries.add(new BarEntry(manacounts[5], 5));
        manaCurveEntries.add(new BarEntry(manacounts[6], 6));

        BarDataSet manaCurveDataSet = new BarDataSet(manaCurveEntries, "Mana Curve");
        manaCurveDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarChart manaCurveChart = new BarChart(getApplicationContext());
        manaCurveChart.setDescription("");
        manaCurveChart.getXAxis().setTextSize(12f);
        BarData manaCurveData = new BarData(manaCurveLabels, manaCurveDataSet);

        manaCurveChart.setData(manaCurveData);


        FrameLayout frame = (FrameLayout) findViewById(R.id.manaCurveFrame);
        frame.addView(manaCurveChart);

        typeDistEntries.add(new Entry(land, 0));
        typeDistEntries.add(new Entry(creature, 1));
        typeDistEntries.add(new Entry(nonCreature, 2));

        PieDataSet typeDistDataSet = new PieDataSet(typeDistEntries, "Type Distribution");
        typeDistDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        typeDistDataSet.setSliceSpace(2f);
        PieChart typeDistChart = new PieChart(getApplicationContext());
        typeDistChart.setDescription("");
        typeDistChart.setDrawHoleEnabled(false);
        PieData typeDistData = new PieData(typeDistLabels, typeDistDataSet);
        typeDistData.setValueTextSize(11f);
        typeDistChart.setData(typeDistData);

        FrameLayout frameTypes = (FrameLayout) findViewById(R.id.typesPieFrame);
        frameTypes.addView(typeDistChart);

        for(int i = 0; i < colorDistLabels.size(); i++) {
            colorDistEntries.add(new Entry(colorCounts.get(i), i));
        }

        PieDataSet colorDistDataSet = new PieDataSet(colorDistEntries, "Color Distribution");
        colorDistDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        colorDistDataSet.setSliceSpace(4f);
        PieChart colorDistChart = new PieChart(getApplicationContext());
        colorDistChart.setDescription("");
        colorDistChart.setDrawHoleEnabled(false);
        colorDistChart.setTransparentCircleRadius(0f);
        colorDistChart.setBackgroundColor(Color.rgb(83, 83, 83));
        PieData colorDistData = new PieData(colorDistLabels, colorDistDataSet);
        colorDistData.setValueTextSize(11f);
        colorDistChart.setData(colorDistData);

        FrameLayout frameColors = (FrameLayout) findViewById(R.id.colorsPieFrame);
        frameColors.addView(colorDistChart);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_deck_analysis, menu);
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
}
