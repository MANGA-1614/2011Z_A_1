package A1Q3;
import java.util.*;

/**
 * Specializes the stack data structure for comparable elements, and provides
 * a method for determining the maximum element on the stack in O(1) time.
 * @author jameselder
 */
public class MaxStack<E extends Comparable<E>> extends Stack<E> {

    public Stack<E> 	maxStack;
    private Stack<E> 	maxStack2;

    public MaxStack() 
    {
        maxStack 	= new Stack<>();
        maxStack2 	= new Stack<>();       
    }

    /* must run in O(1) time */
    public E push(E element) 
    {
    	if (maxStack2.size() > 0)
    	{
    	   	if (maxStack2.lastElement().compareTo(element) <= 0)
	    	{
	    		maxStack2.push(element);
	    	}
	    	else
	    	{
	    		maxStack2.push(maxStack2.lastElement());
	    	}
    	}
    	else
    	{
    		maxStack2.push(element);
    	}
    	
        return maxStack.push(element);
    }

    /* @exception  EmptyStackException  if this stack is empty. */
    /* must run in O(1) time */
   public synchronized E pop() 
   {
	   if (maxStack.isEmpty())
	   {
		   throw new EmptyStackException();
	   }
	   maxStack2.pop();
       return maxStack.pop(); 
   }

    /* Returns the maximum value currenctly on the stack. */
    /* @exception  EmptyStackException  if this stack is empty. */
    /* must run in O(1) time */
    public synchronized E max() 
    {
    	if (maxStack.isEmpty())
    	{
    		throw new EmptyStackException();
    	}
    	
        return maxStack2.lastElement(); //Dummy return to satisfy compiler.  Remove once coded.
    }
}