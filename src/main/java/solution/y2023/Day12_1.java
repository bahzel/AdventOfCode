package solution.y2023;

import java.util.Arrays;
import java.util.List;
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
		atomicLong.addAndGet(countPossibilities(stringListPair.getLeft(), stringListPair.getRight()));
		return false;
	}

	private long countPossibilities(String map, List<Integer> springs) {
		if (springs.isEmpty()) {
			return map.contains("#") ? 0L : 1L;
		}

		var possibilities = 0L;
		var spring = springs.getFirst();
		var nextSprings = springs.subList(1, springs.size());

		for (var i = 0; i + spring <= map.length(); i++) {
			if (map.charAt(i) == '?') {
				if (doesSpringFit(map, spring, i, nextSprings.isEmpty())) {
					possibilities += countPossibilities(map.substring(i + spring + (nextSprings.isEmpty() ? 0 : 1)),
							nextSprings);
				}
			} else if (map.charAt(i) == '#') {
				if (doesSpringFit(map, spring, i, nextSprings.isEmpty())) {
					possibilities += countPossibilities(map.substring(i + spring + (nextSprings.isEmpty() ? 0 : 1)),
							nextSprings);
				}
				return possibilities;
			}
		}
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