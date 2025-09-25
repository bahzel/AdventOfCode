package solution.y2019;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import utils.Direction;
import utils.Point;
import utils.soution.InstructionSolution;

public class Day3_1 extends InstructionSolution<List<Pair<Integer, Direction>>, Set<Point>> {
	public static void main(String[] args) {
		new Day3_1().solve();
	}

	@Override
	protected Set<Point> initializeValue() {
		return new HashSet<>();
	}

	@Override
	protected List<Pair<Integer, Direction>> transformInstruction(String instruction) {
		var instructions = instruction.split(",");
		var transformed = new ArrayList<Pair<Integer, Direction>>();

		for (var direction : instructions) {
			switch (direction.charAt(0)) {
			case 'U' -> transformed.add(Pair.of(Integer.parseInt(direction.substring(1)), Direction.UP));
			case 'D' -> transformed.add(Pair.of(Integer.parseInt(direction.substring(1)), Direction.DOWN));
			case 'L' -> transformed.add(Pair.of(Integer.parseInt(direction.substring(1)), Direction.LEFT));
			case 'R' -> transformed.add(Pair.of(Integer.parseInt(direction.substring(1)), Direction.RIGHT));
			default -> throw new IllegalArgumentException("Invalid instruction: " + direction);
			}
		}

		return transformed;
	}

	@Override
	protected boolean performInstruction(List<Pair<Integer, Direction>> integerDirectionPair, Set<Point> points) {
		if (points.isEmpty()) {
			var currentPoint = new Point(0, 0);
			for (var direction : integerDirectionPair) {
				for (int i = 0; i < direction.getLeft(); i++) {
					currentPoint = currentPoint.getNeighbour(direction.getRight());
					points.add(currentPoint);
				}
			}
		} else {
			var crossings = new HashSet<Point>();
			var currentPoint = new Point(0, 0);
			for (var direction : integerDirectionPair) {
				for (int i = 0; i < direction.getLeft(); i++) {
					currentPoint = currentPoint.getNeighbour(direction.getRight());
					if (points.contains(currentPoint)) {
						crossings.add(currentPoint);
					}
				}
			}
			points.clear();
			points.addAll(crossings);
		}

		return false;
	}

	@Override
	protected String getSolution(Set<Point> points) {
		var startingPoint = new Point(0, 0);

		return points.stream().mapToInt(point -> point.computeManhattanDistance(startingPoint)).min().orElseThrow()
				+ "";
	}
}
