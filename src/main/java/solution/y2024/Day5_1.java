package solution.y2024;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import utils.soution.Solution;

public class Day5_1 extends Solution {
	public static void main(String[] args) {
		new Day5_1().solve();
	}

	@Override
	protected String doSolve() {
		var rules = getRules();
		var updates = getUpdates();

		return "" + updates	.stream()
							.filter(update -> isValid(update, rules))
							.mapToLong(update -> update.get(update.size() / 2))
							.sum();
	}

	private List<Pair<Integer, Integer>> getRules() {
		var indexSplit = input.indexOf("");
		return input.subList(0, indexSplit)
					.stream()
					.map(rule -> rule.split("\\|"))
					.map(rule -> Pair.of(Integer.parseInt(rule[0]), Integer.parseInt(rule[1])))
					.toList();
	}

	private List<List<Integer>> getUpdates() {
		var indexSplit = input.indexOf("");
		return input.subList(indexSplit + 1, input.size())
					.stream()
					.map(rule -> Arrays.stream(rule.split(",")).map(Integer::parseInt).toList())
					.toList();
	}

	private boolean isValid(List<Integer> update, List<Pair<Integer, Integer>> rules) {
		for (var rule : rules) {
			var indexOfFirstPage = update.indexOf(rule.getLeft());
			var indexOfSecondPage = update.indexOf(rule.getRight());

			if (indexOfFirstPage != -1 && indexOfSecondPage != -1 && indexOfFirstPage > indexOfSecondPage) {
				return false;
			}
		}
		return true;
	}
}
