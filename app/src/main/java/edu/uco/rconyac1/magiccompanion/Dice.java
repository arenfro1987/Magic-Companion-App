package edu.uco.rconyac1.magiccompanion;

import java.util.Random;

/**
 * Created by vdpotvin on 10/27/15.
 */
public class Dice {

    private int diceCount;
    private Random random;

    public Dice() {

        this.diceCount = 2;
        random = new Random();
    }

    public void setDiceCount(int diceCount) {
        if(diceCount <= 1) this.diceCount = 2;
        else this.diceCount = diceCount;
    }

    public int rollDice() {
        return random.nextInt(diceCount) + 1;
    }
}
