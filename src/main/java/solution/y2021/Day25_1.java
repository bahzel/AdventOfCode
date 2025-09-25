package solution.y2021;

import java.util.concurrent.atomic.AtomicBoolean;

import utils.Direction;
import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day25_1 extends GridSolution<Direction> {
	public static void main(String[] args) {
		new Day25_1().solve();
	}

	@Override
	protected GridElement<Direction> transformCell(char ch, int x, int y) {
		return switch (ch) {
		case '>' -> new GridElement<>(Direction.RIGHT, x, y);
		case 'v' -> new GridElement<>(Direction.DOWN, x, y);
		case '.' -> new GridElement<>(null, x, y);
		default -> throw new IllegalArgumentException("Unknown character: " + ch);
		};
	}

	@Override
	protected String computeSolution() {
		for (var i = 0; i < grid[0].length; i++) {
			grid[grid.length - 1][i].setRightNeighbour(grid[0][i]);
		}
		for (GridElement<Direction>[] gridElements : grid) {
			gridElements[gridElements.length - 1].setLowerNeighbour(gridElements[0]);
		}

		for (int i = 1;; i++) {
			var changed = new AtomicBoolean(false);
			stream().filter(element -> element.getValue() != null).forEach(element -> {
				if (element.getValue() == Direction.RIGHT && element.getRightNeighbour().getValue() == null) {
					element.getRightNeighbour().setNextValue(Direction.RIGHT);
					changed.set(true);
				} else {
					element.setNextValue(element.getValue());
				}
			});
			stream().forEach(GridElement::assignNextValue);
			stream().filter(element -> element.getValue() != null).forEach(element -> {
				if (element.getValue() == Direction.DOWN && element.getLowerNeighbour().getValue() == null) {
					element.getLowerNeighbour().setNextValue(Direction.DOWN);
					changed.set(true);
				} else {
					element.setNextValue(element.getValue());
				}
			});
			stream().forEach(GridElement::assignNextValue);
			if (!changed.get()) {
				return i + "";
			}
		}
	}
}
