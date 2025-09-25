package solution.y2018;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import utils.Point;
import utils.soution.MapSolution;

public class Day6_1 extends MapSolution<Map<Point, List<Point>>> {
	public static void main(String[] args) {
		new Day6_1().solve();
	}

	@Override
	protected Map<Point, List<Point>> initializeMapping() {
		return new HashMap<>();
	}

	@Override
	protected void transformInstruction(String instruction, Map<Point, List<Point>> pointListMap) {
		var coordinates = instruction.split(", ");
		pointListMap.put(new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])),
				new ArrayList<>());
	}

	@Override
	protected String computeSolution(Map<Point, List<Point>> pointListMap) {
		var xMin = pointListMap.keySet().stream().mapToInt(Point::getX).min().orElseThrow();
		var xMax = pointListMap.keySet().stream().mapToInt(Point::getX).max().orElseThrow();
		var yMin = pointListMap.keySet().stream().mapToInt(Point::getY).min().orElseThrow();
		var yMax = pointListMap.keySet().stream().mapToInt(Point::getY).max().orElseThrow();
		fillMap(pointListMap, xMin, xMax, yMin, yMax);

		var infinityPoints = new HashSet<Point>();
		for (var point : pointListMap.keySet()) {
			for (var nearestPoint : pointListMap.get(point)) {
				if (nearestPoint.getX() == xMin || nearestPoint.getX() == xMax || nearestPoint.getY() == yMin
						|| nearestPoint.getY() == yMax) {
					infinityPoints.add(point);
				}
			}
		}
		return "" + pointListMap.entrySet()
								.stream()
								.filter(entry -> !infinityPoints.contains(entry.getKey()))
								.mapToInt(entry -> entry.getValue().size())
								.max()
								.orElseThrow();
	}

	private void fillMap(Map<Point, List<Point>> pointListMap, int xMin, int xMax, int yMin, int yMax) {
		for (int x = xMin; x <= xMax; x++) {
			for (int y = yMin; y <= yMax; y++) {
				var newPoint = new Point(x, y);
				var closestDistance = Integer.MAX_VALUE;
				Point closestPoint = null;

				for (var point : pointListMap.keySet()) {
					var distance = point.computeManhattanDistance(newPoint);
					if (distance < closestDistance) {
						closestPoint = point;
						closestDistance = distance;
					} else if (distance == closestDistance) {
						closestPoint = null;
					}
				}

				if (closestPoint != null) {
					pointListMap.get(closestPoint).add(newPoint);
				}
			}
		}
	}
}
