package edu.uco.rconyac1.magiccompanion;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ryan on 11/6/2015.
 */
public class Deck implements Serializable{

    private ArrayList<Card> cardList;
    private int size;
    private String deckName;

    public Deck() {
        cardList = new ArrayList<>();
        setSize(0);
        setDeckName(null);
    }

    public Deck(String deckName) {
        cardList = new ArrayList<>();
        setSize(0);
        this.setDeckName(deckName);
    }

    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<>();
        for(int i = 0; i < cardList.size(); i++) {
            names.add(cardList.get(i).getName());
        }
        return names;
    }

    public void addCard(Card newCard) {
        getCardList().add(newCard);
    }

    public void removeCard(Card removingCard) {
        getCardList().remove(removingCard);
    }

    public ArrayList<Card> getCardList() {
        return cardList;
    }

    public boolean containsCard(String name) {
        if(getCardList().contains(name)) {
            return true;
        }
        else {
            return false;
        }
    }

    public int getSize() {
        return cardList.size();
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String toString() {
        String value = "";
        for(int i = 0; i < getCardList().size(); i++) {
            value += getCardList().get(i).toString() + "\n";
        }
        return value;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public void setCardList(ArrayList<Card> cardList) {
        this.cardList = cardList;
    }
}
