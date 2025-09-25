package solution.y2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import utils.soution.MapSolution;

public class Day11_2 extends MapSolution<List<Long>> {
	private final Map<Pair<Long, Integer>, Long> CACHE = new HashMap<>();

	public static void main(String[] args) {
		new Day11_2().solve();
	}

	@Override
	protected List<Long> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		return Arrays.asList(instructions.getFirst().split(" "));
	}

	@Override
	protected void transformInstruction(String instruction, List<Long> integers) {
		integers.add(Long.parseLong(instruction));
	}

	@Override
	protected String computeSolution(List<Long> stones) {
		return stones.stream().mapToLong(stone -> computeChildren(stone, 75)).sum() + "";
	}

	private long computeChildren(long value, int rounds) {
		if (rounds == 0) {
			return 1L;
		} else if (value == 0) {
			return getFromCache(1L, rounds - 1);
		} else if ((value + "").length() % 2 == 0) {
			var length = (value + "").length();
			var factor = (long) Math.pow(10, (double) length / 2);
			return getFromCache(value % factor, rounds - 1) + getFromCache(value / factor, rounds - 1);
		} else {
			return getFromCache(value * 2024, rounds - 1);
		}
	}

	private long getFromCache(long value, int rounds) {
		var key = Pair.of(value, rounds);
		var cachedValue = CACHE.get(key);
		if (cachedValue != null) {
			return cachedValue;
		} else {
			var count = computeChildren(key.getLeft(), key.getRight());
			CACHE.put(key, count);
			return count;
		}
	}
}
