package solution.y2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import utils.Direction;
import utils.Point;
import utils.soution.InstructionSolution;

public class Day3_2 extends InstructionSolution<List<Pair<Integer, Direction>>, Map<Point, Integer>> {
	public static void main(String[] args) {
		new Day3_2().solve();
	}

	@Override
	protected Map<Point, Integer> initializeValue() {
		return new HashMap<>();
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
	protected boolean performInstruction(List<Pair<Integer, Direction>> integerDirectionPair,
			Map<Point, Integer> points) {
		if (points.isEmpty()) {
			var currentPoint = new Point(0, 0);
			var steps = 0;
			for (var direction : integerDirectionPair) {
				for (int i = 0; i < direction.getLeft(); i++) {
					steps++;
					currentPoint = currentPoint.getNeighbour(direction.getRight());
					points.putIfAbsent(currentPoint, steps);
				}
			}
		} else {
			var crossings = new HashMap<Point, Integer>();
			var currentPoint = new Point(0, 0);
			var steps = 0;
			for (var direction : integerDirectionPair) {
				for (int i = 0; i < direction.getLeft(); i++) {
					steps++;
					currentPoint = currentPoint.getNeighbour(direction.getRight());
					if (points.containsKey(currentPoint)) {
						crossings.putIfAbsent(currentPoint, steps + points.get(currentPoint));
					}
				}
			}
			points.clear();
			points.putAll(crossings);
		}

		return false;
	}

	@Override
	protected String getSolution(Map<Point, Integer> points) {
		return points.values().stream().mapToInt(Integer::intValue).min().orElseThrow() + "";
	}
}
