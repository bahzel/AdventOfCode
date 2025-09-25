package solution.y2015;

import utils.Solution;

public class Day20_2 extends Solution {
	public static void main(String[] args) {
		new Day20_2().solve();
	}

	@Override
	protected String doSolve() {
		long goal = Long.parseLong(input.getFirst());
		for (long i = 100; i < Long.MAX_VALUE; i++) {
			if (computePresents(i) >= goal) {
				return i + "";
			}
		}

		throw new IllegalStateException();
	}

	private long computePresents(long house) {
		long presents = 0;
		double firstFactor = Math.sqrt(house);

		for (long i = 1; i < firstFactor; i++) {
			if (house % i == 0) {
				if (i * 50 >= house) {
					presents += i;
				}
				long secondFactor = house / i;
				if (secondFactor * 50 >= house) {
					presents += secondFactor;
				}
			}
		}

		if (house % firstFactor == 0) {
			presents += (long) firstFactor;
		}

		return presents * 11;
	}
}
