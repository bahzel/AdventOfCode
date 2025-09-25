package solution.y2021;

import java.util.Arrays;

import utils.soution.Solution;

public class Day7_2 extends Solution {
	public static void main(String[] args) {
		new Day7_2().solve();
	}

	@Override
	protected String doSolve() {
		var positions = Arrays.stream(input.getFirst().split(",")).map(Integer::parseInt).toList();
		var cheapestSolution = Long.MAX_VALUE;
		var minPosition = positions.stream().mapToInt(Integer::intValue).min().orElseThrow();
		var maxPosition = positions.stream().mapToInt(Integer::intValue).max().orElseThrow();
		for (var position = minPosition; position <= maxPosition; position++) {
			var testPosition = position;
			cheapestSolution = Math.min(cheapestSolution, positions.stream().mapToLong(fish -> {
				var difference = Math.abs(fish - testPosition);
				return ((long) difference * difference + difference) / 2;
			}).sum());
		}
		return cheapestSolution + "";
	}
}
