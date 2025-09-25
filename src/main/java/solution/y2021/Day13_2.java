package solution.y2021;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import utils.Point;
import utils.soution.Solution;

public class Day13_2 extends Solution {
	public static void main(String[] args) {
		new Day13_2().solve();
	}

	@Override
	protected String doSolve() {
		var points = getPoints();
		for (var instruction : getInstructions()) {
			var nextPoints = new HashSet<Point>();
			for (var point : points) {
				if ("x".equals(instruction.getLeft())) {
					if (point.getX() <= instruction.getRight()) {
						nextPoints.add(point);
					} else {
						nextPoints.add(
								new Point(point.getX() - 2 * (point.getX() - instruction.getRight()), point.getY()));
					}
				} else {
					if (point.getY() <= instruction.getRight()) {
						nextPoints.add(point);
					} else {
						nextPoints.add(
								new Point(point.getX(), point.getY() - 2 * (point.getY() - instruction.getRight())));
					}
				}
			}
			points = nextPoints;
		}

		var solution = new StringBuilder();
		var maxX = points.stream().mapToInt(Point::getX).max().orElseThrow();
		var maxY = points.stream().mapToInt(Point::getY).max().orElseThrow();
		for (var y = 0; y <= maxY; y++) {
			for (var x = 0; x <= maxX; x++) {
				solution.append(points.contains(new Point(x, y)) ? "#" : " ");
			}
			solution.append("\n");
		}
		return solution.toString();
	}

	private Set<Point> getPoints() {
		var points = new HashSet<Point>();
		for (var point : input.subList(0, input.indexOf(""))) {
			var instruction = point.split(",");
			points.add(new Point(Integer.parseInt(instruction[0]), Integer.parseInt(instruction[1])));
		}
		return points;
	}

	private List<Pair<String, Integer>> getInstructions() {
		var instructions = new ArrayList<Pair<String, Integer>>();
		for (var instruction : input.subList(input.indexOf("") + 1, input.size())) {
			var fold = instruction.replace("=", " ").split(" ");
			instructions.add(Pair.of(fold[2], Integer.parseInt(fold[3])));
		}
		return instructions;
	}
}
