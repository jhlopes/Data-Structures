package testing;

import chained_hash.Hash;
import linked_list.LinkedList;

public class Testing {
	public static void main(String[] args) {
		
		LinkedList<Integer> list = new LinkedList<Integer>();
		int n = 10;
		for (int i = 0; i < n; i++) {
			list.addFirst(i);
		}
		
		for (int i: list) {
			System.out.println(i);
		}
		
		for (int i = n - 1; i >= 0; i--) {
			int x = list.removeFirst();
			if (x != i) {
				System.out.println("X " + x + " - I " + i);
			}
		}
		
		for (int i = 0; i < n; i++) {
			list.addFirst(10);
		}
		
		Hash<String, String> hash = new Hash<String, String>(20);
		
		for (int i = 0; i < 100; i++) {
			hash.add("x"  + i + "x", i + "-" + i);
		}
		
		for (int i = 0; i < 100; i++) {
			System.out.println(hash.getValue("x"  + i + "x"));
		}
		
	}
}
