package solution.y2018;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import utils.Point;
import utils.soution.InstructionSolution;

public class Day17_1 extends InstructionSolution<String, Map<Point, Material>> {
	public static void main(String[] args) {
		new Day17_1().solve();
	}

	@Override
	protected Map<Point, Material> initializeValue() {
		return new HashMap<>();
	}

	@Override
	protected String transformInstruction(String instruction) {
		return instruction;
	}

	@Override
	protected boolean performInstruction(String s, Map<Point, Material> points) {
		if (s.startsWith("x")) {
			var instruction = s.replace("x=", "").replace(", y=", "..").split("\\.\\.");
			var x = Integer.parseInt(instruction[0]);
			var yFrom = Integer.parseInt(instruction[1]);
			var yTo = Integer.parseInt(instruction[2]);

			for (int y = yFrom; y <= yTo; y++) {
				points.put(new Point(x, y), Material.Clay);
			}
		} else {
			var instruction = s.replace("y=", "").replace(", x=", "..").split("\\.\\.");
			var y = Integer.parseInt(instruction[0]);
			var xFrom = Integer.parseInt(instruction[1]);
			var xTo = Integer.parseInt(instruction[2]);

			for (int x = xFrom; x <= xTo; x++) {
				points.put(new Point(x, y), Material.Clay);
			}
		}

		return false;
	}

	@Override
	protected String getSolution(Map<Point, Material> points) {
		var reachedPoints = new HashSet<Point>();
		var startingPoint = new Point(500, 0);
		var minimumDepth = points.keySet().stream().mapToInt(Point::getY).min().orElseThrow();
		var maximumDepth = points.keySet().stream().mapToInt(Point::getY).max().orElseThrow();

		while (dropDown(startingPoint, maximumDepth, points, reachedPoints)) {
			//drop
		}

		draw(points, reachedPoints);
		return reachedPoints.stream().filter(point -> point.getY() >= minimumDepth).count() + "";
	}

	private void draw(Map<Point, Material> points, HashSet<Point> reachedPoints) {
		var maximumDepth = points.keySet().stream().mapToInt(Point::getY).max().orElseThrow();
		var minimumX = points.keySet().stream().mapToInt(Point::getX).min().orElseThrow();
		var maximumX = points.keySet().stream().mapToInt(Point::getX).max().orElseThrow();

		for (int y = 0; y <= maximumDepth + 1; y++) {
			for (int x = minimumX - 2; x <= maximumX + 2; x++) {
				var point = new Point(x, y);
				var material = points.get(point);
				if (material == null) {
					if (reachedPoints.contains(point)) {
						print("|");
					} else {
						print(".");
					}
				} else if (material == Material.Clay) {
					print("#");
				} else if (material == Material.Water) {
					print("~");
				}
			}
			println();
		}
	}

	private boolean dropDown(Point drop, int maximumDepth, Map<Point, Material> points, HashSet<Point> reachedPoints) {
		while (!points.containsKey(drop.getLowerNeighbour())) {
			drop = drop.getLowerNeighbour();

			if (drop.getY() > maximumDepth) {
				return false;
			}

			reachedPoints.add(drop);
		}

		var hasLeftBorder = hasLeftBorder(points, drop, reachedPoints);
		var hasRightBorder = hasRightBorder(points, drop, reachedPoints);
		if (hasLeftBorder && hasRightBorder) {
			fillLine(points, drop);
			return true;
		}

		var continueLeft = false;
		var continueRight = false;
		if (!hasLeftBorder) {
			var leftDrop = drop;
			while (points.containsKey(leftDrop.getLowerNeighbour())) {
				leftDrop = leftDrop.getLeftNeighbour();
			}
			continueLeft = dropDown(leftDrop, maximumDepth, points, reachedPoints);
		}
		if (!hasRightBorder) {
			while (points.containsKey(drop.getLowerNeighbour())) {
				drop = drop.getRightNeighbour();
			}
			continueRight = dropDown(drop, maximumDepth, points, reachedPoints);
		}

		return continueLeft || continueRight;
	}

	private boolean hasLeftBorder(Map<Point, Material> points, Point drop, HashSet<Point> reachedPoints) {
		while (points.containsKey(drop.getLowerNeighbour())) {
			if (!points.containsKey(drop.getLeftNeighbour())) {
				drop = drop.getLeftNeighbour();
				reachedPoints.add(drop);
			} else {
				return true;
			}
		}
		return false;
	}

	private boolean hasRightBorder(Map<Point, Material> points, Point drop, HashSet<Point> reachedPoints) {
		while (points.containsKey(drop.getLowerNeighbour())) {
			if (!points.containsKey(drop.getRightNeighbour())) {
				drop = drop.getRightNeighbour();
				reachedPoints.add(drop);
			} else {
				return true;
			}
		}
		return false;
	}

	private void fillLine(Map<Point, Material> points, Point drop) {
		points.put(drop, Material.Water);

		var nextPoint = drop;
		while (!points.containsKey(nextPoint.getLeftNeighbour())) {
			nextPoint = nextPoint.getLeftNeighbour();
			points.put(nextPoint, Material.Water);
		}

		nextPoint = drop;
		while (!points.containsKey(nextPoint.getRightNeighbour())) {
			nextPoint = nextPoint.getRightNeighbour();
			points.put(nextPoint, Material.Water);
		}
	}
}

enum Material {
	Clay, Water
}
