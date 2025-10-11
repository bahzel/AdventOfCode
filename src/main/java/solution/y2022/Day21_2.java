package solution.y2022;

import utils.soution.Solution;

public class Day21_2 extends Solution {
	public static void main(String[] args) {
		new Day21_2().solve();
	}

	@Override
	protected String doSolve() {
		var indexHuman = 0;
		for (var i = 0; i < input.size(); i++) {
			if (input.get(i).startsWith("root")) {
				input.set(i, input.get(i).replace("+", "="));
			} else if (input.get(i).startsWith("humn")) {
				indexHuman = i;
			}
		}

		var min = 1L;
		var max = Long.MAX_VALUE / 100;

		while (true) {
			var toCheck = (min + max) / 2;
			input.set(indexHuman, "humn: " + toCheck);
			var solution = new Day21_1(input).disableLog().solve();
			switch (solution) {
			case "-1":
				max = toCheck - 1;
				break;
			case "1":
				min = toCheck + 1;
				break;
			case "0":
				new Day21_1(input).disableLog().solve();
				return toCheck + "";
			default:
				throw new IllegalStateException("Unexpected value: " + solution);
			}
		}
	}
}