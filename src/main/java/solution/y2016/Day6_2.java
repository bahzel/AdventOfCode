package solution.y2016;

import utils.Solution;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Day6_2 extends Solution {
	public static void main(String[] args) {
		new Day6_2().solve();
	}

	@Override
	protected String doSolve() {
		var solution = new StringBuilder();

		for (int i = 0; i < input.getFirst().length(); i++) {
			Map<Character, Integer> characterMap = new HashMap<>();

			for (var message : input) {
				var c = message.charAt(i);
				if (characterMap.containsKey(c)) {
					characterMap.put(c, characterMap.get(c) + 1);
				} else {
					characterMap.put(c, 1);
				}
			}

			solution.append(characterMap.entrySet()
										.stream()
										.min(Comparator.comparingInt(Map.Entry::getValue))
										.orElseThrow()
										.getKey());
		}

		return solution.toString();
	}
}
