package solution.y2018;

import java.util.ArrayList;
import java.util.List;

import utils.Point;
import utils.soution.MapSolution;

public class Day6_2 extends MapSolution<List<Point>> {
	private final int MAX_DISTANCE = 10000;

	public static void main(String[] args) {
		new Day6_2().solve();
	}

	@Override
	protected List<Point> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<Point> pointList) {
		var coordinates = instruction.split(", ");
		pointList.add(new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])));
	}

	@Override
	protected String computeSolution(List<Point> pointList) {
		var xMin = pointList.stream().mapToInt(Point::getX).min().orElseThrow();
		var xMax = pointList.stream().mapToInt(Point::getX).max().orElseThrow();
		var yMin = pointList.stream().mapToInt(Point::getY).min().orElseThrow();
		var yMax = pointList.stream().mapToInt(Point::getY).max().orElseThrow();
		var offset = MAX_DISTANCE / pointList.size() + 1;
		var counter = 0L;

		for (int x = xMin - offset; x <= xMax + offset; x++) {
			for (int y = yMin - offset; y <= yMax + offset; y++) {
				var newPoint = new Point(x, y);
				if (pointList	.stream()
								.mapToLong(point -> point.computeManhattanDistance(newPoint))
								.sum() < MAX_DISTANCE) {
					counter++;
				}
			}
		}

		return counter + "";
	}
}
