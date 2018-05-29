package A1Q1;
import java.util.*;

/**
 * Represents a sparse numeric vector. Elements are comprised of a (long)
 * location index an a (double) value.  The vector is maintained in increasing
 * order of location index, which facilitates numeric operations like
 * inner products (projections).  Note that location indices can be any integer
 * from 1 to Long.MAX_VALUE.  The representation is based upon a
 * singly-linked list.
 * The following methods are supported:  iterator, getSize, getFirst,
 * add, remove, and dot, which takes the dot product of the with a second vector
 * passed as a parameter.
 * @author jameselder
 */
public class SparseNumericVector implements Iterable {

    protected SparseNumericNode head = null;
    protected SparseNumericNode tail = null;
    protected long size;

  /**
     * Iterator
     */
    @Override
    public Iterator<SparseNumericElement> iterator() { //iterator
        return new SparseNumericIterator(this);
    }

    /**
     * @return number of non-zero elements in vector
     */
   public long getSize() {
        return size;
    }

     /**
     * @return the first node in the list.
     */
    public SparseNumericNode getFirst() {
        return head;
    }
    
    /**
     * Add the element to the vector.  It is inserted to maintain the
     * vector in increasing order of index.  If the element has zero value, or if 
     * an element with the same index already exists, an UnsupportedOperationException is thrown. 
     * @param e element to add
     */
    public void add(SparseNumericElement e) throws UnsupportedOperationException
    {
      //index starts at 1; check SparseNumericElement file
	  double value = e.getValue();											//1
	  SparseNumericNode x = head;											//1

	  if (value == 0)
	  {
		  throw new UnsupportedOperationException();
	  }
	  
	  //check vector head
	  if (x == null)
	  {
		SparseNumericNode eNode = new SparseNumericNode(e, tail);
		tail = eNode;
		head = eNode;
		size = 0;
		size++;
	  }
	  else //Check for throws
	  {
		  assemble(this.getFirst(), e);
	  
	  }
    }
    
    private void assemble(SparseNumericNode node, SparseNumericElement e)
    {
    	SparseNumericNode currNode = node;
    	
    	if (currNode.getElement().getIndex() == e.getIndex())
    	{
    		throw new UnsupportedOperationException();
    	}
    	
    	if (currNode.getElement().getIndex() > e.getIndex())
    	{
    		SparseNumericNode eNode = new SparseNumericNode(e, currNode);
    		if (currNode.equals(head))
    		{
    			head = eNode;
    		}
    		size++;
    	}
    	else
    	{
	    	if (currNode.equals(this.head) && currNode.equals(this.tail))//makes sure there is at least 2 nodes before proceeding
	    	{
	    		if (currNode.getElement().getIndex() < e.getIndex())
	    		{
	    			SparseNumericNode eNode = new SparseNumericNode(e, null);
	    			tail = eNode;
	    			currNode.setNext(eNode);
	    			size++;
	    		}
	    		else
	    		{
	    			SparseNumericNode eNode= new SparseNumericNode(e, currNode);
	    			head = eNode;
	    			size++;
	    		}
	    	}
	    	else
	    	{
	    		if (currNode.getElement().getIndex() < e.getIndex())
	    		{
	    			if (currNode.equals(this.tail))
	    			{
	    				SparseNumericNode eNode = new SparseNumericNode(e, null);
	    				currNode.setNext(eNode);
	    				tail = eNode;
	    				size++;
	    			}
	    			else
	    			{
	    				if (currNode.getNext().getElement().getIndex() > e.getIndex())
	    				{
	    					SparseNumericNode eNode = new SparseNumericNode(e, currNode.getNext());
	    					currNode.setNext(eNode);
	    					size++;
	    				}
	    				else
	    				{
	    					assemble(currNode.getNext(), e);
	    				}
	    			}
	    		}
	    	}
    	}
    }
    
    /**
     * If an element with the specified index exists, it is removed and the
     * method returns true.  If not, it returns false.
     *
     * @param index of element to remove
     * @return true if removed, false if does not exist
     */
    public boolean remove(Long index) 
    {
        //implement this method
        //this return statement is here to satisfy the compiler - replace it with your code.
    	SparseNumericNode x = head;
    	
    	if (x == null)
    	{
    		return false;
    	}
    	else
    	{
    		SparseNumericNode y = x.getNext();
    		boolean getOut = true;
    		
    		if (x.getElement().getIndex() == index)
    		{
    			head = y;
    			size--;
    			return true;
    		}
    		
    		while(getOut)
    		{
    			if (y == null)
    			{
    				return false;
    			}
    			else
    			{
    				long yIdx = y.getElement().getIndex();
    				
    				if (yIdx == index)
    				{
    					x.setNext(y.getNext());
    					size--;
    					return true;
    				}
    				
    				if (yIdx > index)
    				{
    					return false;
    				}
    				
    				x = y;
    				y = y.getNext();
    			}
    		}
    	}
    	
    	return false;
    }

    /**
     * Returns the inner product of the vector with a second vector passed as a
     * parameter.  The vectors are assumed to reside in the same space.
     * Runs in O(m+n) time, where m and n are the number of non-zero elements in
     * each vector.
     * @param Y Second vector with which to take inner product
     * @return result of inner product
     */

    public double dot (SparseNumericVector Y) {
    	SparseNumericNode XTail = this.tail;
		SparseNumericNode YTail = Y.tail;
		
		SparseNumericNode XCurr = this.getFirst();
		SparseNumericNode YCurr = Y.getFirst();
		long XCurrIdx = XCurr.getElement().getIndex();
		long YCurrIdx = YCurr.getElement().getIndex();
		double XCurrVal = XCurr.getElement().getValue();
		double YCurrVal = YCurr.getElement().getValue();
		
		double sum = 0;
		boolean tailCheck = false;
		
		while (!tailCheck)
		{	
			if (XCurr.equals(null) || YCurr.equals(null))
			{
				tailCheck = true;
			}
			
			if (XCurrIdx == YCurrIdx)
			{
				sum = sum + XCurrVal * YCurrVal;
				
				if (XCurr.equals(XTail) || YCurr.equals(YTail))
				{
					tailCheck = true;
				}
				else
				{
					XCurr = XCurr.getNext();
					XCurrVal = XCurr.getElement().getValue();
					XCurrIdx = XCurr.getElement().getIndex();
					YCurr = YCurr.getNext();
					YCurrVal = YCurr.getElement().getValue();
					YCurrIdx = YCurr.getElement().getIndex();
				}
			}
			else
			{
				if (XCurrIdx > YCurrIdx)
				{
					YCurr = YCurr.getNext();
					YCurrVal = YCurr.getElement().getValue();
					YCurrIdx = YCurr.getElement().getIndex();
					
				}
				else
				{
					XCurr = XCurr.getNext();
					XCurrVal = XCurr.getElement().getValue();
					XCurrIdx = XCurr.getElement().getIndex();
				}
			}
		}
		
		return sum;
    }

       /**
     * returns string representation of sparse vector
     */

    @Override
    public String toString() {
        String sparseVectorString = "";
        Iterator<SparseNumericElement> it = iterator();
        SparseNumericElement x;
        while (it.hasNext()) {
            x = it.next();
            sparseVectorString += "(index " + x.getIndex() + ", value " + x.getValue() + ")\n";
        }
        return sparseVectorString;
    }
}
