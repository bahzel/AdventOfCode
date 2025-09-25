package solution.y2018;

import java.util.HashMap;
import java.util.Map;

import utils.Point;
import utils.soution.InstructionSolution;

public class Day17_2 extends InstructionSolution<String, Map<Point, Material>> {
	public static void main(String[] args) {
		new Day17_2().solve();
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
		var startingPoint = new Point(500, 0);
		var maximumDepth = points.keySet().stream().mapToInt(Point::getY).max().orElseThrow();

		while (dropDown(startingPoint, maximumDepth, points)) {
			// drop
		}

		return points.values().stream().filter(point -> point == Material.Water).count() + "";
	}

	private boolean dropDown(Point drop, int maximumDepth, Map<Point, Material> points) {
		while (!points.containsKey(drop.getLowerNeighbour())) {
			drop = drop.getLowerNeighbour();

			if (drop.getY() > maximumDepth) {
				return false;
			}
		}

		var hasLeftBorder = hasLeftBorder(points, drop);
		var hasRightBorder = hasRightBorder(points, drop);
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
			continueLeft = dropDown(leftDrop, maximumDepth, points);
		}
		if (!hasRightBorder) {
			while (points.containsKey(drop.getLowerNeighbour())) {
				drop = drop.getRightNeighbour();
			}
			continueRight = dropDown(drop, maximumDepth, points);
		}

		return continueLeft || continueRight;
	}

	private boolean hasLeftBorder(Map<Point, Material> points, Point drop) {
		while (points.containsKey(drop.getLowerNeighbour())) {
			if (!points.containsKey(drop.getLeftNeighbour())) {
				drop = drop.getLeftNeighbour();
			} else {
				return true;
			}
		}
		return false;
	}

	private boolean hasRightBorder(Map<Point, Material> points, Point drop) {
		while (points.containsKey(drop.getLowerNeighbour())) {
			if (!points.containsKey(drop.getRightNeighbour())) {
				drop = drop.getRightNeighbour();
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
