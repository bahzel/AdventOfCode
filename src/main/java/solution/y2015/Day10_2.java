package solution.y2015;

import utils.soution.Solution;

public class Day10_2 extends Solution {
	public static void main(String[] args) {
		new Day10_2().solve();
	}

	@Override
	protected String doSolve() {
		String value = input.getFirst();

		for (int i = 0; i < 50; i++) {
			value = new LookAndSay(value).solve();
		}

		return value.length() + "";
	}
}