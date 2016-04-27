package edu.uco.rconyac1.magiccompanion;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ryan on 11/6/2015.
 */
public class Card implements Serializable{

    private String name;
    private String convertedCost;
    private int numberInDeck;
    private Edition myEdition;
    private ArrayList<String> types;
    private ArrayList<String> colors;
    private String cost;
    private String formats;
    private String text;
    private boolean commanderStatus;

    public Card(String name, String convertedCost, Edition myEdition, ArrayList<String> types, ArrayList<String> colors, String cost, String formats,String text) {
        this.setName(name);
        this.setConvertedCost(convertedCost);
        this.setMyEdition(myEdition);
        this.setTypes(types);
        this.setColors(colors);
        this.setCost(cost);
        this.setFormats(formats);
        this.setText(text);
        this.setNumberInDeck(0);
        commanderStatus = false;
        setUpCost();
    }

    public Card() {
        this.setName("");
        this.setConvertedCost("");
        this.setMyEdition(new Edition());
        this.setTypes(new ArrayList<String>());
        this.setColors(new ArrayList<String>());
        this.setCost("");
        this.setFormats("");
        this.setText("");
        this.setNumberInDeck(0);
        commanderStatus = false;
        setUpCost();
    }

    public boolean isCommanderStatus() {
        return commanderStatus;
    }

    public void setCommanderStatus(boolean commanderStatus) {
        this.commanderStatus = commanderStatus;
    }

    public void setUpCost()
    {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConvertedCost() {
        return convertedCost;
    }

    public void setConvertedCost(String convertedCost) {
        this.convertedCost = convertedCost;
    }

    public Edition getMyEdition() {
        return myEdition;
    }

    public void setMyEdition(Edition myEdition) {
        this.myEdition = myEdition;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<String> types) {
        this.types = types;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getFormats() {
        return formats;
    }

    public void setFormats(String formats) {
        this.formats = formats;
    }

    public int getNumberInDeck() {
        return numberInDeck;
    }

    public void setNumberInDeck(int numberInDeck) {
        this.numberInDeck = numberInDeck;
    }
}
