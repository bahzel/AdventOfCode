package solution.y2019;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import utils.StringTransformer;
import utils.soution.Solution;

public class Day16_1 extends Solution {
	private final List<Integer> BASE_PATTERN = List.of(0, 1, 0, -1);

	public static void main(String[] args) {
		new Day16_1().solve();
	}

	@Override
	protected String doSolve() {
		var currentRound = StringTransformer.splitString(input.getFirst()).stream().map(Integer::parseInt).toList();
		var pattern = createPattern();
		for (int i = 0; i < 100; i++) {
			currentRound = computeNextRound(currentRound, pattern);
		}
		return currentRound.stream().map(String::valueOf).collect(Collectors.joining()).substring(0, 8);
	}

	private List<List<Integer>> createPattern() {
		var pattern = new ArrayList<List<Integer>>();
		for (int i = 1; i <= input.getFirst().length(); i++) {
			var patternCurrentIndex = new ArrayList<Integer>();
			for (int j = 1; j <= input.getFirst().length(); j++) {
				patternCurrentIndex.add(BASE_PATTERN.get((j / i) % BASE_PATTERN.size()));
			}
			pattern.add(patternCurrentIndex);
		}
		return pattern;
	}

	private List<Integer> computeNextRound(List<Integer> currentRound, List<List<Integer>> pattern) {
		var nextRound = new ArrayList<Integer>();
		for (var currentPattern : pattern) {
			var currentValue = 0;
			for (int j = 0; j < currentRound.size(); j++) {
				currentValue += currentRound.get(j) * currentPattern.get(j);
			}
			nextRound.add(Math.abs(currentValue) % 10);
		}
		return nextRound;
	}
}
