// SortTools.java 
/*
 * EE422C Project 1 submission by
 * Replace <...> with your actual data.
 * Zachary Pope
 * zhp76
 * 15465
 * Spring 2017
 * Slip days used: 0
 */

package assignment1;
public class SortTools {
	/**
	  * This method tests to see if the given array is sorted.
	  * @param nums is the array
	  * @param n is the size of the input to be checked
	  * @return true if array is sorted, false if it is not
	  */
	public static boolean isSorted(int[] nums, int n) {
		//You can't tell if an array of 0 numbers is sorted
		if(nums.length == 0 || n == 0){
			return false;
		}

		//If the element before the current element is the greater
		//of the two, it is not sorted. If every element is larger
		//than its predecessor then it is sorted
		for(int i = 1; i < n; i++){
			if(nums[i-1] > nums[i]){
				return false;
			}
		}
		return true;
	}

	/**
	 * This method looks for a number v if it exists and returns its index
	 * @param nums
	 * @param n
	 * @param v
	 * @return -1 if v is not in nums and the index of v if it is
	 */
	public static int find(int[] nums, int n, int v){
		//Check if the largest number in nums is smaller than v
		if(nums[n-1] < v){
			return -1;
		}

		//Do a binary search for v
		int left = 0;
		int right = n-1;
		int middle = 0;
		while(left <= right){
			middle = (left+right)/2;
			if(nums[middle] == v){
				return middle;
			} else if(nums[middle] < v){
				left = middle + 1;
			} else if(nums[middle] > v){
				right = middle - 1;
			}
		}
		return -1;
	}

	/**
	 * This method takes the array nums and the integer v and
	 * creates a new array such that v is sorted into nums
	 * @param nums
	 * @param n
	 * @param v
	 * @return retArray[] if v does not already exist in nums, nums if it does
	 */
	public static int[] insertGeneral(int[] nums, int n, int v){
		int retArray[];

		//Is v already in nums?
		if(find(nums, n, v) != -1){
			return nums;
		}

		//Is v the largest element?
		if(nums[n-1] <= v){
			retArray = java.util.Arrays.copyOfRange(nums, 0, n+1);
			retArray[n] = v;
			return retArray;
		}

		//Other two conditions failed, find where v goes
		retArray = new int[n+1];
		boolean vWasPlaced = false;
		for(int i = 0; i < n; i++){
			if(vWasPlaced) {
				retArray[i + 1] = nums[i];
			} else if(i == 0 && v < nums[0]){
				retArray[0] = v;
				retArray[1] = nums[0];
				vWasPlaced = true;
			} else if(v > nums[i] && v < nums[i+1]){
				retArray[i] = nums[i];
				retArray[i+1] = v;
				vWasPlaced = true;
			} else {
				retArray[i] = nums[i];
			}
		}
		return retArray;
	}

	/**
	 * This function takes the array nums and inserts v into it
	 * while maintaining that nums must be sorted
	 * @param nums
	 * @param n
	 * @param v
	 * @return n if v already exists in nums or is the largest element, or n+1 if it does not
	 */
	public static int insertInPlace(int[] nums, int n, int v){
		if(find(nums, n, v) != -1 || v > nums[n-1]){
			return n;
		}

		//Creates a clone array of nums that has v sorted into it
		int[] tempArray = java.util.Arrays.copyOf(insertGeneral(nums, n, v), n);
		for(int i = 0; i < n; i++){
			nums[i] = tempArray[i];
		}

		//This beautiful function was mercilessly killed by Amr:
		//System.arraycopy(insertGeneral(nums, n, v), 0, nums, 0, nums.length);
		return n+1;
	}

	/**
	 * Sorts the array nums using insertion sort
	 * @param nums
	 * @param n
	 * @return nothing
	 */
	public static void insertSort(int[] nums, int n){
		for(int i = 0; i < n-1; i++){
			if(nums[i] > nums[i+1]){
				int temp = nums[i+1];
				int j;
				for(j = i; j >= 0 && nums[j] > temp; j--){
					nums[j+1] = nums[j];
				}
				nums[j+1] = temp;
			}
		}
	}
}
