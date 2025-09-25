package solution.y2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.tuple.Pair;

import utils.soution.Solution;

public class Day16_2 extends Solution {
	public static void main(String[] args) {
		new Day16_2().solve();
	}

	@Override
	protected String doSolve() {
		var possibleCombinations = getPossibleCombinations();
		var combinations = new ArrayList<Pair<Pair<String, int[]>, Integer>>();
		while (combinations.size() < 20) {
			var combination = getNextCombination(possibleCombinations);
			for (var i = 0; i < possibleCombinations.size(); i++) {
				if (possibleCombinations.get(i).getLeft() == combination.getLeft()
						|| Objects.equals(possibleCombinations.get(i).getRight(), combination.getRight())) {
					possibleCombinations.remove(i);
					i--;
				}
			}
			combinations.add(combination);
		}

		var solution = 1L;
		var ownTicket = getOwnTicket();
		for (var combination : combinations) {
			if (combination.getLeft().getLeft().startsWith("departure")) {
				solution *= ownTicket.get(combination.getRight());
			}
		}
		return solution + "";
	}

	private Pair<Pair<String, int[]>, Integer> getNextCombination(
			List<Pair<Pair<String, int[]>, Integer>> possibleCombinations) {
		for (int i = 0; i < 20; i++) {
			Pair<Pair<String, int[]>, Integer> matchingRole = null;
			for (var combination : possibleCombinations) {
				if (combination.getRight() == i) {
					if (matchingRole == null) {
						matchingRole = combination;
					} else {
						matchingRole = null;
						break;
					}
				}
			}
			if (matchingRole != null) {
				return matchingRole;
			}
		}
		throw new IllegalStateException();
	}

	private List<Pair<Pair<String, int[]>, Integer>> getPossibleCombinations() {
		var rules = getRules();
		var tickets = getTickets();
		for (var i = 0; i < tickets.size(); i++) {
			for (var value : tickets.get(i)) {
				if (!isValid(value, rules)) {
					tickets.remove(i);
					i--;
					break;
				}
			}
		}

		var combinations = new ArrayList<Pair<Pair<String, int[]>, Integer>>();
		for (var rule : rules) {
			for (var i = 0; i < tickets.getFirst().size(); i++) {
				if (isRuleForIndex(rule, i, tickets)) {
					combinations.add(Pair.of(rule, i));
				}
			}
		}
		return combinations;
	}

	private boolean isRuleForIndex(Pair<String, int[]> rule, int index, List<List<Integer>> tickets) {
		for (var ticket : tickets) {
			if (ticket.get(index) < rule.getRight()[0]
					|| ticket.get(index) > rule.getRight()[1] && ticket.get(index) < rule.getRight()[2]
					|| ticket.get(index) > rule.getRight()[3]) {
				return false;
			}
		}
		return true;
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

	private List<Integer> getOwnTicket() {
		return Arrays.stream(input.get(input.indexOf("your ticket:") + 1).split(",")).map(Integer::parseInt).toList();
	}
}
