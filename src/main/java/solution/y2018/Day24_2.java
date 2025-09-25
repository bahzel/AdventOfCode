package solution.y2018;

import utils.soution.Solution;

public class Day24_2 extends Solution {
	public static void main(String[] args) {
		new Day24_2().solve();
	}

	@Override
	protected String doSolve() {
		var lowerBound = 1;
		Integer higherBound = null;

		while (true) {
			int boostToCheck;
			if (higherBound == null) {
				boostToCheck = lowerBound * 2;
			} else {
				boostToCheck = (higherBound + lowerBound) / 2;
			}

			var solver = new Day24_1(boostToCheck);
			var solution = solver.solve();
			if (solver.getArmies().getFirst() instanceof ImmuneSystem) {
				if (lowerBound == boostToCheck - 1) {
					return solution;
				}
				higherBound = boostToCheck;
			} else {
				if (higherBound != null && higherBound == boostToCheck + 1) {
					return new Day24_1(higherBound).solve();
				}
				lowerBound = boostToCheck;
			}
		}
	}
}
