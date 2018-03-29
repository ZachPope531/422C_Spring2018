package assignment4;
/* CRITTERS Critter2.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Zachary Pope
 * zhp76
 * 15465
 * Slip days used: <0>
 * Spring 2018
 */

/**
 * A critter who's job is to reproduce and fight Goliaths
 */
public class Critter2 extends Critter {
    @Override
    public String toString(){ return "2"; }
    @Override
    public void doTimeStep() {
        walk(Critter.getRandomInt(4)*2); //Only walks in the Lord's directions, those of the cross
        if(getEnergy() > Params.min_reproduce_energy*2){
            //David always has twins for some reason
            reproduce(new Critter2(), Critter.getRandomInt(8));
            reproduce(new Critter2(), Critter.getRandomInt(8));
        }
    }

    @Override
    public boolean fight(String opponent) {
        //David only fights Goliaths for some reason, personal preference?
        return opponent.equals("1");
    }
}
