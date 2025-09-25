package solution.y2020;

import utils.soution.Solution;

public class Day25_1 extends Solution {
	public static void main(String[] args) {
		new Day25_1().solve();
	}

	@Override
	protected String doSolve() {
		var publicKey1 = Long.parseLong(input.getFirst());
		var publicKey2 = Long.parseLong(input.getLast());
		var loopSize = getLoopSize(publicKey1);
		println("Loop size: " + loopSize);

		var value = 1L;
		for (var i = 0L; i < loopSize; i++) {
			value *= publicKey2;
			value %= 20201227;
		}

		return value + "";
	}

	private long getLoopSize(long key) {
		var value = 1L;
		for (var i = 1L;; i++) {
			value *= 7;
			value %= 20201227;
			if (value == key) {
				return i;
			}
		}
	}
}
