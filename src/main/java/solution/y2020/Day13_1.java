package solution.y2020;

import java.util.ArrayList;
import java.util.List;

import utils.soution.MapSolution;

public class Day13_1 extends MapSolution<List<Long>> {
	public static void main(String[] args) {
		new Day13_1().solve();
	}

	@Override
	protected List<Long> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		return List.of(instructions.get(1).split(","));
	}

	@Override
	protected void transformInstruction(String instruction, List<Long> longs) {
		if (!"x".equals(instruction)) {
			longs.add(Long.parseLong(instruction));
		}
	}

	@Override
	protected String computeSolution(List<Long> longs) {
		var startTime = Long.parseLong(input.getFirst());
		for (var time = startTime;; time++) {
			for (var bus : longs) {
				if (time % bus == 0) {
					return bus * (time - startTime) + "";
				}
			}
		}
	}
}
