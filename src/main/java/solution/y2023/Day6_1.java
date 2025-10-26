package solution.y2023;

import java.util.ArrayList;
import java.util.List;

import utils.StringTransformer;
import utils.soution.Solution;

public class Day6_1 extends Solution {
	public static void main(String[] args) {
		new Day6_1().solve();
	}

	@Override
	protected String doSolve() {
		var times = getTimes();
		var distances = getDistances();
		var solution = 1L;

		for (var i = 0; i < times.size(); i++) {
			var time = times.get(i);
			var distance = distances.get(i);
			var min = 0L;
			var highPoint = (min + time) / 2;

			var upper = highPoint;
			while (min != upper) {
				var toCheck = (min + upper) / 2;
				if ((time - toCheck) * toCheck <= distance) {
					min = toCheck + 1;
				} else {
					upper = toCheck;
				}
			}

			long max = time;
			var lower = highPoint;
			while (lower != max) {
				var toCheck = (lower + max) / 2 + 1;
				if ((time - toCheck) * toCheck <= distance) {
					max = toCheck - 1;
				} else {
					lower = toCheck;
				}
			}

			solution *= max - min + 1;
		}

		return solution + "";
	}

	protected List<Long> getTimes() {
		var times = new ArrayList<Long>();
		var inputs = StringTransformer.removeDuplicateWhitespaces(input.getFirst()).split(" ");
		for (var i = 1; i < inputs.length; i++) {
			times.add(Long.parseLong(inputs[i]));
		}
		return times;
	}

	protected List<Long> getDistances() {
		var distances = new ArrayList<Long>();
		var inputs = StringTransformer.removeDuplicateWhitespaces(input.getLast()).split(" ");
		for (var i = 1; i < inputs.length; i++) {
			distances.add(Long.parseLong(inputs[i]));
		}
		return distances;
	}
}