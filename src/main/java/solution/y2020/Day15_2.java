package solution.y2020;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.soution.MapSolution;

public class Day15_2 extends MapSolution<Map<Integer, Integer>> {
	public static void main(String[] args) {
		new Day15_2().solve();
	}

	@Override
	protected Map<Integer, Integer> initializeMapping() {
		return new HashMap<>();
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		return Arrays.asList(instructions.getFirst().split(","));
	}

	@Override
	protected void transformInstruction(String instruction, Map<Integer, Integer> integerIntegerMap) {
		integerIntegerMap.put(Integer.parseInt(instruction), integerIntegerMap.size());
	}

	@Override
	protected String computeSolution(Map<Integer, Integer> integerIntegerMap) {
		var lastNumber = integerIntegerMap	.entrySet()
											.stream()
											.max(Comparator.comparingInt(Map.Entry::getValue))
											.orElseThrow()
											.getKey();
		integerIntegerMap.remove(lastNumber);
		for (var i = integerIntegerMap.size(); i < 30000000 - 1; i++) {
			int nextNumber;
			if (integerIntegerMap.containsKey(lastNumber)) {
				nextNumber = i - integerIntegerMap.get(lastNumber);
			} else {
				nextNumber = 0;
			}
			integerIntegerMap.put(lastNumber, i);
			lastNumber = nextNumber;
		}
		return lastNumber + "";
	}
}
