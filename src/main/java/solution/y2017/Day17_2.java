package solution.y2017;

import utils.soution.Solution;

public class Day17_2 extends Solution {
	public static void main(String[] args) {
		new Day17_2().solve();
	}

	@Override
	protected String doSolve() {
		var valueAt1 = 0;
		var currentIndex = 0;
		int step = Integer.parseInt(input.getFirst());

		for (int i = 1; i <= 50000000; i++) {
			currentIndex += step;
			currentIndex %= i;
			if (currentIndex == 0) {
				valueAt1 = i;
			}
			currentIndex++;
		}
		return valueAt1 + "";
	}
}
