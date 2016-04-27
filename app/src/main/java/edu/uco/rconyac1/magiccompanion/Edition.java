package edu.uco.rconyac1.magiccompanion;

import java.io.Serializable;

/**
 * Created by Derek on 11/9/2015.
 */
public class Edition implements Serializable{
    private String set;
    private String setID;
    private String rarity;
    private String artist;
    private String flavor;
    private String storeURL;
    private String imageURL;
    private String lowPrice;
    private String highPrice;
    private String avgPrice;
    private String avgFoilPrice;

    public String getAvgFoilPrice() {
        return avgFoilPrice;
    }

    public void setAvgFoilPrice(String avgFoilPrice) {
        this.avgFoilPrice = avgFoilPrice;
    }

    public String getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(String lowPrice) {
        this.lowPrice = lowPrice;
    }

    public String getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(String highPrice) {
        this.highPrice = highPrice;
    }

    public String getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(String avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSetID() {
        return setID;
    }

    public void setSetID(String setID) {
        this.setID = setID;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getStoreURL() {
        return storeURL;
    }

    public void setStoreURL(String storeURL) {
        this.storeURL = storeURL;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }
}
