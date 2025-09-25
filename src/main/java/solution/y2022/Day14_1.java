package solution.y2022;

import java.util.HashSet;
import java.util.Set;

import utils.Point;
import utils.soution.MapSolution;

public class Day14_1 extends MapSolution<Set<Point>> {
	public static void main(String[] args) {
		new Day14_1().solve();
	}

	@Override
	protected Set<Point> initializeMapping() {
		return new HashSet<>();
	}

	@Override
	protected void transformInstruction(String instruction, Set<Point> points) {
		var instructions = instruction.split(" -> ");
		var pointInstructions = instructions[0].split(",");
		var currentPoint = new Point(Integer.parseInt(pointInstructions[0]), Integer.parseInt(pointInstructions[1]));
		points.add(currentPoint);

		for (var i = 1; i < instructions.length; i++) {
			pointInstructions = instructions[i].split(",");
			var toX = Integer.parseInt(pointInstructions[0]);
			var toY = Integer.parseInt(pointInstructions[1]);
			for (var x = currentPoint.getX() + 1; x <= toX; x++) {
				currentPoint = new Point(x, currentPoint.getY());
				points.add(currentPoint);
			}
			for (var x = currentPoint.getX() - 1; x >= toX; x--) {
				currentPoint = new Point(x, currentPoint.getY());
				points.add(currentPoint);
			}
			for (var y = currentPoint.getY() + 1; y <= toY; y++) {
				currentPoint = new Point(currentPoint.getX(), y);
				points.add(currentPoint);
			}
			for (var y = currentPoint.getY() - 1; y >= toY; y--) {
				currentPoint = new Point(currentPoint.getX(), y);
				points.add(currentPoint);
			}
		}
	}

	@Override
	protected String computeSolution(Set<Point> points) {
		var blockedTiles = points.size();
		var maximumY = points.stream().mapToInt(Point::getY).max().orElseThrow();

		while (true) {
			var currentPoint = new Point(500, 0);
			while (currentPoint.getY() < maximumY) {
				if (!points.contains(currentPoint.getLowerNeighbour())) {
					currentPoint = currentPoint.getLowerNeighbour();
				} else if (!points.contains(currentPoint.getLowerNeighbour().getLeftNeighbour())) {
					currentPoint = currentPoint.getLowerNeighbour().getLeftNeighbour();
				} else if (!points.contains(currentPoint.getLowerNeighbour().getRightNeighbour())) {
					currentPoint = currentPoint.getLowerNeighbour().getRightNeighbour();
				} else {
					break;
				}
			}

			if (currentPoint.getY() >= maximumY) {
				break;
			} else {
				points.add(currentPoint);
			}
		}

		return points.size() - blockedTiles + "";
	}
}
