package solution.y2015;

import utils.MapSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Day17_1 extends MapSolution<List<Integer>> {
	public static void main(String[] args) {
		new Day17_1().solve();
	}

	@Override
	protected List<Integer> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<Integer> integers) {
		integers.add(Integer.parseInt(instruction));
	}

	@Override
	protected String computeSolution(List<Integer> integers) {
		var validCombinations = new AtomicLong();
		addContainers(integers, new ArrayList<>(), validCombinations);
		return validCombinations.toString();
	}

	private void addContainers(List<Integer> containers, List<Integer> usedContainers, AtomicLong validCombinations) {
		var currentSum = usedContainers.stream().mapToInt(Integer::intValue).sum();
		if (currentSum > 150) {
			return;
		} else if (currentSum == 150) {
			validCombinations.incrementAndGet();
			return;
		}

		if (!containers.isEmpty()) {
			var leftContainers = containers.subList(1, containers.size());
			addContainers(leftContainers, usedContainers, validCombinations);
			var newUsedContainers = new ArrayList<>(usedContainers);
			newUsedContainers.add(containers.getFirst());
			addContainers(leftContainers, newUsedContainers, validCombinations);
		}
	}
}
