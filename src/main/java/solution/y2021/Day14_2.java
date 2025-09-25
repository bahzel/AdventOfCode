package solution.y2021;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import utils.soution.Solution;

public class Day14_2 extends Solution {
	public static void main(String[] args) {
		new Day14_2().solve();
	}

	@Override
	protected String doSolve() {
		var pairs = getPairs();
		var rules = getRules();
		for (var i = 0; i < 40; i++) {
			var nextPairs = new HashMap<String, Long>();
			for (var pair : pairs.entrySet()) {
				var nextPair = rules.get(pair.getKey());
				nextPairs.put(nextPair.getLeft(), nextPairs.getOrDefault(nextPair.getLeft(), 0L) + pair.getValue());
				nextPairs.put(nextPair.getRight(), nextPairs.getOrDefault(nextPair.getRight(), 0L) + pair.getValue());
			}
			pairs = nextPairs;
		}

		var charCount = new HashMap<Character, Long>();
		for (var pair : pairs.entrySet()) {
			charCount.put(pair.getKey().charAt(1),
					charCount.getOrDefault(pair.getKey().charAt(1), 0L) + pair.getValue());
		}
		charCount.put(input.getFirst().charAt(0), charCount.getOrDefault(input.getFirst().charAt(0), 0L) + 1);

		var max = charCount.entrySet().stream().max(Map.Entry.comparingByValue()).orElseThrow().getValue();
		var min = charCount.entrySet().stream().min(Map.Entry.comparingByValue()).orElseThrow().getValue();
		return max - min + "";
	}

	private Map<String, Long> getPairs() {
		var pairs = new HashMap<String, Long>();
		for (var i = 0; i < input.getFirst().length() - 1; i++) {
			var pair = input.getFirst().substring(i, i + 2);
			pairs.put(pair, pairs.getOrDefault(pair, 0L) + 1);
		}
		return pairs;
	}

	private Map<String, Pair<String, String>> getRules() {
		var rules = new HashMap<String, Pair<String, String>>();
		for (var rule : input.subList(2, input.size())) {
			var instruction = rule.split(" -> ");
			rules.put(instruction[0],
					Pair.of(instruction[0].charAt(0) + instruction[1], instruction[1] + instruction[0].charAt(1)));
		}
		return rules;
	}
}
