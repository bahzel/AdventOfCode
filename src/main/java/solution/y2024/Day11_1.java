package solution.y2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utils.soution.MapSolution;

public class Day11_1 extends MapSolution<List<Long>> {
	public static void main(String[] args) {
		new Day11_1().solve();
	}

	@Override
	protected List<Long> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		return Arrays.asList(instructions.getFirst().split(" "));
	}

	@Override
	protected void transformInstruction(String instruction, List<Long> integers) {
		integers.add(Long.parseLong(instruction));
	}

	@Override
	protected String computeSolution(List<Long> stones) {
		for (int i = 0; i < 25; i++) {
			computeRound(stones);
		}

		return stones.size() + "";
	}

	private void computeRound(List<Long> stones) {
		for (int i = stones.size() - 1; i >= 0; i--) {
			if (stones.get(i) == 0) {
				stones.set(i, 1L);
			} else if ((stones.get(i) + "").length() % 2 == 0) {
				var oldValue = stones.get(i);
				var length = (oldValue + "").length();
				var factor = (long) Math.pow(10, (double) length / 2);
				stones.set(i, oldValue % factor);
				stones.add(i, oldValue / factor);
			} else {
				stones.set(i, stones.get(i) * 2024);
			}
		}
	}
}
