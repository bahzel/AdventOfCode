package solution.y2024;

import utils.soution.Solution;

public class Day18_2 extends Solution {
	public static void main(String[] args) {
		new Day18_2().solve();
	}

	@Override
	protected String doSolve() {
		for (int i = 1025; i < input.size(); i++) {
			try {
				new Day18_1(i).disableLog().solve();
			} catch (IllegalStateException e) {
				return input.get(i - 1);
			}
		}

		throw new IllegalStateException();
	}
}
