package chained_hash;

public interface HashI<K, V> {

	boolean add(K key, V value);
	boolean remove(K key);
	V getValue(K key);
	int size();
	
}
