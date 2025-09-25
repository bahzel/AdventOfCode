package solution.y2015;

import org.apache.commons.codec.digest.DigestUtils;
import utils.Solution;

public class Day4_1 extends Solution {
	public static void main(String[] args) {
		new Day4_1().solve();
	}

	@Override
	protected String doSolve() {
		String secretKey = input.getFirst();
		long number = 1;

		while (!doesHashHave5LeadingZeros(secretKey + number)) {
			number++;
		}

		return String.valueOf(number);
	}

	private boolean doesHashHave5LeadingZeros(String input) {
		var hash = DigestUtils.md5Hex(input);
		return hash.startsWith("00000");
	}
}
