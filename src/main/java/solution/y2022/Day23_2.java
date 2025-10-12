package solution.y2022;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import utils.Direction;
import utils.Point;
import utils.soution.Solution;

public class Day23_2 extends Solution {
	public static void main(String[] args) {
		new Day23_2().solve();
	}

	@Override
	protected String doSolve() {
		var pointSet = initializeElves();
		var checkList = new ArrayList<>(List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT));

		var counter = 0;
		while (true) {
			counter++;
			var moveMap = new HashMap<Point, List<Point>>();
			for (var elf : pointSet) {
				if (!hasNeighbours(elf, pointSet)) {
					continue;
				}
				for (var direction : checkList) {
					if (direction == Direction.UP) {
						if (!hasNorthernNeighbours(elf, pointSet)) {
							moveMap.computeIfAbsent(elf.getUpperNeighbour(), key -> new ArrayList<>()).add(elf);
							break;
						}
					} else if (direction == Direction.DOWN) {
						if (!hasSouthernNeighbours(elf, pointSet)) {
							moveMap.computeIfAbsent(elf.getLowerNeighbour(), key -> new ArrayList<>()).add(elf);
							break;
						}
					} else if (direction == Direction.LEFT) {
						if (!hasWesternNeighbours(elf, pointSet)) {
							moveMap.computeIfAbsent(elf.getLeftNeighbour(), key -> new ArrayList<>()).add(elf);
							break;
						}
					} else if (direction == Direction.RIGHT) {
						if (!hasEasternNeighbours(elf, pointSet)) {
							moveMap.computeIfAbsent(elf.getRightNeighbour(), key -> new ArrayList<>()).add(elf);
							break;
						}
					}
				}
			}

			var somethingChanged = new AtomicBoolean(false);
			moveMap.forEach((newPosition, oldPositions) -> {
				if (oldPositions.size() == 1) {
					pointSet.remove(oldPositions.getFirst());
					pointSet.add(newPosition);
					somethingChanged.set(true);
				}
			});
			if (!somethingChanged.get()) {
				break;
			}

			checkList.add(checkList.removeFirst());
		}

		return counter + "";
	}

	private Set<Point> initializeElves() {
		var points = new HashSet<Point>();
		for (var y = 0; y < input.size(); y++) {
			var row = input.get(y);
			for (var x = 0; x < row.length(); x++) {
				if (row.charAt(x) == '#') {
					points.add(new Point(x, y));
				}
			}
		}
		return points;
	}

	private boolean hasNeighbours(Point elf, Set<Point> pointSet) {
		return hasNorthernNeighbours(elf, pointSet) || hasSouthernNeighbours(elf, pointSet)
				|| pointSet.contains(elf.getLeftNeighbour()) || pointSet.contains(elf.getRightNeighbour());
	}

	private boolean hasNorthernNeighbours(Point elf, Set<Point> pointSet) {
		return pointSet.contains(elf.getUpperNeighbour())
				|| pointSet.contains(elf.getUpperNeighbour().getLeftNeighbour())
				|| pointSet.contains(elf.getUpperNeighbour().getRightNeighbour());
	}

	private boolean hasSouthernNeighbours(Point elf, Set<Point> pointSet) {
		return pointSet.contains(elf.getLowerNeighbour())
				|| pointSet.contains(elf.getLowerNeighbour().getLeftNeighbour())
				|| pointSet.contains(elf.getLowerNeighbour().getRightNeighbour());
	}

	private boolean hasWesternNeighbours(Point elf, Set<Point> pointSet) {
		return pointSet.contains(elf.getLeftNeighbour())
				|| pointSet.contains(elf.getLeftNeighbour().getUpperNeighbour())
				|| pointSet.contains(elf.getLeftNeighbour().getLowerNeighbour());
	}

	private boolean hasEasternNeighbours(Point elf, Set<Point> pointSet) {
		return pointSet.contains(elf.getRightNeighbour())
				|| pointSet.contains(elf.getRightNeighbour().getUpperNeighbour())
				|| pointSet.contains(elf.getRightNeighbour().getLowerNeighbour());
	}
}