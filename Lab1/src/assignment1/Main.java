/* 
 * This file is how you might test out your code.  Don't submit this, and don't 
 * have a main method in SortTools.java.
 */

package assignment1;

import java.util.Arrays;
import java.util.Random;

public class Main {
	public static void main(String [] args) {
		Random r = new Random();

		int[] testArray = new int[]{r.nextInt(51), r.nextInt(51), r.nextInt(51), r.nextInt(51)};
		SortTools.insertSort(testArray, 4);
		System.out.println(Arrays.toString(testArray));

	}
}
