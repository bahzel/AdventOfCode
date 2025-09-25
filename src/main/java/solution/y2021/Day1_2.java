package solution.y2021;

import java.util.ArrayList;
import java.util.List;

import utils.soution.MapSolution;

public class Day1_2 extends MapSolution<List<Integer>> {
	public static void main(String[] args) {
		new Day1_2().solve();
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
		var count = 0;
		for (int i = 3; i < integers.size(); i++) {
			if (integers.get(i) > integers.get(i - 3)) {
				count++;
			}
		}
		return String.valueOf(count);
	}
}
