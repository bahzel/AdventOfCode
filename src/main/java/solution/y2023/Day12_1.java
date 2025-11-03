package solution.y2023;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.tuple.Pair;

import utils.soution.InstructionSolution;

public class Day12_1 extends InstructionSolution<Pair<String, List<Integer>>, AtomicLong> {
	public static void main(String[] args) {
		new Day12_1().solve();
	}

	@Override
	protected AtomicLong initializeValue() {
		return new AtomicLong();
	}

	@Override
	protected Pair<String, List<Integer>> transformInstruction(String instruction) {
		var instructions = instruction.split(" ");
		var springs = Arrays.stream(instructions[1].split(",")).map(Integer::parseInt).toList();
		return Pair.of(instructions[0], springs);
	}

	@Override
	protected boolean performInstruction(Pair<String, List<Integer>> stringListPair, AtomicLong atomicLong) {
		CACHE.clear();
		atomicLong.addAndGet(countPossibilities(stringListPair));
		return false;
	}

	private final Map<Pair<String, List<Integer>>, Long> CACHE = new HashMap<>();

	private long countPossibilities(Pair<String, List<Integer>> pair) {
		if (CACHE.containsKey(pair)) {
			return CACHE.get(pair);
		}

		var map = pair.getLeft();
		var springs = pair.getRight();
		if (springs.isEmpty()) {
			return map.contains("#") ? 0L : 1L;
		}

		var possibilities = 0L;
		var spring = springs.getFirst();
		var nextSprings = springs.subList(1, springs.size());

		var minSizeLeft = springs.stream().mapToInt(Integer::intValue).sum() + springs.size() - 1;
		for (var i = 0; i + minSizeLeft <= map.length(); i++) {
			if (map.charAt(i) == '?') {
				if (doesSpringFit(map, spring, i, nextSprings.isEmpty())) {
					possibilities += countPossibilities(
							Pair.of(map.substring(i + spring + (nextSprings.isEmpty() ? 0 : 1)), nextSprings));
				}
			} else if (map.charAt(i) == '#') {
				if (doesSpringFit(map, spring, i, nextSprings.isEmpty())) {
					possibilities += countPossibilities(
							Pair.of(map.substring(i + spring + (nextSprings.isEmpty() ? 0 : 1)), nextSprings));
				}
				return possibilities;
			}
		}

		CACHE.put(pair, possibilities);
		return possibilities;
	}

	private boolean doesSpringFit(String map, int spring, int index, boolean last) {
		for (var i = 0; i < spring; i++) {
			if (map.charAt(index + i) == '.') {
				return false;
			}
		}

		if (map.length() == index + spring) {
			return last;
		}
		return map.charAt(index + spring) != '#';
	}

	@Override
	protected String getSolution(AtomicLong atomicLong) {
		return atomicLong.toString();
	}
}