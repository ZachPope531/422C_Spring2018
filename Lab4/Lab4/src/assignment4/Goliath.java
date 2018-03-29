package assignment4;
/* CRITTERS Goliath.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Zachary Pope
 * zhp76
 * 15465
 * Slip days used: <0>
 * Spring 2018
 */

/**
 * A brutish critter who always wants to fight
 * Especially with that David fellow
 */
public class Goliath extends Critter {
    @Override
    public String toString(){ return "G"; }

    @Override
    public void doTimeStep() {
        //Move two steps in a random direction
        run(Critter.getRandomInt(8));
        reproduce(new Goliath(), Critter.getRandomInt(8));
    }

    @Override
    public boolean fight(String opponent) {
        return true;
    }
}
