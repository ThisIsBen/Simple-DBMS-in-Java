import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DuplicateMap<K, V> {

	int bucketNum=0;
    private Map<K, ArrayList<V>> m = new HashMap<>();
    
   public Map<K, ArrayList<V>> returnMap()
   {
	   return m;
   }
    
    
    

    public void put(K k, V v) {
        if (m.containsKey(k)) 
        {
            m.get(k).add(v);
        } 
        else 
        {
        	bucketNum++;
        	if(bucketNum<=10)//each hashtable can only have at most 10 bucket.
        	{
	            ArrayList<V> arr = new ArrayList<>();
	            arr.add(v);
	            m.put(k, arr);
	            
        	}
        	
        }
        //System.out.println(k);
    }

     public ArrayList<V> get(K i) {
        return m.get(i);
    }

    public V get(K k, int index) {
        return m.get(k).size()-1 < index ? null : m.get(k).get(index);
    }
}