package solution.y2024;

import java.util.HashSet;

import org.apache.commons.lang3.tuple.Pair;
import utils.Direction;
import utils.Point;
import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day6_2 extends GridSolution<Boolean> {
	private GridElement<Boolean> startingPoint;

	public static void main(String[] args) {
		new Day6_2().solve();
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
		return stream().filter(GridElement::getValue)
					   .filter(position -> position != startingPoint)
					   .filter(this::hasLoop)
					   .count() + "";
	}

	private boolean hasLoop(GridElement<Boolean> additionalObstacle) {
		var currentPosition = startingPoint;
		var currentDirection = Direction.UP;
		var cache = new HashSet<Pair<Point, Direction>>();
		cache.add(Pair.of(currentPosition.getCoordinates(), currentDirection));

		while (true) {
			var neighbour = currentPosition.getNeighbour(currentDirection);
			if (neighbour == null) {
				return false;
			} else if (neighbour.getValue() && neighbour != additionalObstacle) {
				currentPosition = neighbour;
				if (!cache.add(Pair.of(currentPosition.getCoordinates(), currentDirection))) {
					return true;
				}
			} else {
				currentDirection = currentDirection.turn(Direction.RIGHT);
			}
		}
	}
}
