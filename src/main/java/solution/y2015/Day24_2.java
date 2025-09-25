package solution.y2015;

import utils.MapSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Day24_2 extends MapSolution<List<Long>> {
	private long GROUP_SIZE;

	public static void main(String[] args) {
		new Day24_2().solve();
	}

	@Override
	protected List<Long> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<Long> longs) {
		longs.add(Long.parseLong(instruction));
	}

	@Override
	protected String computeSolution(List<Long> longs) {
		GROUP_SIZE = longs.stream().mapToLong(Long::longValue).sum() / 4;
		var reversedList = longs.reversed();
		return getGroupWithSize4(reversedList).stream()
											  //.filter(group -> isPossibleGroup(group, longs))
											  .map(this::computeQuantumEntanglement)
											  .mapToLong(Long::longValue)
											  .min()
											  .orElseThrow() + "";
	}

	private List<Set<Long>> getGroupWithSize4(List<Long> longs) {
		var solution = new ArrayList<Set<Long>>();

		for (int i = 0; i < longs.size() - 4; i++) {
			for (int j = i + 1; j < longs.size() - 3; j++) {
				for (int k = j + 1; k < longs.size() - 2; k++) {
					for (int l = k + 1; l < longs.size() - 1; l++) {
						if (longs.get(i) + longs.get(j) + longs.get(k) + longs.get(l) == GROUP_SIZE) {
							solution.add(Set.of(longs.get(i), longs.get(j), longs.get(k), longs.get(l)));
						}
					}

				}
			}
		}

		return solution;
	}

	private long computeQuantumEntanglement(Set<Long> group) {
		var solution = group.stream().mapToLong(Long::longValue).reduce(1, Math::multiplyExact);
		System.out.println(group + " - " + solution);
		return solution;
	}
}
