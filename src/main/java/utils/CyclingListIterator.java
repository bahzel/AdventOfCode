package utils;

import java.util.List;
import java.util.ListIterator;

public class CyclingListIterator<T> {
	private final List<T> list;
	private ListIterator<T> iterator;

	public CyclingListIterator(List<T> list) {
		this.list = list;
		iterator = list.listIterator();
	}

	public CyclingListIterator(List<T> list, int index) {
		this.list = list;
		iterator = list.listIterator(index);
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
			iterator = list.listIterator(list.size() - 2);
			return iterator.next();
		}
		return iterator.previous();
	}
}
