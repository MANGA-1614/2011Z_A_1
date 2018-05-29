package A3Q1;

/**
 * Extends the TreeMap class to allow convenient access to entries
 * within a specified range of key values (findAllInRange).
 * @author jameselder
 */
public class BSTRange<K,V> extends TreeMap<K,V>{

    /* Returns the lowest (deepest) position in the subtree rooted at pos
     * that is a common ancestor to positions with
     * keys k1 and k2, or to the positions they would occupy were they present.
     */
    public Position<Entry<K, V>> findLowestCommonAncestor(K k1, K k2, Position<Entry<K, V>> pos) 
    {
    	if (pos.getElement() == null)
    	{
    		return null;
    	}
    	
    	int k1i = (int) k1;
    	int k2i = (int) k2;
    	
    	if ((k1i < 0) && (k2i < 0))
    	{
    		return null;
    	}
    	
    	K posKey = pos.getElement().getKey();
    	int k1Compare = this.compare(k1, posKey);
    	int k2Compare = this.compare(k2, posKey);		
    	int k1ECompare = this.compare(k1, this.treeMax(pos).getElement());
    	int k2ECompare = this.compare(k2, this.treeMax(pos).getElement());
    	/* If k#Compare < 0, k# < posKey
		 * If k#Compare > 0, k# > posKey
		*/
    	int k12Compare = this.compare(k1,  k2); 		
    	if (k12Compare > 0)										//checks if k1 < k2
    	{
    		return null;
    		
    	}
    	
    	if ((k1ECompare > 0) && (k2ECompare > 0)) 				//checks if k1 and k2 is bigger than the last key
    	{
    		return null;
    	}
    	if ((k1Compare != 0) && (k2Compare != 0) && (k1 != k2)) //checks if k1 != k2 != pos 
    	{
	    	if ((k1Compare < 0) && (k2Compare > 0)) 			// k1 < posKey < k2
	    	{
	    		return pos;
	    	}
	    	else
	    	{
	    		if ((k1Compare < 0) && (k2Compare < 0)) 		// k1 < k2 < posKey 
	    		{
	    			pos = this.parent(pos);
	    			return this.findLowestCommonAncestor(k1, k2, pos);
	    		}
	    		else
	    		{
	    			//posKey < k1 < k2
	    			pos = this.right(pos);
	    			return findLowestCommonAncestor(k1, k2, pos);
	    		}
	    	}
    	}
    	else
    	{
    		if ((k1 == k2) && (k1Compare != 0))				//allows me to iterate until k1 = k2 = pos
    		{												//allowed since we already checked if they're not equal
    			pos = this.right(pos);
    			return findLowestCommonAncestor(k1, k2, pos);
    		}
    		else
    		{
    			return pos;
    		}
    	}
    }
    
    /* Finds all entries in the subtree rooted at pos  with keys of k or greater
     * and copies them to L, in non-decreasing order.
     */
    protected void findAllAbove(K k, Position<Entry<K, V>> pos, PositionalList<Entry<K, V>> L) 
    {
		//implement this method
    	
    	if (pos != null)
    	{
	    	L.addLast(pos.getElement());
    	}
    }

    /* Finds all entries in the subtree rooted at pos with keys of k or less
     * and copies them to L, in non-decreasing order.
     */
    protected void findAllBelow(K k, Position<Entry<K, V>> pos, PositionalList<Entry<K, V>> L) 
    {
 		//implement this method
    	
    	if (pos != null)
    	{
	    	K posKey = pos.getElement().getKey();
	    	
	    	Iterable<Entry<K, V>> subMapBelow = this.subMap(posKey, k);
	    	for (Entry<K, V> entry : subMapBelow)
	    	{
	    		L.addLast(entry);
	    	}
	    	
	    	L.addLast(this.ceilingEntry(k));
    	}
    	
    }

    /* Returns all entries with keys no less than k1 and no greater than k2,
     * in non-decreasing order.
     */
    public PositionalList<Entry<K, V>> findAllInRange(K k1, K k2) 
    {
		//implement this method
    	PositionalList<Entry<K, V>> left = new LinkedPositionalList<Entry<K, V>>();
    	PositionalList<Entry<K, V>> right = new LinkedPositionalList<Entry<K, V>>();
    	PositionalList<Entry<K, V>> finalList = new LinkedPositionalList<Entry<K, V>>();
    	Position<Entry<K, V>> pos = this.findLowestCommonAncestor(k1, k2, this.root());
    	if (pos != null)
    	{
	    	this.findAllAbove(k1, pos, left);
	    	for (Entry<K, V> entry : left)
	    	{
	    		finalList.addLast(entry);
	    	}
	    	
	    	this.findAllBelow(k2, pos, right);
	    	for (Entry<K, V> entry : right)
	    	{
	    		if (entry != null)
	    		{
		    		int kCompare = this.compare(entry, finalList.last().getElement());
		    		if (kCompare > 0)
		    		{
		    			finalList.addLast(entry);
		    		}
	    		}
	    	}
    	}
    	
    	return finalList;
    }
}