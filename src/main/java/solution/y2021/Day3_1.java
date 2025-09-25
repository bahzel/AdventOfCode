package solution.y2021;

import utils.soution.Solution;

public class Day3_1 extends Solution {
	public static void main(String[] args) {
		new Day3_1().solve();
	}

	@Override
	protected String doSolve() {
		StringBuilder gamma = new StringBuilder();
		StringBuilder epsilon = new StringBuilder();
		for (var i = 0; i < input.getFirst().length(); i++) {
			var index = i;
			if (input.stream().filter(value -> value.charAt(index) == '1').count() > input.size() / 2) {
				gamma.append('1');
				epsilon.append('0');
			} else {
				gamma.append('0');
				epsilon.append('1');
			}
		}
		return Long.parseLong(gamma.toString(), 2) * Long.parseLong(epsilon.toString(), 2) + "";
	}
}
