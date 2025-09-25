package solution.y2015;

import utils.soution.MapSolution;

import java.util.ArrayList;
import java.util.List;

public class Day17_2 extends MapSolution<List<Integer>> {
	public static void main(String[] args) {
		new Day17_2().solve();
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
		var validSolutions = new ArrayList<List<Integer>>();
		addContainers(integers, new ArrayList<>(), validSolutions);
		var minimumCount = validSolutions.stream().map(List::size).mapToInt(Integer::intValue).min().orElseThrow();
		return validSolutions.stream().filter(l -> l.size() == minimumCount).count() + "";
	}

	private void addContainers(List<Integer> containers, List<Integer> usedContainers,
			List<List<Integer>> validSolutions) {
		var currentSum = usedContainers.stream().mapToInt(Integer::intValue).sum();
		if (currentSum > 150) {
			return;
		} else if (currentSum == 150) {
			validSolutions.add(usedContainers);
			return;
		}

		if (!containers.isEmpty()) {
			var leftContainers = containers.subList(1, containers.size());
			addContainers(leftContainers, usedContainers, validSolutions);
			var newUsedContainers = new ArrayList<>(usedContainers);
			newUsedContainers.add(containers.getFirst());
			addContainers(leftContainers, newUsedContainers, validSolutions);
		}
	}
}
