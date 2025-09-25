package solution.y2020;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import utils.soution.MapSolution;

public class Day5_2 extends MapSolution<List<Pair<Integer, Integer>>> {
	public static void main(String[] args) {
		new Day5_2().solve();
	}

	@Override
	protected List<Pair<Integer, Integer>> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<Pair<Integer, Integer>> pairs) {
		var minRow = 0;
		var maxRow = 127;
		for (int i = 0; i < 7; i++) {
			if (instruction.charAt(i) == 'F') {
				maxRow = (minRow + maxRow) / 2;
			} else if (instruction.charAt(i) == 'B') {
				minRow = (minRow + maxRow + 1) / 2;
			} else {
				throw new IllegalArgumentException("Invalid instruction: " + instruction);
			}
		}

		var minColumn = 0;
		var maxColumn = 7;
		for (int i = 7; i < 10; i++) {
			if (instruction.charAt(i) == 'L') {
				maxColumn = (minColumn + maxColumn) / 2;
			} else if (instruction.charAt(i) == 'R') {
				minColumn = (minColumn + maxColumn + 1) / 2;
			} else {
				throw new IllegalArgumentException("Invalid instruction: " + instruction);
			}
		}

		pairs.add(Pair.of(minRow, minColumn));
	}

	@Override
	protected String computeSolution(List<Pair<Integer, Integer>> pairs) {
		var ids = pairs	.stream()
						.filter(seat -> seat.getLeft() > 0 && seat.getLeft() < 127)
						.map(seat -> seat.getLeft() * 8 + seat.getRight())
						.sorted()
						.toList();
		for (int i = 1; i < ids.size(); i++) {
			if (ids.get(i - 1) + 1 != ids.get(i)) {
				return ids.get(i - 1) + 1 + "";
			}
		}
		throw new IllegalArgumentException("No solution found");
	}
}
