package solution.y2018;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import utils.soution.MapSolution;

public class Day1_2 extends MapSolution<List<Long>> {
	public static void main(String[] args) {
		new Day1_2().solve();
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
		var cache = new HashSet<Long>();
		var currentValue = 0L;

		while (true) {
			for (var value : longs) {
				currentValue += value;

				if (!cache.add(currentValue)) {
					return currentValue + "";
				}
			}
		}
	}
}
