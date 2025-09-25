package solution.y2017;

import org.apache.commons.lang3.StringUtils;
import utils.StringTransformer;
import utils.soution.Solution;

public class Day14_1 extends Solution {
	public static void main(String[] args) {
		new Day14_1().solve();
	}

	@Override
	protected String doSolve() {
		var solution = 0;
		var key = input.getFirst();

		for (int i = 0; i < 128; i++) {
			var binaryString = StringTransformer.hexToBin(new Day10_2(key + "-" + i).disableLog().solve());
			solution += StringUtils.countMatches(binaryString, "1");
		}
		return solution + "";
	}
}
