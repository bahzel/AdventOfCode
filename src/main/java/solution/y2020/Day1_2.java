package solution.y2020;

import java.util.ArrayList;
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
		for (var i = 0; i < longs.size(); i++) {
			for (int j = i + 1; j < longs.size(); j++) {
				for (int k = j + 1; k < longs.size(); k++) {
					if (longs.get(i) + longs.get(j) + longs.get(k) == 2020) {
						return longs.get(i) * longs.get(j) * longs.get(k) + "";
					}
				}
			}
		}
		throw new IllegalStateException("No solution found");
	}
}
