package assignment4;
/* CRITTERS ScaredyCat.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Zachary Pope
 * zhp76
 * 15465
 * Slip days used: <0>
 * Spring 2018
 */

/**
 * The Scaredy Cat Critter, always running away and never fighting back
 */
public class ScaredyCat extends Critter {
    @Override
    public String toString(){ return "S"; }

    @Override
    public void doTimeStep() {
        run(getRandomInt(8));
    }

    @Override
    public boolean fight(String opponent) {
        run(getRandomInt(8));
        return false;
    }
}
