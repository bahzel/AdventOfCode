package solution.y2023;

import java.util.List;
import java.util.function.BiFunction;

import org.apache.commons.lang3.tuple.Pair;

import utils.Direction;
import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day10_1 extends GridSolution<BiFunction<GridElement<?>, Direction, Pair<GridElement<?>, Direction>>> {
	private GridElement<BiFunction<GridElement<?>, Direction, Pair<GridElement<?>, Direction>>> startElement;

	public static void main(String[] args) {
		new Day10_1().solve();
	}

	@Override
	protected GridElement<BiFunction<GridElement<?>, Direction, Pair<GridElement<?>, Direction>>> transformCell(char ch,
			int x, int y) {
		return switch (ch) {
		case '|' -> new GridElement<>((element, direction) -> switch (direction) {
		case UP -> Pair.of(element.getUpperNeighbour(), Direction.UP);
		case DOWN -> Pair.of(element.getLowerNeighbour(), Direction.DOWN);
		default -> throw new IllegalArgumentException();
		}, x, y);
		case '-' -> new GridElement<>((element, direction) -> switch (direction) {
		case RIGHT -> Pair.of(element.getRightNeighbour(), Direction.RIGHT);
		case LEFT -> Pair.of(element.getLeftNeighbour(), Direction.LEFT);
		default -> throw new IllegalArgumentException();
		}, x, y);
		case 'L' -> new GridElement<>((element, direction) -> switch (direction) {
		case DOWN -> Pair.of(element.getRightNeighbour(), Direction.RIGHT);
		case LEFT -> Pair.of(element.getUpperNeighbour(), Direction.UP);
		default -> throw new IllegalArgumentException();
		}, x, y);
		case 'J' -> new GridElement<>((element, direction) -> switch (direction) {
		case DOWN -> Pair.of(element.getLeftNeighbour(), Direction.LEFT);
		case RIGHT -> Pair.of(element.getUpperNeighbour(), Direction.UP);
		default -> throw new IllegalArgumentException();
		}, x, y);
		case '7' -> new GridElement<>((element, direction) -> switch (direction) {
		case UP -> Pair.of(element.getLeftNeighbour(), Direction.LEFT);
		case RIGHT -> Pair.of(element.getLowerNeighbour(), Direction.DOWN);
		default -> throw new IllegalArgumentException();
		}, x, y);
		case 'F' -> new GridElement<>((element, direction) -> switch (direction) {
		case UP -> Pair.of(element.getRightNeighbour(), Direction.RIGHT);
		case LEFT -> Pair.of(element.getLowerNeighbour(), Direction.DOWN);
		default -> throw new IllegalArgumentException();
		}, x, y);
		case '.' -> new GridElement<>((element, direction) -> {
			throw new IllegalArgumentException();
		}, x, y);
		case 'S' -> {
			startElement = new GridElement<>(
					(element, direction) -> Pair.of(element.getNeighbour(direction), direction), x, y);
			yield startElement;
		}
		default -> throw new IllegalArgumentException();
		};
	}

	@Override
	protected String computeSolution() {
		var startingDirections = List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT);
		for (var startingDirection : startingDirections) {
			try {
				return computeLength(startingDirection) + "";
			} catch (IllegalArgumentException ignored) {
			}
		}

		throw new IllegalStateException();
	}

	private int computeLength(Direction startingDirection) {
		var moves = 1;
		Pair<GridElement<?>, Direction> currentPosition = startElement	.getValue()
																		.apply(startElement, startingDirection);

		while (currentPosition.getLeft() != startElement) {
			moves++;
			@SuppressWarnings("unchecked")
			var nextElement = (GridElement<BiFunction<GridElement<?>, Direction, Pair<GridElement<?>, Direction>>>) currentPosition.getLeft();
			currentPosition = nextElement.getValue().apply(nextElement, currentPosition.getRight());
		}

		return moves / 2;
	}
}