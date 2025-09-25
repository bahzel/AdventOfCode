package utils;

import java.util.LinkedList;
import java.util.ListIterator;

public class CyclingListIterator<T> {
	private final LinkedList<T> list;
	private ListIterator<T> iterator;

	public CyclingListIterator(LinkedList<T> list) {
		this.list = list;
		iterator = list.listIterator(list.size() / 2);
	}

	public T next() {
		if (!iterator.hasNext()) {
			iterator = list.listIterator();
		}
		return iterator.next();
	}

	public void remove() {
		if (list.size() > 1) {
			iterator.remove();
		}
	}

	public void add(T element) {
		iterator.add(element);
	}

	public T previous() {
		if (!iterator.hasPrevious()) {
			iterator = list.listIterator(list.size() - 1);
		}
		return iterator.previous();
	}
}
