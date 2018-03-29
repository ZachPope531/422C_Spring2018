package assignment4;
/* CRITTERS Glutton.java
* EE422C Project 4 submission by
* Replace <...> with your actual data.
* Zachary Pope
* zhp76
* 15465
* Slip days used: <0>
* Spring 2018
*/

/**
 * A lazy critter, only eats Algae and reproduces
 * Walks 50% of the time
 */
public class Glutton extends Critter {
    @Override
    public String toString(){ return "O"; }

    @Override
    public void doTimeStep() {
        if(getRandomInt(10) >= 5) {
            walk(getRandomInt(8));
        }
        if(getEnergy() > Params.min_reproduce_energy){
            reproduce(new Glutton(), getRandomInt(8));
        }
    }

    @Override
    public boolean fight(String opponent) {
        return opponent.equals("@");
    }
}
