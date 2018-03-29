package assignment4;
/* CRITTERS Critter1.java
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
public class Critter1 extends Critter {
    @Override
    public String toString(){ return "1"; }

    @Override
    public void doTimeStep() {
        //Move two steps in a random direction
        run(Critter.getRandomInt(8));
        reproduce(new Critter1(), Critter.getRandomInt(8));
    }

    @Override
    public boolean fight(String opponent) {
        return true;
    }
}
