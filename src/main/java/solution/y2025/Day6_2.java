package solution.y2025;

import java.util.ArrayList;
import java.util.List;

public class Day6_2 extends Day6_1 {
	public static void main(String[] args) {
		new Day6_2().solve();
	}

	protected List<List<Long>> getNumbers() {
		var numberInput = input.subList(0, input.size() - 1);
		var numbers = new ArrayList<List<Long>>();
		var currentGroup = new ArrayList<Long>();
		numbers.add(currentGroup);

		for (var i = 0; i < input.getFirst().length(); i++) {
			var currentNumber = new StringBuilder();
			for (var line : numberInput) {
				var currentChar = line.charAt(i);
				if (currentChar != ' ') {
					currentNumber.append(currentChar);
				}
			}

			if (currentNumber.isEmpty()) {
				currentGroup = new ArrayList<>();
				numbers.add(currentGroup);
			} else {
				currentGroup.add(Long.parseLong(currentNumber.toString()));
			}
		}

		return numbers;
	}
}
