package edu.uco.rconyac1.magiccompanion;

import java.io.Serializable;

/**
 * Created by vdpotvin on 10/27/15.
 */
public class Roll implements Serializable{
    private String diceType;
    private String roll;

    public Roll(String diceType, String roll) {
        this.diceType = diceType;
        this.roll = roll;
    }

    public String getDiceType() {
        return diceType;
    }

    public String getRoll() {
        return roll;
    }

    @Override
    public String toString() {
        return diceType + ": " + roll;
    }
}