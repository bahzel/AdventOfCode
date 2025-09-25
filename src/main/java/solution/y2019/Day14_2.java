package solution.y2019;

import utils.soution.Solution;

public class Day14_2 extends Solution {
	public static void main(String[] args) {
		new Day14_2().solve();
	}

	@Override
	protected String doSolve() {
		var minimumAmount = 1;
		var maximumAmount = Integer.MAX_VALUE / 2;

		while (true) {
			var checkAmount = (minimumAmount + maximumAmount) / 2;
			var oreAmount = Long.parseLong(new Day14_1(checkAmount).disableLog().solve());
			if (oreAmount <= 1000000000000L) {
				if (checkAmount == maximumAmount - 1) {
					return checkAmount + "";
				} else {
					minimumAmount = checkAmount;
				}
			} else {
				if (checkAmount == minimumAmount + 1) {
					return minimumAmount + "";
				} else {
					maximumAmount = checkAmount;
				}
			}
		}
	}
}
