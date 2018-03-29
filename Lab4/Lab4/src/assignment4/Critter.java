package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Zachary Pope
 * zhp76
 * 15465
 * Slip days used: <0>
 * Spring 2018
 */


import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	//True if the critter has moved during the current timestep
	private boolean hasMoved;

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;

	/**
	 * Moves a critter one space, taking walk_energy_cost from the critter's energy
	 * @param direction	The direction that the critter moves
	 */
	protected final void walk(int direction) {
		if(!hasMoved) {
			switch (direction) {
				case 0:
					x_coord++;
					break;
				case 1:
					x_coord++;
					y_coord--;
					break;
				case 2:
					y_coord--;
					break;
				case 3:
					x_coord--;
					y_coord--;
					break;
				case 4:
					x_coord--;
					break;
				case 5:
					x_coord--;
					y_coord++;
					break;
				case 6:
					y_coord++;
					break;
				case 7:
					x_coord++;
					y_coord++;
				default:
					break;
			}
			hasMoved = true;
		}
		energy -= Params.walk_energy_cost;
		if(x_coord < 0){
			x_coord = Params.world_width-1;
		} else if(x_coord >= Params.world_width){
			x_coord = 0;
		}
		if(y_coord < 0){
			y_coord = Params.world_height-1;
		} else if(y_coord >= Params.world_height){
			y_coord = 0;
		}
	}

	/**
	 * Moves a critter two spaces, taking run_energy_cost from the critter's energy
	 * @param direction	The direction that the critter moves
	 */
	protected final void run(int direction) {
		if(!hasMoved) {
			switch (direction) {
				case 0:
					x_coord += 2;
					break;
				case 1:
					x_coord += 2;
					y_coord -= 2;
					break;
				case 2:
					y_coord -= 2;
					break;
				case 3:
					x_coord -= 2;
					y_coord -= 2;
					break;
				case 4:
					x_coord -= 2;
					break;
				case 5:
					x_coord -= 2;
					y_coord += 2;
					break;
				case 6:
					y_coord += 2;
					break;
				case 7:
					x_coord += 2;
					y_coord += 2;
				default:
					break;
			}
			hasMoved = true;
		}
		energy -= Params.run_energy_cost;
		if(x_coord < 0){
			x_coord += Params.world_width;
		} else if(x_coord >= Params.world_width){
			x_coord -= Params.world_width;
		}
		if(y_coord < 0){
			y_coord += Params.world_height;
		} else if(y_coord >= Params.world_height){
			y_coord -= Params.world_height;
		}
	}

	/**
	 * Spawns a new baby critter next to its parent
	 * @param offspring	The Critter child object
	 * @param direction	The direction the offspring will be placed from the parent
	 */
	protected final void reproduce(Critter offspring, int direction) {
		if(energy >= Params.min_reproduce_energy) {
			babies.add(offspring);
			switch (direction) {
				case 0:
					offspring.x_coord = this.x_coord + 1;
					break;
				case 1:
					offspring.x_coord = this.x_coord + 1;
					offspring.y_coord = this.y_coord - 1;
					break;
				case 2:
					offspring.y_coord = this.y_coord - 1;
					break;
				case 3:
					offspring.x_coord = this.x_coord - 1;
					offspring.y_coord = this.y_coord - 1;
					break;
				case 4:
					offspring.x_coord = this.x_coord - 1;
					break;
				case 5:
					offspring.x_coord = this.x_coord - 1;
					offspring.y_coord = this.y_coord + 1;
					break;
				case 6:
					offspring.y_coord = this.y_coord + 1;
					break;
				case 7:
					offspring.x_coord = this.x_coord + 1;
					offspring.y_coord = this.y_coord + 1;
					break;
				default:
					break;
			}

			if(offspring.x_coord < 0){
				offspring.x_coord += Params.world_width;
			} else if(offspring.x_coord >= Params.world_width){
				offspring.x_coord -= Params.world_width;
			}
			if(offspring.y_coord < 0){
				offspring.y_coord += Params.world_height;
			} else if(offspring.y_coord >= Params.world_height){
				offspring.y_coord -= Params.world_height;
			}

			offspring.energy = (int) Math.floor(this.energy/2);
			energy = (int) Math.ceil(this.energy/2);
		}
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String opponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name	The name of the critter class to be created
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try{
			Class<?> newCritterClass = Class.forName(myPackage + "." + critter_class_name);
			Object newCritterObject = newCritterClass.newInstance();
			if(newCritterObject instanceof Critter){
				((Critter) newCritterObject).energy = Params.start_energy;
				((Critter) newCritterObject).x_coord = getRandomInt(Params.world_width);
				((Critter) newCritterObject).y_coord = getRandomInt(Params.world_height);
				population.add((Critter) newCritterObject);
			} else {
				throw new InvalidCritterException(critter_class_name);
			}
		}catch(ClassNotFoundException e){
			throw new InvalidCritterException(critter_class_name);
		}catch(IllegalAccessException e){
			throw new InvalidCritterException(critter_class_name);
		}catch(InstantiationException e){
			throw new InvalidCritterException(critter_class_name);
		}
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();

		try{
			Class<?> critterClass = Class.forName(myPackage + "." + critter_class_name);
			for(Critter crit : population){
				if(critterClass.isInstance(crit)){
					result.add(crit);
				}
			}
		}catch(ClassNotFoundException e){
			throw new InvalidCritterException(critter_class_name);
		}
	
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		// Complete this method.
		population.clear();
		babies.clear();
	}

	/**
	 * Do a world time step:
	 * Each critter does its specific time step
	 * Encounters are resolved
	 * Each critter has rest energy subtracted
	 * Dead critters are removed from the population
	 * Algae are generated
	 * Babies are generated
	 */
	public static void worldTimeStep() {
		//Do each critter's time step
		for(Critter crit : population){
			crit.hasMoved = false;
			crit.doTimeStep();
		}

		//Resolve encounters
		for(int i = 0; i < population.size()-1; i++){
			Critter A = population.get(i);

			//Don't choose a dead critter
			if(A.energy <= 0) continue;

			for(int j = i+1; j < population.size(); j++){
				Critter B = population.get(j);

				//Don't choose a dead critter
				if(B.energy <= 0) continue;

				if(A.x_coord == B.x_coord && A.y_coord == B.y_coord){
					int oldX = A.x_coord;
					int oldY = A.y_coord;

					boolean Afights = A.fight(B.toString());
					boolean Bfights = B.fight(A.toString());

					if(A.energy <= 0){
						break;
					}
					if(B.energy <= 0){
						continue;
					}

					//A tried to move into a spot occupied by a critter
					if(A.x_coord != oldX && A.y_coord != oldY){
						for(Critter crit : population){
							if(crit != A && A.x_coord == crit.x_coord && A.y_coord == crit.y_coord){
								A.x_coord = oldX;
								A.y_coord = oldY;
							}
						}
					}

					//B tried to move into a spot occupied by a critter
					if(B.x_coord != oldX && B.y_coord != oldY){
						for(Critter crit : population){
							if(crit != B && B.x_coord == crit.x_coord && B.y_coord == crit.y_coord){
								B.x_coord = oldX;
								B.y_coord = oldY;
							}
						}
					}

					//A critter may have fled, check the positions again
					if(A.x_coord == B.x_coord && A.y_coord == B.y_coord) {
						int Achance;
						int Bchance;
						if (!Afights) {
							Achance = 0;
						} else {
							Achance = getRandomInt(A.energy);
						}
						if (!Bfights) {
							Bchance = 0;
						} else {
							Bchance = getRandomInt(B.energy);
						}

						//Kill whoever has less energy
						if(Achance > Bchance){
							A.energy += Math.floor(B.energy/2);
							B.energy = -1; //B is dead
						} else if(Bchance > Achance){
							B.energy += Math.floor(A.energy/2);
							A.energy = -1; //A is dead
							break;
						} else { //Arbitrarily let A win
							A.energy += Math.floor(B.energy/2);
							B.energy = -1; //B is dead
						}
					}

				}
			}
		}

		//Update rest energy
		for(Critter crit : population){
			crit.energy -= Params.rest_energy_cost;
		}

		//If a critter has <= 0 energy, it has died
		for(int i = population.size()-1; i >= 0; i--){
			if(population.get(i).energy <= 0){
				population.remove(population.get(i));
			}
		}
		//Generate Algae
		for(int i = 0; i < Params.refresh_algae_count; i++){
			try {
				makeCritter("Algae");
			} catch (InvalidCritterException e){
				System.out.println("Damn");
			}
		}

		//Move babies to population
		population.addAll(babies);
		babies.clear();
	}

	/**
	 * Displays the world as a world_height x world_width grid
	 * Each spot with a critter will print that critter's toString()
	 */
	public static void displayWorld() {
		//Populate a matrix with each Critter's symbol where they stand
		String worldMatrix[][] = new String[Params.world_height][Params.world_width];

		//Print top barrier
		System.out.print("+");
		for(int i = 0; i < Params.world_width; i++){
			System.out.print("-");
		}
		System.out.println("+");

		for(Critter crit : population){
			worldMatrix[crit.y_coord][crit.x_coord] = crit.toString();
		}
		for(int i = 0; i < Params.world_height; i++){
			for(int j = 0; j < Params.world_width; j++){
				if(j == 0){
					System.out.print("|");
				}
				if(worldMatrix[i][j] == null){
					System.out.print(" ");
				} else {
					System.out.print(worldMatrix[i][j].charAt(0));
				}
				if(j == Params.world_width-1){
					System.out.print("|");
				}
			}
			System.out.println();
		}

		//Print bottom layer
		System.out.print("+");
		for(int i = 0; i < Params.world_width; i++){
			System.out.print("-");
		}
		System.out.println("+");
	}
}
