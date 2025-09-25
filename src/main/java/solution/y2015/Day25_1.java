package solution.y2015;

import java.util.HashMap;

import utils.soution.Solution;

public class Day25_1 extends Solution {
	public static void main(String[] args) {
		new Day25_1().solve();
	}

	@Override
	protected String doSolve() {
		var coordinates = input.getFirst().replace("row ", ".").replace(", column ", ".").split("\\.");
		long row = Integer.parseInt(coordinates[2]);
		long column = Integer.parseInt(coordinates[3]);

		long indexInRow1 = (column * column + column) / 2;
		long index = indexInRow1 + ((column + row - 2) * (column + row - 2) + (column + row - 2)) / 2
				- ((column - 1) * (column - 1) + (column - 1)) / 2;
		return computeAtIndex(index) + "";
	}

	private long computeAtIndex(long index) {
		var CACHE = new HashMap<Long, Long>();

		long code = 20151125;

		for (long i = 2; i <= index; i++) {
			if (CACHE.containsKey(code)) {
				long distance = i - CACHE.get(code);
				i += ((index - i) / distance) * distance;
				CACHE.clear();
			} else {
				CACHE.put(code, i);
			}
			code = (code * 252533) % 33554393;
		}

		return code;
	}
}
