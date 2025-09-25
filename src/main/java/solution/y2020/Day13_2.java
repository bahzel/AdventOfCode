package solution.y2020;

import utils.soution.Solution;

public class Day13_2 extends Solution {
	public static void main(String[] args) {
		new Day13_2().solve();
	}

	@Override
	protected String doSolve() {
		var startingNumber = 100000000000000L;
		for (; startingNumber % 421 != 0; startingNumber++) {
		}

		for (; (startingNumber - 31) % 419 != 0; startingNumber += 421L) {
		}

		for (; (startingNumber - 41) % 41 != 0; startingNumber += 421L * 419) {
		}

		for (; (startingNumber - 37) % 37 != 0; startingNumber += 421L * 419 * 41) {
		}

		for (; (startingNumber - 2) % 29 != 0; startingNumber += 421L * 419 * 41 * 37) {
		}

		for (; (startingNumber - 8) % 23 != 0; startingNumber += 421L * 419 * 41 * 37 * 29) {
		}

		for (; (startingNumber - 12) % 19 != 0; startingNumber += 421L * 419 * 41 * 37 * 29 * 23) {
		}

		for (; (startingNumber + 17) % 17 != 0; startingNumber += 421L * 419 * 41 * 37 * 29 * 23 * 19) {
		}

		for (; (startingNumber - 44) % 13 != 0; startingNumber += 421L * 419 * 41 * 37 * 29 * 23 * 19 * 17) {
		}

		return startingNumber - 44 + "";
	}
}
