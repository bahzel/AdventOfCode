package solution.y2022;

import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day8_2 extends GridSolution<Integer> {
	public static void main(String[] args) {
		new Day8_2().solve();
	}

	@Override
	protected GridElement<Integer> transformCell(char ch, int x, int y) {
		return new GridElement<>(Integer.parseInt(String.valueOf(ch)), x, y);
	}

	@Override
	protected String computeSolution() {
		return stream().mapToInt(this::computeScenicScore).max().orElseThrow() + "";
	}

	private int computeScenicScore(GridElement<Integer> element) {
		var scenicScore = 1;

		var neighbour = element.getLeftNeighbour();
		var count = 0;
		while (neighbour != null && neighbour.getValue() < element.getValue()) {
			neighbour = neighbour.getLeftNeighbour();
			count++;
		}
		if (neighbour != null) {
			count++;
		}
		scenicScore *= count;

		neighbour = element.getRightNeighbour();
		count = 0;
		while (neighbour != null && neighbour.getValue() < element.getValue()) {
			neighbour = neighbour.getRightNeighbour();
			count++;
		}
		if (neighbour != null) {
			count++;
		}
		scenicScore *= count;

		neighbour = element.getUpperNeighbour();
		count = 0;
		while (neighbour != null && neighbour.getValue() < element.getValue()) {
			neighbour = neighbour.getUpperNeighbour();
			count++;
		}
		if (neighbour != null) {
			count++;
		}
		scenicScore *= count;

		neighbour = element.getLowerNeighbour();
		count = 0;
		while (neighbour != null && neighbour.getValue() < element.getValue()) {
			neighbour = neighbour.getLowerNeighbour();
			count++;
		}
		if (neighbour != null) {
			count++;
		}
		return scenicScore * count;
	}
}
