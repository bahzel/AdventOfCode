package solution.y2023;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.BiFunction;

import org.apache.commons.lang3.tuple.Pair;

import utils.Direction;
import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day16_1
		extends GridSolution<BiFunction<GridElement<?>, Direction, List<Pair<GridElement<?>, Direction>>>> {
	public static void main(String[] args) {
		new Day16_1().solve();
	}

	@Override
	protected GridElement<BiFunction<GridElement<?>, Direction, List<Pair<GridElement<?>, Direction>>>> transformCell(
			char ch, int x, int y) {
		return switch (ch) {
		case '.' ->
			new GridElement<>((cell, direction) -> List.of(Pair.of(cell.getNeighbour(direction), direction)), x, y);
		case '|' -> new GridElement<>((cell, direction) -> switch (direction) {
		case Direction.UP, Direction.DOWN -> List.of(Pair.of(cell.getNeighbour(direction), direction));
		default -> List.of(Pair.of(cell.getNeighbour(Direction.UP), Direction.UP),
				Pair.of(cell.getNeighbour(Direction.DOWN), Direction.DOWN));
		}, x, y);
		case '-' -> new GridElement<>((cell, direction) -> switch (direction) {
		case Direction.LEFT, Direction.RIGHT -> List.of(Pair.of(cell.getNeighbour(direction), direction));
		default -> List.of(Pair.of(cell.getNeighbour(Direction.LEFT), Direction.LEFT),
				Pair.of(cell.getNeighbour(Direction.RIGHT), Direction.RIGHT));
		}, x, y);
		case '/' -> new GridElement<>((cell, direction) -> switch (direction) {
		case Direction.LEFT -> List.of(Pair.of(cell.getNeighbour(Direction.DOWN), Direction.DOWN));
		case Direction.UP -> List.of(Pair.of(cell.getNeighbour(Direction.RIGHT), Direction.RIGHT));
		case Direction.RIGHT -> List.of(Pair.of(cell.getNeighbour(Direction.UP), Direction.UP));
		default -> List.of(Pair.of(cell.getNeighbour(Direction.LEFT), Direction.LEFT));
		}, x, y);
		case '\\' -> new GridElement<>((cell, direction) -> switch (direction) {
		case Direction.LEFT -> List.of(Pair.of(cell.getNeighbour(Direction.UP), Direction.UP));
		case Direction.UP -> List.of(Pair.of(cell.getNeighbour(Direction.LEFT), Direction.LEFT));
		case Direction.RIGHT -> List.of(Pair.of(cell.getNeighbour(Direction.DOWN), Direction.DOWN));
		default -> List.of(Pair.of(cell.getNeighbour(Direction.RIGHT), Direction.RIGHT));
		}, x, y);
		default -> throw new IllegalArgumentException();
		};
	}

	@Override
	protected String computeSolution() {
		return countIlluminatedTiles(grid[0][0], Direction.RIGHT) + "";
	}

	protected long countIlluminatedTiles(
			GridElement<BiFunction<GridElement<?>, Direction, List<Pair<GridElement<?>, Direction>>>> start,
			Direction direction) {
		Queue<Pair<GridElement<BiFunction<GridElement<?>, Direction, List<Pair<GridElement<?>, Direction>>>>, Direction>> queue = new LinkedList<>();
		queue.add(Pair.of(start, direction));
		var CACHE = new HashSet<Pair<GridElement<BiFunction<GridElement<?>, Direction, List<Pair<GridElement<?>, Direction>>>>, Direction>>();

		while (!queue.isEmpty()) {
			var current = queue.poll();
			if (current == null || !CACHE.add(current)) {
				continue;
			}

			var nextSteps = current.getLeft().getValue().apply(current.getLeft(), current.getRight());
			for (var step : nextSteps) {
				if (step.getLeft() != null) {
					@SuppressWarnings("unchecked")
					var typedStep = (Pair<GridElement<BiFunction<GridElement<?>, Direction, List<Pair<GridElement<?>, Direction>>>>, Direction>) (Pair<?, ?>) step;
					queue.add(typedStep);
				}
			}
		}

		return CACHE.stream().map(Pair::getLeft).distinct().count();
	}
}