package solution.y2016;

import java.util.ArrayList;
import java.util.List;

import utils.Booleans;
import utils.soution.MapSolution;

public class Day18_1 extends MapSolution<List<boolean[]>> {
	private final int ROW_COUNT = 40;

	public static void main(String[] args) {
		new Day18_1().solve();
	}

	@Override
	protected List<boolean[]> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<boolean[]> booleans) {
		var row = new boolean[instruction.length() + 2];
		row[0] = true;
		for (int i = 0; i < instruction.length(); i++) {
			switch (instruction.charAt(i)) {
			case '.':
				row[i + 1] = true;
				break;
			case '^':
				row[i + 1] = false;
				break;
			default:
				throw new IllegalArgumentException("Invalid instruction: " + instruction);
			}
		}
		row[row.length - 1] = true;

		booleans.add(row);
	}

	@Override
	protected String computeSolution(List<boolean[]> booleans) {
		while (booleans.size() < ROW_COUNT) {
			booleans.add(computeNextRow(booleans.getLast()));
		}

		return booleans.stream().flatMap(Booleans::stream).filter(value -> value).count() - 2 * ROW_COUNT + "";
	}

	private boolean[] computeNextRow(boolean[] row) {
		var nextRow = new boolean[row.length];
		nextRow[0] = true;
		for (int i = 1; i < row.length - 1; i++) {
			nextRow[i] = row[i - 1] == row[i + 1];
		}
		nextRow[row.length - 1] = true;
		return nextRow;
	}
}
