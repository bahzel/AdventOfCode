package solution.y2021;

import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day9_1 extends GridSolution<Integer> {
	public static void main(String[] args) {
		new Day9_1().solve();
	}

	@Override
	protected GridElement<Integer> transformCell(char ch, int x, int y) {
		return new GridElement<>(Integer.parseInt(String.valueOf(ch)), x, y);
	}

	@Override
	protected String computeSolution() {
		return stream()	.filter(tile -> tile.getBorderingNeighbours()
											.stream()
											.noneMatch(neighbour -> neighbour.getValue() <= tile.getValue()))
						.mapToInt(tile -> tile.getValue() + 1)
						.sum()
				+ "";
	}
}
