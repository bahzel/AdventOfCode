package solution.y2021;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import utils.soution.MapSolution;

public class Day6_1 extends MapSolution<Map<Integer, Long>> {
	public static void main(String[] args) {
		new Day6_1().solve();
	}

	@Override
	protected Map<Integer, Long> initializeMapping() {
		return new HashMap<>();
	}

	@Override
	protected void transformInstruction(String instruction, Map<Integer, Long> integerLongMap) {
		var instructions = Arrays.stream(instruction.split(",")).map(Integer::parseInt).toList();
		instructions.forEach(fish -> integerLongMap.put(fish, integerLongMap.getOrDefault(fish, 0L) + 1));
	}

	@Override
	protected String computeSolution(Map<Integer, Long> currentFishes) {
		for (var i = 0; i < 80; i++) {
			var nextFishes = new HashMap<Integer, Long>();
			nextFishes.put(0, currentFishes.getOrDefault(1, 0L));
			nextFishes.put(1, currentFishes.getOrDefault(2, 0L));
			nextFishes.put(2, currentFishes.getOrDefault(3, 0L));
			nextFishes.put(3, currentFishes.getOrDefault(4, 0L));
			nextFishes.put(4, currentFishes.getOrDefault(5, 0L));
			nextFishes.put(5, currentFishes.getOrDefault(6, 0L));
			nextFishes.put(6, currentFishes.getOrDefault(7, 0L) + currentFishes.getOrDefault(0, 0L));
			nextFishes.put(7, currentFishes.getOrDefault(8, 0L));
			nextFishes.put(8, currentFishes.getOrDefault(0, 0L));
			currentFishes = nextFishes;
		}

		return currentFishes.values().stream().mapToLong(Long::longValue).sum() + "";
	}
}
