package solution.y2020;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import utils.soution.MapSolution;

public class Day10_1 extends MapSolution<List<Integer>> {
	public static void main(String[] args) {
		new Day10_1().solve();
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

		var ones = 0;
		var threes = 0;

		for (int i = 1; i < integers.size(); i++) {
			var difference = integers.get(i) - integers.get(i - 1);
			if (difference == 1)
				ones++;
			if (difference == 3)
				threes++;
		}

		return ones * threes + "";
	}
}
