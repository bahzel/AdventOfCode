package solution.y2020;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import utils.soution.MapSolution;

public class Day19_1 extends MapSolution<Map<String, String>> {
	public static void main(String[] args) {
		new Day19_1().solve();
	}

	@Override
	protected Map<String, String> initializeMapping() {
		return new HashMap<>();
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		return instructions.subList(0, instructions.indexOf(""));
	}

	@Override
	protected void transformInstruction(String instruction, Map<String, String> stringStringMap) {
		var instructions = instruction.split(": ");
		stringStringMap.put(instructions[0], instructions[1].replace("\"", ""));
	}

	@Override
	protected String computeSolution(Map<String, String> instructions) {
		var count = 0;
		for (var message : input.subList(input.indexOf("") + 1, input.size())) {
			if (doesMatch(instructions, "0", message)) {
				count++;
			}
		}
		return count + "";
	}

	private final Map<String, Boolean> CACHE = new HashMap<>();

	private boolean doesMatch(Map<String, String> instructions, String key, String value) {
		if (CACHE.containsKey(key + value)) {
			return CACHE.get(key + value);
		}

		for (var split : instructions.get(key).split(" \\| ")) {
			var keys = split.split(" ");
			if (keys.length == 1) {
				if (StringUtils.isNumeric(keys[0])) {
					if (doesMatch(instructions, keys[0], value)) {
						CACHE.put(key + value, true);
						return true;
					}
				} else {
					if (keys[0].equals(value)) {
						CACHE.put(key + value, true);
						return true;
					}
				}
				continue;
			}

			for (var i = 1; i < value.length(); i++) {
				if (doesMatch(instructions, keys[0], value.substring(0, i))
						&& doesMatch(instructions, keys[1], value.substring(i))) {
					CACHE.put(key + value, true);
					return true;
				}
			}
		}
		CACHE.put(key + value, false);
		return false;
	}
}
