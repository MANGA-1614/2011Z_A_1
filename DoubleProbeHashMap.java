/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A3Q2;

/**
 *
 * @author elder
 */
public class DoubleProbeHashMap<K, V> extends ProbeHashMap<K, V> {

    int q; //Prime number of secondary hash
    
    // provide same constructors as base class
    /**
     * Creates a hash table with capacity 17,  prime factor 109345121 and 
     * secondary prime factor q = 13.
     */
    public DoubleProbeHashMap() {
        super();
        q = 13; //largest prime less than 17
    }

    /**
     * Creates a hash table with the given capacity cap, prime factor 109345121 
     * and secondary prime factor q equal to the smallest prime less than cap.
     * Cap must be at least 3 otherwise an Exception is thrown.
     */
    public DoubleProbeHashMap(int cap) throws Exception {
        super(cap);
        if (cap < 3) {
            throw new Exception();
        }
        q = selectSecondaryHashPrime(cap);
    }

    /**
     * Creates a hash table with the given capacity and prime factor, and
     * secondary prime factor q equal to the smallest prime less than capacity.
     * Cap must be at least 3 otherwise an Exception is thrown.
     */
    public DoubleProbeHashMap(int cap, int p) throws Exception {
        super(cap, p);
        if (cap < 3) {
            throw new Exception();
        }
        q = selectSecondaryHashPrime(cap);
    }
    
  /**
     * Updates the size of the hash table to be at least newCap and rehashes all 
     * entries.  Must also update secondary prime factor q.
     */
    protected void resize(int newCap) {
        //Implement this method.  Note that you can make use of the resize 
        //method of probleHashMap with a super.resize call.
    	
    	q = this.selectSecondaryHashPrime(newCap);
    	super.resize(newCap);
    }

    /**
     * Searches for an entry with key equal to k (which is known to have primary
     * hash value h1), returning the index at which it was found, or returning
     * -(a+1) where a is the index of the first empty or available slot that can
     * be used to store a new such entry.  Uses secondary hashing function 
     * h’(k) = q - k mod q, where the secondary prime factor q is the smallest 
     * prime less than the current capacity.
     *
     * @param h1 the precalculated hash value of the given key
     * @param k the key
     * @return index of found entry or if not found
     */
    protected int findSlot(int h1, K k) 
    {
        //modify the findSlot method of probeHashMap to use double hashing
    	int avail = -1;
    	int j1 = h1;
    	int h2 = this.secondaryHashValue(k);
		int j2 = h2;
    	
    	do
    	{
    		this.totalProbes++;
    		if (this.isAvailable(j1))
    		{
    			if (avail == -1)
    			{
    				avail = j1;
    			}
    			
    			if (table[j1] == null)
    			{
    				break;
    			}
    		}
    		else
    		{
    			if (table[j1].getKey().equals(k))
    			{
    				return j1;
    			}
    			else
    			{
    				//The following part is closer to the actual double hashing logic than the current one, but the current one has less probes.
    				
    				/*
    				int j2 = this.wrongSecondaryHashValue(k);
    				
    				if (this.isAvailable(j2))
    				{
    					if (avail == -1)
    					{
    						avail = j2;
    					}
    					
    					if (table[j2] == null)
    					{
    						break;
    					}
    				}
    				else
    				{
    					if (table[j2].getKey().equals(k))
    					{
    						return j2;
    					}
    				}
    				*/
    				
    			}
    		}
    		j1 = (j1 + j2) % capacity;
    	}
    	while (j1 != h1);
    	return -(avail + 1);
    }

    /**
     * Returns value of secondary hash function h’(k) = q - k mod q, 
     * where k is the hash code for key*
     */
    private int secondaryHashValue(K key) {
        //implement this method
    	//Proper secondaryHashValue
    	int keyHashCode = Math.abs(this.hashValue(key));
    	int part1 = q - keyHashCode;
    	return part1 % q;
    }
    
    private int wrongSecondaryHashValue(K key)
    {
    	int keyHashCode = Math.abs(this.hashValue(key));
    	int part1 = keyHashCode % q;
    	return q - part1;
    }

  //Selects secondary hash prime to be the largest prime less than cap
    int selectSecondaryHashPrime(int cap) {
        //
        for (int i = cap - 1; i > 0; i--) {
            if (isPrime(i)) {
                return i;
            }
        }
        return 1;
    }
}


