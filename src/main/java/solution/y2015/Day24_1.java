package solution.y2015;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import utils.soution.MapSolution;

public class Day24_1 extends MapSolution<List<Long>> {
	private long GROUP_SIZE;

	public static void main(String[] args) {
		new Day24_1().solve();
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
		GROUP_SIZE = longs.stream().mapToLong(Long::longValue).sum() / 3;
		var reversedList = longs.reversed();
		return getGroupWithSize6(reversedList)	.stream()
												.filter(group -> isPossibleGroup(group, longs))
												.map(this::computeQuantumEntanglement)
												.mapToLong(Long::longValue)
												.min()
												.orElseThrow()
				+ "";
	}

	private boolean isPossibleGroup(Set<Long> group, List<Long> longs) {
		var leftPresents = new ArrayList<>(longs);
		leftPresents.removeAll(group);
		var startGroup = new HashSet<Long>();
		startGroup.add(leftPresents.getFirst());
		leftPresents.removeFirst();
		return computeGroup(startGroup, leftPresents);
	}

	private List<Set<Long>> getGroupWithSize6(List<Long> longs) {
		var solution = new ArrayList<Set<Long>>();

		for (int a = 0; a < longs.size() - 5; a++) {
			for (int i = a + 1; i < longs.size() - 4; i++) {
				for (int j = i + 1; j < longs.size() - 3; j++) {
					for (int k = j + 1; k < longs.size() - 2; k++) {
						for (int l = k + 1; l < longs.size() - 1; l++) {
							for (int m = l + 1; m < longs.size(); m++) {
								if (longs.get(a) + longs.get(i) + longs.get(j) + longs.get(k) + longs.get(l)
										+ longs.get(m) == GROUP_SIZE) {
									solution.add(Set.of(longs.get(a), longs.get(i), longs.get(j), longs.get(k),
											longs.get(l), longs.get(m)));
								}
							}
						}
					}
				}
			}
		}

		return solution;
	}

	private long computeQuantumEntanglement(Set<Long> group) {
		return group.stream().mapToLong(Long::longValue).reduce(1, Math::multiplyExact);
	}

	private boolean computeGroup(Set<Long> startGroup, List<Long> availablePresents) {
		long missing = GROUP_SIZE - startGroup.stream().mapToLong(Long::longValue).sum();
		for (var availablePresent : availablePresents) {
			var newStartGroup = new HashSet<>(startGroup);
			newStartGroup.add(availablePresent);
			if (availablePresent == missing) {
				return true;
			} else if (availablePresent < missing) {
				var newAvailablePresents = new ArrayList<>(availablePresents);
				newAvailablePresents.remove(availablePresent);
				if (computeGroup(newStartGroup, newAvailablePresents)) {
					return true;
				}
			}
		}

		return false;
	}
}
