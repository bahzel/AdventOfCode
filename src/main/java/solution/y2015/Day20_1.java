package solution.y2015;

import utils.soution.Solution;

public class Day20_1 extends Solution {
	public static void main(String[] args) {
		new Day20_1().solve();
	}

	@Override
	protected String doSolve() {
		long goal = Long.parseLong(input.getFirst()) / 10;
		for (long i = 4; i < Long.MAX_VALUE; i++) {
			if (computePresents(i) >= goal) {
				return i + "";
			}
		}

		throw new IllegalStateException();
	}

	private long computePresents(long house) {
		long presents = 1 + house;
		double firstFactor = Math.sqrt(house);

		for (long i = 2; i < firstFactor; i++) {
			if (house % i == 0) {
				presents += i;
				presents += house / i;
			}
		}

		if (house % firstFactor == 0) {
			presents += (long) firstFactor;
		}

		return presents;
	}
}
