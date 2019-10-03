package chained_hash;

import java.util.Iterator;

import linked_list.LinkedList;

public class Hash<K, V> implements HashI<K, V>, Iterable<K> {

	class HashElement<KT, VT> implements Comparable<HashElement<KT, VT>> {

		K key;
		V value;

		public HashElement(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public int compareTo(HashElement<KT, VT> o) {
			return ((Comparable<K>) this.key).compareTo(o.key);
		}

	}

	private int numElements;
	private int tableSize;
	private double maxLoadFactor;
	LinkedList<HashElement<K, V>>[] hashArray;

	public Hash() {
		this(16);
	}

	public Hash(int tableSize) {
		this.tableSize = tableSize;
		hashArray = (LinkedList<HashElement<K, V>>[]) new LinkedList[tableSize];
		for (int i = 0; i < tableSize; i++) {
			hashArray[i] = new LinkedList<HashElement<K, V>>();
		}
		maxLoadFactor = 0.75;
		numElements = 0;
	}

	public int size() {
		return hashArray.length;
	}
	
	private int getHashValue(K key) {
		int hashValue = key.hashCode();
		hashValue = hashValue & 0x7FFFFFFF;
		hashValue = hashValue % tableSize;
		return hashValue;
	}

	public boolean add(K key, V value) {
		if (loadFactor() > maxLoadFactor) {
			resize(tableSize * 2);
		}
		HashElement<K, V> element = new HashElement<K, V>(key, value);
		int hashValue = getHashValue(key);
		hashArray[hashValue].addLast(element);
		numElements++;
		return true;
	}

	public boolean remove(K key) {
		HashElement<K, V> element = new HashElement<K, V>(key, null);
		int hashValue = getHashValue(key);
		hashArray[hashValue].remove(element);
		numElements--;
		return true;
	}

	public V getValue(K key) {
		int hashValue = getHashValue(key);
		for (HashElement<K, V> element : hashArray[hashValue]) {
			if (((Comparable<K>) key).compareTo(element.key) == 0) {
				return element.value;
			}
		}
		return null;
	}

	private void resize(int newSize) {
		LinkedList<HashElement<K, V>>[] newArray = (LinkedList<HashElement<K, V>>[]) new LinkedList[newSize];
		for (int i = 0; i < newSize; i++) {
			newArray[i] = new LinkedList<HashElement<K, V>>();
		}
		for (int i = 0; i < tableSize; i++) {
			for (HashElement<K, V> he : hashArray[i]) {
				K key = he.key;
				V val = getValue(key);
				HashElement<K, V> element = new HashElement<K, V>(key, val);
				int hashValue = ((key.hashCode()) & 0x7FFFFFFF) % newSize;
				newArray[hashValue].addLast(element);
			}
		}
		hashArray = newArray;
		tableSize = newSize;

	}

	private double loadFactor() {
		return numElements / tableSize;
	}

	@Override
	public Iterator<K> iterator() {
		return new IteratorHelper<K>();
	}

	class IteratorHelper<T> implements Iterator<T> {

		T[] keys;
		int position;

		public IteratorHelper() {
			keys = (T[]) new Object[numElements];
			int p = 0;
			for (int i = 0; i < tableSize; i++) {
				LinkedList<HashElement<K, V>> list = hashArray[i];
				for (HashElement<K, V> element : list) {
					keys[p++] = (T) element.key;
				}
				p = 0;
			}
			position = 0;
		}

		@Override
		public boolean hasNext() {
			return position < keys.length;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				return null;
			}
			return keys[position++];
		}

	}

}
