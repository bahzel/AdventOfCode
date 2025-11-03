package solution.y2023;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.function.BiFunction;

import org.apache.commons.lang3.tuple.Pair;

import utils.Direction;
import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day10_2 extends GridSolution<BiFunction<GridElement<?>, Direction, Pair<GridElement<?>, Direction>>> {
	private GridElement<BiFunction<GridElement<?>, Direction, Pair<GridElement<?>, Direction>>> startElement;

	public static void main(String[] args) {
		new Day10_2().solve();
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
				return computeEnclosedTiles(startingDirection) + "";
			} catch (IllegalArgumentException ignored) {
			}
		}

		throw new IllegalStateException();
	}

	private int computeEnclosedTiles(Direction startingDirection) {
		Set<GridElement<BiFunction<GridElement<?>, Direction, Pair<GridElement<?>, Direction>>>> loop = new HashSet<>();
		loop.add(startElement);
		Pair<GridElement<?>, Direction> currentPosition = startElement	.getValue()
																		.apply(startElement, startingDirection);

		while (currentPosition.getLeft() != startElement) {
			@SuppressWarnings("unchecked")
			var nextElement = (GridElement<BiFunction<GridElement<?>, Direction, Pair<GridElement<?>, Direction>>>) currentPosition.getLeft();
			loop.add(nextElement);
			currentPosition = nextElement.getValue().apply(nextElement, currentPosition.getRight());
		}

		Set<GridElement<?>> enclosed = new HashSet<>();
		currentPosition = startElement.getValue().apply(startElement, startingDirection);
		while (currentPosition.getLeft() != startElement) {
			@SuppressWarnings("unchecked")
			var nextElement = (GridElement<BiFunction<GridElement<?>, Direction, Pair<GridElement<?>, Direction>>>) currentPosition.getLeft();
			enclosed.addAll(getNeighboursNotContaining(loop,
					nextElement.getNeighbour(currentPosition.getRight().turn(Direction.LEFT))));
			currentPosition = nextElement.getValue().apply(nextElement, currentPosition.getRight());
			enclosed.addAll(getNeighboursNotContaining(loop,
					nextElement.getNeighbour(currentPosition.getRight().turn(Direction.LEFT))));
		}

		for (var y = 0; y < grid[0].length; y++) {
			for (var x = 0; x < grid.length; x++) {
				var currentElement = grid[x][y];
				if (loop.contains(currentElement)) {
					print(input.get(y).charAt(x));
				} else if (enclosed.contains(currentElement)) {
					print("X");
				} else {
					print(".");
				}
			}
			println();
		}

		return enclosed.size();
	}

	private Collection<GridElement<?>> getNeighboursNotContaining(
			Set<GridElement<BiFunction<GridElement<?>, Direction, Pair<GridElement<?>, Direction>>>> loop,
			GridElement<?> start) {
		var neighbours = new HashSet<GridElement<?>>();
		if (start == null) {
			return neighbours;
		}

		Queue<GridElement<?>> queue = new LinkedList<>();
		queue.add(start);

		while (!queue.isEmpty()) {
			var currentElement = queue.poll();
			if (loop.contains(currentElement) || !neighbours.add(currentElement)) {
				continue;
			}

			if (currentElement.getBorderingNeighbours().size() < 4) {
				throw new IllegalArgumentException();
			}
			queue.addAll(currentElement.getBorderingNeighbours());
		}

		return neighbours;
	}
}