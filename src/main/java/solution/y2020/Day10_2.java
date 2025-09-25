package solution.y2020;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.soution.MapSolution;

public class Day10_2 extends MapSolution<List<Integer>> {
	public static void main(String[] args) {
		new Day10_2().solve();
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
		integers.sort(Comparator.naturalOrder());
		integers.addFirst(0);
		integers.add(integers.getLast() + 3);

		return getCombinationsFrom(integers, 0) + "";
	}

	private static final Map<Integer, Long> CACHE = new HashMap<>();

	private long getCombinationsFrom(List<Integer> integers, int value) {
		var combinations = 0L;
		for (int i = value + 1; i < value + 4; i++) {
			if (integers.contains(i)) {
				var cachedChilds = CACHE.get(i);
				if (cachedChilds != null) {
					combinations += cachedChilds;
				} else {
					var childs = getCombinationsFrom(integers, i);
					CACHE.put(i, childs);
					combinations += childs;
				}
			}
		}

		return Math.max(combinations, 1);
	}
}
