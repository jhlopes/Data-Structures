package linked_list;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class LinkedList<E> implements ListI<E>, Iterable<E> {

	class Node<T> {
		T data;
		Node<T> next;

		public Node(T obj) {
			data = obj;
			next = null;
		}
	}

	private Node<E> head;
	private Node<E> tail;
	private int currentSize;

	public LinkedList() {
		head = null;
		tail = null;
		currentSize = 0;
	}

	public void addFirst(E obj) {
		Node<E> node = new Node<E>(obj);
		if (head == null) {
			head = tail = node;
			currentSize++;
			return;
		}
		node.next = head;
		head = node;
		currentSize++;
	}

	public void addLast(E obj) {
		Node<E> node = new Node<E>(obj);
		if (head == null) {
			head = tail = node;
			currentSize++;
			return;
		}
		tail.next = node;
		tail = node;
		currentSize++;
	}

	public E removeFirst() {
		if (head == null) {
			return null;
		}
		E temp = head.data;
		if (head == tail) {
			head = tail = null;
		} else {
			head = head.next;
		}
		currentSize--;
		return temp;
	}

	public E removeLast() {
		if (head == null) {
			return null;
		}
		if (head == tail) {
			return removeFirst();
		}
		Node<E> current = head;
		Node<E> previous = null;
		while (current != tail) {
			previous = current;
			current = current.next;
		}
		previous.next = null;
		tail = previous;
		currentSize--;
		return current.data;
	}

	public E remove(E obj) {
		Node<E> current = head;
		Node<E> previous = null;
		while (current != null) {
			if (((Comparable<E>) current.data).compareTo(obj) == 0) {
				if (current == head) {
					return removeFirst();
				}
				if (current == tail) {
					return removeLast();
				}
				currentSize--;
				previous.next = current.next;
				return current.data;
			}
			previous = current;
			current = current.next;
		}
		return null;
	}

	public boolean contains(E obj) {
		Node<E> current = head;
		while (current != null) {
			if (((Comparable<E>) current.data).compareTo(obj) == 0) {
				return true;
			}
			current = current.next;
		}
		return false;
	}
	
	public E peekFirst() {
		if (head == null) {
			return null;
		}
		return head.data;
	}
	
	public E peekLast() {
		if (tail == null) {
			return null;
		}
		return tail.data;
	}

	class IteratorHelper implements Iterator<E> {
		Node<E> index;
		
		public IteratorHelper() {
			index = head;
		}
		
		@Override
		public boolean hasNext() {
			return index != null;
		}

		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			E val = index.data;
			index = index.next;
			return val;
		}
		
	}
	
	@Override
	public Iterator<E> iterator() {
		return new IteratorHelper();
	}
	

}
