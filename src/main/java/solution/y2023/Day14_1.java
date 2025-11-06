package solution.y2023;

import utils.Direction;
import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day14_1 extends GridSolution<Rock> {
	public static void main(String[] args) {
		new Day14_1().solve();
	}

	@Override
	protected GridElement<Rock> transformCell(char ch, int x, int y) {
		return switch (ch) {
		case 'O' -> new GridElement<>(Rock.ROUND, x, y);
		case '#' -> new GridElement<>(Rock.CUBE, x, y);
		case '.' -> new GridElement<>(Rock.EMPTY, x, y);
		default -> throw new IllegalArgumentException("Invalid character " + ch);
		};
	}

	@Override
	protected String computeSolution() {
		tiltNorth();
		return countLoad() + "";
	}

	protected void tiltNorth() {
		for (var i = 0; i < grid[0].length; i++) {
			for (GridElement<Rock>[] gridElements : grid) {
				if (gridElements[i].getValue() == Rock.ROUND) {
					move(gridElements[i], Direction.UP);
				}
			}
		}
	}

	protected void move(GridElement<Rock> element, Direction direction) {
		var nextElement = element;
		while (nextElement.getNeighbour(direction) != null
				&& nextElement.getNeighbour(direction).getValue() == Rock.EMPTY) {
			nextElement = nextElement.getNeighbour(direction);
		}
		element.setValue(Rock.EMPTY);
		nextElement.setValue(Rock.ROUND);
	}

	protected int countLoad() {
		return stream()	.filter(rock -> rock.getValue() == Rock.ROUND)
						.mapToInt(rock -> grid[0].length - rock.getCoordinates().getY())
						.sum();
	}
}

enum Rock {
	ROUND, CUBE, EMPTY,
}