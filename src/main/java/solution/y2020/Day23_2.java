package solution.y2020;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import utils.soution.Solution;

public class Day23_2 extends Solution {
	public static void main(String[] args) {
		new Day23_2().solve();
	}

	@Override
	protected String doSolve() {
		var cups = new ImprovedList();
		var inputs = input.getFirst().split("");
		for (var inputNumber : inputs) {
			cups.add(Integer.parseInt(inputNumber));
		}
		for (int i = inputs.length + 1; i <= 1000000; i++) {
			cups.add(i);
		}

		for (int i = 0; i < 10000000; i++) {
			cups.moveNextThreeItems();
		}

		return (long) cups.get(1).getNext().getValue() * cups.get(1).getNext().getNext().getValue() + "";
	}
}

class ImprovedList {
	private ImprovedListItem lastItem = null;
	private ImprovedListItem firstItem = null;
	private ImprovedListItem currentItem = null;
	private final Map<Integer, ImprovedListItem> cache = new HashMap<>();
	private int size = 0;

	public void add(int value) {
		var newItem = new ImprovedListItem(value);
		if (firstItem == null) {
			firstItem = newItem;
		} else {
			lastItem.setNext(newItem);
		}
		lastItem = newItem;
		lastItem.setNext(firstItem);
		cache.put(value, newItem);
		size++;
	}

	public ImprovedListItem get(int value) {
		return cache.get(value);
	}

	public void moveNextThreeItems() {
		switchToNextItem();
		var picked1 = currentItem.getNext();
		var picked2 = picked1.getNext();
		var picked3 = picked2.getNext();
		currentItem.setNext(picked3.getNext());
		var destination = getDestination(currentItem.getValue(), picked1.getValue(), picked2.getValue(),
				picked3.getValue());
		var afterDestination = destination.getNext();
		destination.setNext(picked1);
		picked3.setNext(afterDestination);
	}

	private ImprovedListItem getDestination(int currentValue, int picked1, int picked2, int picked3) {
		var nextValue = currentValue - 1;
		while (true) {
			if (nextValue < 1) {
				nextValue = size;
			}

			if (nextValue != picked1 && nextValue != picked2 && nextValue != picked3) {
				return cache.get(nextValue);
			}
			nextValue--;
		}
	}

	private void switchToNextItem() {
		if (currentItem == null) {
			currentItem = firstItem;
		} else {
			currentItem = currentItem.getNext();
		}
	}
}

@Getter
class ImprovedListItem {
	@Setter
	private ImprovedListItem next = null;
	private final int value;

	public ImprovedListItem(int value) {
		this.value = value;
	}
}
