package solution.y2020;

import utils.soution.Solution;

public class Day3_2 extends Solution {
	public static void main(String[] args) {
		new Day3_2().solve();
	}

	@Override
	protected String doSolve() {
		return ride(1, 1) * ride(3, 1) * ride(5, 1) * ride(7, 1) * ride(1, 2) + "";
	}

	private long ride(int x, int y) {
		var collisions = 0;

		var column = x;
		for (int row = y; row < input.size(); row += y) {
			if (input.get(row).charAt(column) == '#') {
				collisions++;
			}
			column += x;
			column %= input.get(row).length();
		}

		return collisions;
	}
}
