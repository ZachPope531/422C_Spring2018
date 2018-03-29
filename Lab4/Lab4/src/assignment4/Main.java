package assignment4;
/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Zachary Pope
 * zhp76
 * 15465
 * Slip days used: <0>
 * Spring 2018
 */

import java.util.Scanner;
import java.io.*;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) { 
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        
        // System.out.println("GLHF");

        String[] input;
        while(true){
            input = null;
            System.out.print("critters>");
            input = kb.nextLine().split(" ");
            if(input.length >= 4){
                System.out.print("error processing: ");
                for(int i = 0; i < input.length; i++){
                    if(i != input.length - 1){
                        System.out.print(input[i] + " ");
                    } else {
                        System.out.print(input[i]);
                    }
                }
                System.out.println();
                continue;
            }
            if(input[0].equals("quit") && input.length == 1){
                break;
            } else if(input[0].equals("quit") && input.length > 1){
                System.out.print("error processing: ");
                for(int i = 0; i < input.length; i++){
                    if(i != input.length - 1){
                        System.out.print(input[i] + " ");
                    } else {
                        System.out.print(input[i]);
                    }
                }
                System.out.println();
                continue;
            }

            /**
             * Take in any input on the keyboard, decide if that input is valid, and run
             * the specified command for it
             * @param input
             */
            switch(input[0]) {
                case "show":
                    try {
                        if (input.length > 1) {
                            throw new ArrayIndexOutOfBoundsException();
                        }
                        Critter.displayWorld();
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.print("error processing: ");
                        for (int i = 0; i < input.length; i++) {
                            if (i != input.length - 1) {
                                System.out.print(input[i] + " ");
                            } else {
                                System.out.print(input[i]);
                            }
                        }
                        System.out.println();
                        break;
                    }
                    break;
                case "step":
                    int stepNum = 0;
                    try {
                        stepNum = Integer.parseInt(input[1]);
                    } catch (NumberFormatException e) {
                        System.out.print("error processing: ");
                        for (int i = 0; i < input.length; i++) {
                            if (i != input.length - 1) {
                                System.out.print(input[i] + " ");
                            } else {
                                System.out.print(input[i]);
                            }
                        }
                        System.out.println();
                        break;
                    } catch(ArrayIndexOutOfBoundsException e){
                        stepNum = 1;
                    }
                    if (stepNum <= 0) {
                        System.out.println("Invalid input");
                        break;
                    }
                    for (; stepNum > 0; stepNum--) {
                        Critter.worldTimeStep();
                    }
                    break;
                case "seed":
                    long seedNum = 0;
                    try {
                        seedNum = Long.parseLong(input[1]);
                        if (input.length > 2) {
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        System.out.print("error processing: ");
                        for (int i = 0; i < input.length; i++) {
                            if (i != input.length - 1) {
                                System.out.print(input[i] + " ");
                            } else {
                                System.out.print(input[i]);
                            }
                        }
                        System.out.println();
                        break;
                    }
                    Critter.setSeed(seedNum);
                    break;
                case "make":
                    String className = null;
                    int makeNum = 0;
                    try {
                        className = input[1];
                        makeNum = Integer.parseInt(input[2]);
                        if (makeNum <= 0) {
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        System.out.print("error processing: ");
                        for (int i = 0; i < input.length; i++) {
                            if (i != input.length - 1) {
                                System.out.print(input[i] + " ");
                            } else {
                                System.out.print(input[i]);
                            }
                        }
                        System.out.println();
                        break;
                    }
                    for (; makeNum > 0; makeNum--) {
                        try {
                            Critter.makeCritter(className);
                        } catch (InvalidCritterException e) {
                            System.out.print("error processing: ");
                            for (int i = 0; i < input.length; i++) {
                                if (i != input.length - 1) {
                                    System.out.print(input[i] + " ");
                                } else {
                                    System.out.print(input[i]);
                                }
                            }
                            System.out.println();
                            break;
                        }
                    }
                    break;
                case "stats":
                    /**
                     * Try to invoke the runStats() method for the inputted class
                     * @param critter_class_name
                     * @throws InvalidCritterException
                     */
                    try {
                        Class<?> statsClass = Class.forName(myPackage + "." + input[1]);
                        Critter statsObj = (Critter) statsClass.newInstance();
                        java.lang.reflect.Method runStats = statsObj.getClass().getDeclaredMethod("runStats", java.util.List.class);
                        java.util.List<Critter> theseCritters = Critter.getInstances(input[1]);
                        runStats.invoke(statsObj, theseCritters);
                    }catch(ArrayIndexOutOfBoundsException e){
                        System.out.print("error processing: ");
                        for(int i = 0; i < input.length; i++){
                            if(i != input.length - 1){
                                System.out.print(input[i] + " ");
                            } else {
                                System.out.print(input[i]);
                            }
                        }
                        System.out.println();
                        break;
                    }catch(NoSuchMethodException e){
                        try{
                            Class<?> statsClass = Class.forName(myPackage + "." + input[1]);
                            Critter statsObj = (Critter) statsClass.newInstance();
                            Critter.runStats(Critter.getInstances(input[1]));
                        } catch(Exception a) {
                            for(int i = 0; i < input.length; i++){
                                if(i != input.length - 1){
                                    System.out.print(input[i] + " ");
                                } else {
                                    System.out.print(input[i]);
                                }
                            }
                            break;
                        }
                    }catch (InstantiationException | java.lang.reflect.InvocationTargetException | IllegalAccessException | ClassNotFoundException | InvalidCritterException e) {
                        System.out.print("error processing: ");
                        for(int i = 0; i < input.length; i++){
                            if(i != input.length - 1){
                                System.out.print(input[i] + " ");
                            } else {
                                System.out.print(input[i]);
                            }
                        }
                        System.out.println();
                        break;
                    }
                    break;
                default:
                    System.out.print("Invalid command: ");
                    for(int i = 0; i < input.length; i++){
                        if(i != input.length - 1){
                            System.out.print(input[i] + " ");
                        } else {
                            System.out.print(input[i]);
                        }
                    }
                    System.out.println();
                    break;
            }
        }
        
        /* Write your code above */
        System.out.flush();

    }
}
