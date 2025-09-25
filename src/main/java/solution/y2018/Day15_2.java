package solution.y2018;

import org.apache.commons.lang3.StringUtils;
import utils.soution.Solution;

public class Day15_2 extends Solution {
	public static void main(String[] args) {
		new Day15_2().solve();
	}

	@Override
	protected String doSolve() {
		var elfCount = input.stream().mapToInt(row -> StringUtils.countMatches(row, 'E')).sum();
		var minimumStrength = 3;
		Integer maximumStrength = null;

		while (true) {
			int testStrength;
			if (maximumStrength == null) {
				testStrength = minimumStrength * 2;
			} else {
				testStrength = (minimumStrength + maximumStrength) / 2;
			}

			var simulation = new Day15_1(testStrength);
			simulation.disableLog();
			var solution = simulation.solve();
			if (simulation.getElves().size() == elfCount) {
				maximumStrength = testStrength;
			} else {
				minimumStrength = testStrength;
			}

			if (maximumStrength != null && maximumStrength == minimumStrength + 1) {
				return solution;
			}
		}
	}
}
