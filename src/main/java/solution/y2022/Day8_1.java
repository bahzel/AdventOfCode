package solution.y2022;

import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day8_1 extends GridSolution<Integer> {
	public static void main(String[] args) {
		new Day8_1().solve();
	}

	@Override
	protected GridElement<Integer> transformCell(char ch, int x, int y) {
		return new GridElement<>(Integer.parseInt(String.valueOf(ch)), x, y);
	}

	@Override
	protected String computeSolution() {
		return stream().filter(this::isVisible).count() + "";
	}

	private boolean isVisible(GridElement<Integer> element) {
		var neighbour = element.getLeftNeighbour();
		while (neighbour != null && neighbour.getValue() < element.getValue()) {
			neighbour = neighbour.getLeftNeighbour();
		}
		if (neighbour == null) {
			return true;
		}

		neighbour = element.getRightNeighbour();
		while (neighbour != null && neighbour.getValue() < element.getValue()) {
			neighbour = neighbour.getRightNeighbour();
		}
		if (neighbour == null) {
			return true;
		}

		neighbour = element.getUpperNeighbour();
		while (neighbour != null && neighbour.getValue() < element.getValue()) {
			neighbour = neighbour.getUpperNeighbour();
		}
		if (neighbour == null) {
			return true;
		}

		neighbour = element.getLowerNeighbour();
		while (neighbour != null && neighbour.getValue() < element.getValue()) {
			neighbour = neighbour.getLowerNeighbour();
		}
		return neighbour == null;
	}
}
