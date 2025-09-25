package solution.y2024;

import java.util.HashSet;

import utils.Direction;
import utils.Point;
import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day6_1 extends GridSolution<Boolean> {
	private GridElement<Boolean> startingPoint;

	public static void main(String[] args) {
		new Day6_1().solve();
	}

	@Override
	protected GridElement<Boolean> transformCell(char ch, int x, int y) {
		return switch (ch) {
			case '.' -> new GridElement<>(true, x, y);
			case '#' -> new GridElement<>(false, x, y);
			case '^' -> {
				startingPoint = new GridElement<>(true, x, y);
				yield startingPoint;
			}
			default -> throw new IllegalArgumentException("Invalid character: " + ch);
		};
	}

	@Override
	protected String computeSolution() {
		var cache = new HashSet<Point>();
		cache.add(startingPoint.getCoordinates());
		var currentPosition = startingPoint;
		var currentDirection = Direction.UP;

		while (true) {
			if (currentPosition.getNeighbour(currentDirection) == null) {
				return cache.size() + "";
			} else if (currentPosition.getNeighbour(currentDirection).getValue()) {
				currentPosition = currentPosition.getNeighbour(currentDirection);
				cache.add(currentPosition.getCoordinates());
			} else {
				currentDirection = currentDirection.turn(Direction.RIGHT);
			}
		}
	}
}
