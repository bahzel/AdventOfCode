package solution.y2018;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.apache.commons.lang3.tuple.Pair;

import utils.Point;
import utils.soution.GridElement;
import utils.soution.Solution;

public class Day20_2 extends Solution {
	public static void main(String[] args) {
		new Day20_2().solve();
	}

	@Override
	protected String doSolve() {
		var startPosition = new GridElement<>(true, 0, 0);
		var map = new HashMap<Point, GridElement<Boolean>>();
		walk(1, input.getFirst(), startPosition, map);

		return findRoomsAbove1000(startPosition) + "";
	}

	private int findRoomsAbove1000(GridElement<Boolean> startPoint) {
		var cache = new HashSet<GridElement<Boolean>>();
		Queue<Pair<GridElement<Boolean>, Integer>> queue = new LinkedList<>();
		queue.add(Pair.of(startPoint, 0));
		var count = 0;

		while (!queue.isEmpty()) {
			var room = queue.remove();
			if (cache.add(room.getLeft())) {
				if (room.getRight() >= 1000) {
					count++;
				}
				room.getLeft()
					.getAllNeighbours()
					.forEach(neighbour -> queue.add(Pair.of(neighbour, room.getRight() + 1)));
			}
		}

		return count;
	}

	private int walk(int currentIndex, String instruction, GridElement<Boolean> startPosition,
			Map<Point, GridElement<Boolean>> map) {
		var currentPosition = startPosition;
		while (true) {
			Point newCoordinate;
			GridElement<Boolean> newPosition;

			switch (instruction.charAt(currentIndex)) {
			case 'N':
				newCoordinate = currentPosition.getCoordinates().getUpperNeighbour();
				newPosition = map.computeIfAbsent(newCoordinate, coordinate -> new GridElement<>(true, newCoordinate));
				currentPosition.setUpperNeighbour(newPosition);
				newPosition.setLowerNeighbour(currentPosition);
				currentPosition = newPosition;
				currentIndex++;
				break;
			case 'E':
				newCoordinate = currentPosition.getCoordinates().getRightNeighbour();
				newPosition = map.computeIfAbsent(newCoordinate, coordinate -> new GridElement<>(true, newCoordinate));
				currentPosition.setRightNeighbour(newPosition);
				newPosition.setLeftNeighbour(currentPosition);
				currentPosition = newPosition;
				currentIndex++;
				break;
			case 'S':
				newCoordinate = currentPosition.getCoordinates().getLowerNeighbour();
				newPosition = map.computeIfAbsent(newCoordinate, coordinate -> new GridElement<>(true, newCoordinate));
				currentPosition.setLowerNeighbour(newPosition);
				newPosition.setUpperNeighbour(currentPosition);
				currentPosition = newPosition;
				currentIndex++;
				break;
			case 'W':
				newCoordinate = currentPosition.getCoordinates().getLeftNeighbour();
				newPosition = map.computeIfAbsent(newCoordinate, coordinate -> new GridElement<>(true, newCoordinate));
				currentPosition.setLeftNeighbour(newPosition);
				newPosition.setRightNeighbour(currentPosition);
				currentPosition = newPosition;
				currentIndex++;
				break;
			case '(':
				currentIndex = walk(currentIndex + 1, instruction, currentPosition, map);
				break;
			case '|':
				return walk(currentIndex + 1, instruction, startPosition, map);
			case ')':
			case '$':
				return currentIndex + 1;
			default:
				throw new IllegalStateException();
			}
		}
	}
}
