package solution.y2018;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import utils.soution.MapSolution;

public class Day12_2 extends MapSolution<Pair<Set<Long>, List<boolean[]>>> {
	private final long ROUNDS = 50000000000L;

	public static void main(String[] args) {
		new Day12_2().solve();
	}

	@Override
	protected Pair<Set<Long>, List<boolean[]>> initializeMapping() {
		return Pair.of(new HashSet<>(), new ArrayList<>());
	}

	@Override
	protected void transformInstruction(String instruction, Pair<Set<Long>, List<boolean[]>> setListPair) {
		if (instruction.startsWith("initial")) {
			var initialState = instruction.split(" ")[2];
			for (int i = 0; i < initialState.length(); i++) {
				if ('#' == initialState.charAt(i)) {
					setListPair.getLeft().add((long) i);
				}
			}
		} else {
			var ruleInstruction = instruction.replace(" => ", "");
			var rule = new boolean[6];
			for (int i = 0; i < ruleInstruction.length(); i++) {
				if ('#' == ruleInstruction.charAt(i)) {
					rule[i] = true;
				}
			}
			setListPair.getRight().add(rule);
		}
	}

	@Override
	protected String computeSolution(Pair<Set<Long>, List<boolean[]>> setListPair) {
		var currentRound = setListPair.getLeft();
		var rules = setListPair.getRight();
		var lastScore = currentRound.stream().mapToLong(Long::longValue).sum();
		var lastDifference = 0L;

		for (long i = 1; i <= ROUNDS; i++) {
			currentRound = performRound(currentRound, rules);
			var nextScore = currentRound.stream().mapToLong(Long::longValue).sum();
			var nextDifference = nextScore - lastScore;

			if (lastDifference == nextDifference) {
				return (nextScore + nextDifference * (ROUNDS - i)) + "";
			}

			lastDifference = nextDifference;
			lastScore = nextScore;
		}

		throw new IllegalStateException();
	}

	private Set<Long> performRound(Set<Long> plants, List<boolean[]> rules) {
		var nextRound = new HashSet<Long>();
		var minIndex = plants.stream().mapToLong(Long::longValue).min().orElseThrow();
		var maxIndex = plants.stream().mapToLong(Long::longValue).max().orElseThrow();

		for (long i = minIndex - 2; i <= maxIndex + 2; i++) {
			if (computePlant(plants, i, rules)) {
				nextRound.add(i);
			}
		}

		return nextRound;
	}

	private boolean computePlant(Set<Long> plants, long index, List<boolean[]> rules) {
		for (var rule : rules) {
			if (plants.contains(index - 2) == rule[0] && plants.contains(index - 1) == rule[1]
					&& plants.contains(index) == rule[2] && plants.contains(index + 1) == rule[3]
					&& plants.contains(index + 2) == rule[4]) {
				return rule[5];
			}
		}
		return false;
	}
}
