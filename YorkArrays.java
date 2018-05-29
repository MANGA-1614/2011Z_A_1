package A4Q1;
import java.util.*;

/**
 *
 * Provides two static methods for sorting Integer arrays (heapSort and mergeSort)
 * @author jameselder
 */
public class YorkArrays {

    /* Sorts the input array of Integers a using HeapSort.  Result is returned in a.
     * Makes use of java.util.PriorityQueue.  
       Sorting is NOT in place - PriorityQueue allocates a separate heap-based priority queue. 
       Not a stable sort, in general.  
       Throws a null ointer exception if the input array is null.  */
    public static void heapSort(Integer[] a) throws NullPointerException 
    {
       //implement this method.
    	
    	if (a == null)
    	{
    		throw new NullPointerException();
    	}
    	
    	PriorityQueue<Integer> queueArray = new PriorityQueue<Integer>(a.length);
    	
    	for (int i = 0; i < a.length; i++) 
    	{
    		queueArray.add(a[i]);
    	}
    	
    	for (int i = 0; i < a.length; i++)
    	{
    		a[i] = queueArray.remove();
    	}
    }
    
    /* Sorts the input array of Integers a using mergeSort and returns result.
     * Sorting is stable.
       Throws a null pointer exception if the input array is null. */
    public static Integer[] mergeSort(Integer[] a)  throws NullPointerException 
    {
    	if (a == null)
    	{
    		throw new NullPointerException();
    	}
    	else
    	{
    		return mergeSort(a, 0, a.length - 1);
    	}
    }
    
    /* Sorts the input subarray of Integers a[p...q] using MergeSort and returns result.
     * Sorting is stable.
     */
    private static Integer[] mergeSort(Integer[] a, int p, int q) 
    {
        //implement this method.
    	
    	if (a.length > 1)
    	{
    		Integer[] a1 = Arrays.copyOfRange(a, 0, a.length / 2);
    		a1 = mergeSort(a1, 0, a1.length - 1);
    		Integer[] a2 = Arrays.copyOfRange(a, a.length / 2, a.length);
    		a2 = mergeSort(a2, 0, a2.length - 1);
    		
    		return merge(a1, a2);
    	}
    	else
    	{
    		return a;
    	}
    	
    }
    
    /* Merges two sorted arrays of Integers into a single sorted array.  Given two
     * equal elements, one in a and one in b, the element in a precedes the element in b.
     */
    private static Integer[] merge(Integer[] a1, Integer[] a2) 
    {
        //implement this method.
    	
    	Integer[] fA = new Integer[a1.length + a2.length];
		int fI = 0;
		int fa1 = 0;
		int fa2 = 0;
		
		while ( (fa1 < a1.length) || (fa2 < a2.length) )
		{
			if ( (fa1 < a1.length) && (fa2 < a2.length) )
			{
				if (a1[fa1] < a2[fa2])
				{
					fA[fI] = a1[fa1];
					fI++;
					fa1++;
					fA[fI] = a2[fa2];
				}
				else
				{	
					fA[fI] = a2[fa2];
					fI++;
					fa2++;
					fA[fI] = a1[fa1];
				}
			}
			else
			{
				if ( (fa1 >= a1.length) && (fa2 < a2.length))
				{
					fA[fI] = a2[fa2];
					fI++;
					fa2++;
				}
				else
				{
					fA[fI] = a1[fa1];
					fI++;
					fa1++;
				}
			}
		}
    	return fA;
    }

    
}