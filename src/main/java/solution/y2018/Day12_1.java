package solution.y2018;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import utils.soution.MapSolution;

public class Day12_1 extends MapSolution<Pair<Set<Integer>, List<boolean[]>>> {
	private final int ROUNDS = 20;

	public static void main(String[] args) {
		new Day12_1().solve();
	}

	@Override
	protected Pair<Set<Integer>, List<boolean[]>> initializeMapping() {
		return Pair.of(new HashSet<>(), new ArrayList<>());
	}

	@Override
	protected void transformInstruction(String instruction, Pair<Set<Integer>, List<boolean[]>> setListPair) {
		if (instruction.startsWith("initial")) {
			var initialState = instruction.split(" ")[2];
			for (int i = 0; i < initialState.length(); i++) {
				if ('#' == initialState.charAt(i)) {
					setListPair.getLeft().add(i);
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
	protected String computeSolution(Pair<Set<Integer>, List<boolean[]>> setListPair) {
		var currentRound = setListPair.getLeft();
		var rules = setListPair.getRight();

		for (int i = 0; i < ROUNDS; i++) {
			currentRound = performRound(currentRound, rules);
		}

		return currentRound.stream().mapToInt(Integer::intValue).sum() + "";
	}

	private Set<Integer> performRound(Set<Integer> plants, List<boolean[]> rules) {
		var nextRound = new HashSet<Integer>();
		var minIndex = plants.stream().mapToInt(Integer::intValue).min().orElseThrow();
		var maxIndex = plants.stream().mapToInt(Integer::intValue).max().orElseThrow();

		for (int i = minIndex - 2; i <= maxIndex + 2; i++) {
			if (computePlant(plants, i, rules)) {
				nextRound.add(i);
			}
		}

		return nextRound;
	}

	private boolean computePlant(Set<Integer> plants, int index, List<boolean[]> rules) {
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
