package solution.y2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utils.soution.Solution;

public class Day23_1 extends Solution {
	public static void main(String[] args) {
		new Day23_1().solve();
	}

	@Override
	protected String doSolve() {
		var cups = new ArrayList<>(Arrays.stream(input.getFirst().split("")).map(Integer::parseInt).toList());

		var currentValue = cups.getFirst();
		for (int i = 0; i < 100; i++) {
			println("Move " + (i + 1) + " current value: " + currentValue);
			println(cups);
			var picked1 = cups.remove((cups.indexOf(currentValue) + 1) % cups.size());
			var picked2 = cups.remove((cups.indexOf(currentValue) + 1) % cups.size());
			var picked3 = cups.remove((cups.indexOf(currentValue) + 1) % cups.size());
			println("pick up: " + picked1 + ", " + picked2 + ", " + picked3);
			var destination = getDestination(cups, currentValue);
			println("destination: " + destination + "\n");
			var destinationIndex = cups.indexOf(destination);
			cups.add(destinationIndex + 1, picked1);
			cups.add(destinationIndex + 2, picked2);
			cups.add(destinationIndex + 3, picked3);
			currentValue = cups.get((cups.indexOf(currentValue) + 1) % cups.size());
		}

		StringBuilder solution = new StringBuilder();
		for (var i = cups.indexOf(1) + 1;; i++) {
			if (i == cups.size()) {
				i = 0;
			}
			if (i == cups.indexOf(1)) {
				break;
			}
			solution.append(cups.get(i));
		}
		return solution.toString();
	}

	private int getDestination(List<Integer> cups, int currentValue) {
		var nextValue = currentValue - 1;
		while (true) {
			if (nextValue < 1) {
				nextValue = cups.size() + 3;
			}

			if (cups.contains(nextValue)) {
				return nextValue;
			}
			nextValue--;
		}
	}
}
