package solution.y2023;

import java.util.ArrayList;
import java.util.List;

import utils.Point;

public class Day18_2 extends Day18_1 {
	public static void main(String[] args) {
		new Day18_2().solve();
	}

	@Override
	protected List<Edge> getEdges() {
		var edges = new ArrayList<Edge>();

		var lastPoint = new Point(0, 0);
		Edge lastEdge = null;
		for (var line : input) {
			var hexString = line.split(" ")[2];
			var distance = Integer.parseInt(hexString.substring(2, hexString.length() - 2), 16);
			Point nextPoint = switch (hexString.substring(hexString.length() - 2, hexString.length() - 1)) {
			case "0" -> new Point(lastPoint.getX() + distance, lastPoint.getY());
			case "2" -> new Point(lastPoint.getX() - distance, lastPoint.getY());
			case "3" -> new Point(lastPoint.getX(), lastPoint.getY() - distance);
			case "1" -> new Point(lastPoint.getX(), lastPoint.getY() + distance);
			default -> throw new IllegalArgumentException("Invalid input");
			};
			lastEdge = new Edge(lastPoint, nextPoint, lastEdge);
			edges.add(lastEdge);
			lastPoint = nextPoint;
		}

		edges.getFirst().setFromEdge(lastEdge);
		return edges;
	}
}