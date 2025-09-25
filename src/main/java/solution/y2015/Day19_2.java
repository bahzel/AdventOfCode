package solution.y2015;

import org.apache.commons.lang3.tuple.Pair;
import utils.soution.Solution;

import java.util.ArrayList;
import java.util.List;

public class Day19_2 extends Solution {
	private List<Pair<String, String>> rules;

	public static void main(String[] args) {
		new Day19_2().solve();
	}

	@Override
	protected String doSolve() {
		rules = new ArrayList<>(input.subList(0, input.size() - 2)
									 .stream()
									 .map(rule -> rule.split(" => "))
									 .map(rule -> Pair.of(rule[0], rule[1]))
									 .toList());
		String goal = input.getLast();
		for (int i = 1; i < 10000; i++) {
			goal = reduce(goal);
			if ("e".equals(goal)) {
				return i + "";
			}
		}

		throw new IllegalStateException();
	}

	private String reduce(String input) {
		for (var rule : rules) {
			if (input.contains(rule.getRight())) {
				return input.replaceFirst(rule.getRight(), rule.getLeft());
			}
		}

		throw new IllegalArgumentException("No matching rule found");
	}
}
