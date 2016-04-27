package edu.uco.rconyac1.magiccompanion;

import java.io.Serializable;

/**
 * Created by Derek on 10/26/2015.
 */
public class player implements Serializable{
    private int health;
    private int poison;
    private boolean mainPlayer;
    private int comDamage;
    private int playerNumber;
    public player(int health,int poison, boolean mainPlayer, int comDamage ,int playerNumber)
    {
        this.health = health;
        this.poison = poison;
        this.mainPlayer = mainPlayer;
        this.comDamage = comDamage;
        this.playerNumber = playerNumber;
    }

    public boolean amIDead()
    {
        boolean dead = false;
        if(health < 1)
        {
            dead = true;
        }
        else if (poison > 9)
        {
            dead= true;
        }
        return dead;
    }

    public boolean amIDeadFromComm(int damgage)
    {
        boolean dead = false;
        if(damgage > 20)
        {
            dead = true;
        }
        return dead;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getPoison() {
        return poison;
    }

    public void setPoison(int poison) {
        this.poison = poison;
    }

    public boolean isMainPlayer() {
        return mainPlayer;
    }

    public void setMainPlayer(boolean mainPlayer) {
        this.mainPlayer = mainPlayer;
    }

    public int getComDamage() {
        return comDamage;
    }

    public void setComDamage(int comDamage) {
        this.comDamage = comDamage;
    }
}
