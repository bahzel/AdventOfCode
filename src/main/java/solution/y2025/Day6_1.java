package solution.y2025;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utils.StreamUtils;
import utils.StringTransformer;
import utils.soution.Solution;

public class Day6_1 extends Solution {
	public static void main(String[] args) {
		new Day6_1().solve();
	}

	@Override
	protected String doSolve() {
		var numbers = getNumbers();
		var signs = StringTransformer.removeDuplicateWhitespaces(input.getLast()).trim().split(" ");
		var solution = 0L;

		for (var i = 0; i < signs.length; i++) {
			if (signs[i].equals("+")) {
				solution += numbers.get(i).stream().mapToLong(Long::longValue).sum();
			} else if (signs[i].equals("*")) {
				solution += StreamUtils.product(numbers.get(i).stream().mapToLong(Long::longValue));
			}
		}

		return solution + "";
	}

	protected List<List<Long>> getNumbers() {
		List<List<Long>> numbers = new ArrayList<>(
				Arrays	.stream(StringTransformer.removeDuplicateWhitespaces(input.getFirst()).trim().split(" "))
						.map(number -> new ArrayList<>(List.of(Long.parseLong(number))))
						.toList());

		for (var line : input.subList(1, input.size() - 1)) {

			var nextNumbers = StringTransformer.removeDuplicateWhitespaces(line).trim().split(" ");
			for (var i = 0; i < nextNumbers.length; i++) {
				numbers.get(i).add(Long.parseLong(nextNumbers[i]));
			}
		}

		return numbers;
	}
}
