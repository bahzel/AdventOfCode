package solution.y2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import utils.soution.Solution;

public class Day5_2 extends Solution {
	public static void main(String[] args) {
		new Day5_2().solve();
	}

	@Override
	protected String doSolve() {
		var rules = getRules();
		var updates = getUpdates();

		return "" + updates	.stream()
							.filter(update -> isInvalid(update, rules))
							.peek(update -> fixUpdate(update, rules))
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
					.map(rule -> (List<Integer>) Arrays	.stream(rule.split(","))
														.map(Integer::parseInt)
														.collect(Collectors.toCollection(ArrayList::new)))
					.toList();
	}

	private boolean isInvalid(List<Integer> update, List<Pair<Integer, Integer>> rules) {
		for (var rule : rules) {
			var indexOfFirstPage = update.indexOf(rule.getLeft());
			var indexOfSecondPage = update.indexOf(rule.getRight());

			if (indexOfFirstPage != -1 && indexOfSecondPage != -1 && indexOfFirstPage > indexOfSecondPage) {
				return true;
			}
		}
		return false;
	}

	private void fixUpdate(List<Integer> update, List<Pair<Integer, Integer>> rules) {
		while (isInvalid(update, rules)) {
			reorderFirstValidation(update, rules);
		}
	}

	private void reorderFirstValidation(List<Integer> update, List<Pair<Integer, Integer>> rules) {
		for (var rule : rules) {
			var indexOfFirstPage = update.indexOf(rule.getLeft());
			var indexOfSecondPage = update.indexOf(rule.getRight());

			if (indexOfFirstPage != -1 && indexOfSecondPage != -1 && indexOfFirstPage > indexOfSecondPage) {
				update.remove(indexOfFirstPage);
				update.add(indexOfSecondPage, rule.getLeft());
			}
		}
	}
}
