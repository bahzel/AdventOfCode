package solution.y2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import utils.soution.Solution;

public class Day16_1 extends Solution {
	public static void main(String[] args) {
		new Day16_1().solve();
	}

	@Override
	protected String doSolve() {
		var solution = 0;
		var rules = getRules();
		for (var ticket : getTickets()) {
			for (var value : ticket) {
				if (!isValid(value, rules)) {
					solution += value;
				}
			}
		}
		return solution + "";
	}

	private boolean isValid(int value, List<Pair<String, int[]>> rules) {
		for (var rule : rules) {
			if (value >= rule.getRight()[0] && value <= rule.getRight()[1]
					|| value >= rule.getRight()[2] && value <= rule.getRight()[3]) {
				return true;
			}
		}
		return false;
	}

	private List<Pair<String, int[]>> getRules() {
		var rules = new ArrayList<Pair<String, int[]>>();
		for (var i = 0; !input.get(i).isEmpty(); i++) {
			var inputs = input.get(i).split(": ");
			var values = inputs[1].replace("-", " ").replace("or ", "").split(" ");
			rules.add(Pair.of(inputs[0], new int[] { Integer.parseInt(values[0]), Integer.parseInt(values[1]),
					Integer.parseInt(values[2]), Integer.parseInt(values[3]) }));
		}
		return rules;
	}

	private List<List<Integer>> getTickets() {
		var tickets = new ArrayList<List<Integer>>();
		for (var ticket : input.subList(input.indexOf("nearby tickets:") + 1, input.size())) {
			tickets.add(Arrays.stream(ticket.split(",")).map(Integer::parseInt).toList());
		}
		return tickets;
	}
}
