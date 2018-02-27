package assignment1;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import java.util.*;

import org.junit.Test;


public class SortToolsTest {
	
	@Test(timeout = 2000)
	public void testFindFoundFull(){
		int[] x = new int[]{-2, -1, 0, 1, 2, 3};
		assertEquals(3, SortTools.find(x, 6, 1));
	}
	
	@Test(timeout = 2000)
	public void testInsertGeneralPartialEnd(){
		int[] x = new int[]{10, 20, 30, 40, 50};
		int[] expected = new int[]{10, 20, 30, 35};
		assertArrayEquals(expected, SortTools.insertGeneral(x, 3, 35));
	}

	@Test(timeout = 2000)
    public void testInsertGeneralLargestElement(){
	    int[] x = new int[]{0, 1, 2, 3, 4};
	    int[] expected = new int[]{0, 1, 2, 3, 4, 5};
	    assertArrayEquals(expected, SortTools.insertGeneral(x, x.length, 5));
    }

    @Test(timeout = 2000)
    public void testInsertGeneralSmallestElement(){
        int[] x = new int[]{0, 1, 2, 3, 4};
        int[] expected = new int[]{-1, 0, 1, 2, 3, 4};
        assertArrayEquals(expected, SortTools.insertGeneral(x, x.length, -1));
    }

    @Test(timeout = 2000)
    public void testInsertGeneralAlreadyThere(){
        int[] x = new int[]{0, 1, 2, 3, 4};
        int[] expected = new int[]{0, 1, 2, 3, 4};
        assertArrayEquals(expected, SortTools.insertGeneral(x, x.length, 3));
    }

    @Test(timeout = 2000)
    public void testInsertInPlaceGeneral(){
        int[] x = new int[]{0, 1, 2, 4, 5};
        int[] expected = new int[]{0, 1, 2, 3, 4};
        assertEquals(6, SortTools.insertInPlace(x, x.length, 3));
        assertArrayEquals(expected, x);
    }

    @Test(timeout = 2000)
    public void testInsertInPlaceAlreadyThere(){
        int[] x = new int[]{0, 1, 2, 3, 4};
        int[] expected = new int[]{0, 1, 2, 3, 4};
        assertEquals(5, SortTools.insertInPlace(x, x.length, 4));
        assertArrayEquals(expected, x);
    }

    @Test(timeout = 2000)
    public void testInsertInPlaceOutOfBounds(){
        int[] x = new int[]{0, 1, 2, 3, 4};
        int[] expected = new int[]{0, 1, 2, 3, 4};
        assertEquals(5, SortTools.insertInPlace(x, x.length, 5));
        assertArrayEquals(expected, x);
    }

    @Test(timeout = 2000)
    public void testInsertInPlaceSmallestElement(){
        int[] x = new int[]{0, 1, 2, 3, 4};
        int[] expected = new int[]{-1, 0, 1, 2, 3};
        assertEquals(6, SortTools.insertInPlace(x, x.length, -1));
        assertArrayEquals(expected, x);
    }

    @Test(timeout = 2000)
    public void testInsertInPlaceOneElement(){
        int[] x = new int[]{0};
        int[] expected = new int[]{-1};
        assertEquals(2, SortTools.insertInPlace(x, x.length, -1));
        assertArrayEquals(expected, x);
    }

    @Test
    public void testInsertionSort(){
	    Random r = new Random();
	    int[] x;
	    int[] expected;
	    for(int i = 0; i < 10000; i++){
	        x = new int[]{r.nextInt(1001), r.nextInt(1001), r.nextInt(1001), r.nextInt(1001), r.nextInt(1001),
                    r.nextInt(1001), r.nextInt(1001), r.nextInt(1001), r.nextInt(1001), r.nextInt(1001)};
	        expected = x.clone();
	        Arrays.sort(expected);
	        SortTools.insertSort(x, 10);
	        assertArrayEquals(expected, x);
        }
    }
}
