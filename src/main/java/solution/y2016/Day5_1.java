package solution.y2016;

import org.apache.commons.codec.digest.DigestUtils;
import utils.Solution;

public class Day5_1 extends Solution {
	public static void main(String[] args) {
		new Day5_1().solve();
	}

	@Override
	protected String doSolve() {
		String key = input.getFirst();
		var solution = new StringBuilder();

		for (long index = 0; index < Long.MAX_VALUE; index++) {
			var hash = DigestUtils.md5Hex(key + index);
			if (hash.startsWith("00000")) {
				solution.append(hash.charAt(5));
				if (solution.length() == 8) {
					return solution.toString();
				}
			}
		}

		throw new IllegalStateException();
	}
}
