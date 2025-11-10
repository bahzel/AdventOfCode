package solution.y2023;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.MutablePair;

import lombok.Getter;
import lombok.Setter;
import utils.Point;
import utils.soution.Solution;

public class Day18_1 extends Solution {
	public static void main(String[] args) {
		new Day18_1().solve();
	}

	@Override
	protected String doSolve() {
		var edges = getEdges();
		var minX = edges.stream().mapToInt(edge -> edge.getLeft().getX()).min().orElseThrow();
		var maxX = edges.stream().mapToInt(edge -> edge.getRight().getX()).max().orElseThrow();
		var minY = edges.stream().mapToInt(edge -> edge.getUpper().getY()).min().orElseThrow();
		var maxY = edges.stream().mapToInt(edge -> edge.getLower().getY()).max().orElseThrow();
		var rectangles = new ArrayList<>(List.of(MutablePair.of(new Point(minX, minY), new Point(maxX, maxY))));

		edges.stream().filter(Edge::isHorizontal).forEach(edge -> {
			var index = edge.getFrom().getY();
			for (var i = 0; i < rectangles.size(); i++) {
				if (rectangles.get(i).getLeft().getY() > index || rectangles.get(i).getRight().getY() < index) {
					continue;
				}

				if (rectangles.get(i).getLeft().getY() < index) {
					rectangles.add(MutablePair.of(rectangles.get(i).getLeft(),
							new Point(rectangles.get(i).getRight().getX(), index - 1)));
					rectangles.get(i).setLeft(new Point(rectangles.get(i).getLeft().getX(), index));
				}
				if (rectangles.get(i).getRight().getY() > index) {
					rectangles.add(MutablePair.of(new Point(rectangles.get(i).getLeft().getX(), index + 1),
							rectangles.get(i).getRight()));
					rectangles.get(i).setRight(new Point(rectangles.get(i).getRight().getX(), index));
				}
			}
		});

		edges.stream().filter(Edge::isVertical).forEach(edge -> {
			var index = edge.getFrom().getX();
			for (var i = 0; i < rectangles.size(); i++) {
				if (rectangles.get(i).getLeft().getX() > index || rectangles.get(i).getRight().getX() < index) {
					continue;
				}
				if (rectangles.get(i).getLeft().getY() < edge.getUpper().getY()
						&& rectangles.get(i).getRight().getY() < edge.getUpper().getY()
						|| rectangles.get(i).getLeft().getY() > edge.getLower().getY()
								&& rectangles.get(i).getRight().getY() > edge.getLower().getY()) {
					continue;
				}

				if (rectangles.get(i).getLeft().getX() < index) {
					rectangles.add(MutablePair.of(rectangles.get(i).getLeft(),
							new Point(index - 1, rectangles.get(i).getRight().getY())));
					rectangles.get(i).setLeft(new Point(index, rectangles.get(i).getLeft().getY()));
				}
				if (rectangles.get(i).getRight().getX() > index) {
					rectangles.add(MutablePair.of(new Point(index + 1, rectangles.get(i).getLeft().getY()),
							rectangles.get(i).getRight()));
					rectangles.get(i).setRight(new Point(index, rectangles.get(i).getRight().getY()));
				}
			}
		});

		return rectangles.stream().mapToLong(rectangle -> {
			if (edges.stream().anyMatch(edge -> {
				var relationship = edge.isRectangleIncluded(rectangle.getLeft());
				if (relationship == Relationship.INCLUDED || relationship == Relationship.RIGHT) {
					return true;
				}
				relationship = edge.isRectangleIncluded(rectangle.getRight());
				return relationship == Relationship.INCLUDED || relationship == Relationship.RIGHT;
			})) {

				return (rectangle.getRight().getX() - rectangle.getLeft().getX() + 1L)
						* (rectangle.getRight().getY() - rectangle.getLeft().getY() + 1L);
			}
			return 0L;
		}).sum() + "";
	}

	private List<Edge> getEdges() {
		var edges = new ArrayList<Edge>();

		var lastPoint = new Point(0, 0);
		Edge lastEdge = null;
		for (var line : input) {
			var inputs = line.split(" ");
			var distance = Integer.parseInt(inputs[1]);
			Point nextPoint = switch (inputs[0]) {
			case "R" -> new Point(lastPoint.getX() + distance, lastPoint.getY());
			case "L" -> new Point(lastPoint.getX() - distance, lastPoint.getY());
			case "U" -> new Point(lastPoint.getX(), lastPoint.getY() - distance);
			case "D" -> new Point(lastPoint.getX(), lastPoint.getY() + distance);
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

@Getter
class Edge {
	private final Point from;
	private final Point to;
	@Setter
	private Edge fromEdge;
	private Edge toEdge;
	private final boolean sorted;

	public Edge(Point from, Point to, Edge fromEdge) {
		this.from = from;
		this.to = to;
		if (fromEdge != null) {
			this.fromEdge = fromEdge;
			fromEdge.toEdge = this;
		}
		sorted = from.getX() < to.getX() || from.getY() < to.getY();
	}

	public boolean isHorizontal() {
		return from.getY() == to.getY();
	}

	public boolean isVertical() {
		return from.getX() == to.getX();
	}

	public Point getUpper() {
		if (from.getY() <= to.getY())
			return from;
		else
			return to;
	}

	public Point getLower() {
		if (from.getY() >= to.getY())
			return from;
		else
			return to;
	}

	public Point getLeft() {
		if (from.getX() <= to.getX())
			return from;
		else
			return to;
	}

	public Point getRight() {
		if (from.getX() >= to.getX())
			return from;
		else
			return to;
	}

	public Relationship isRectangleIncluded(Point point) {
		if (isHorizontal()) {
			if (point.getX() >= getLeft().getX() && point.getX() <= getRight().getX()) {
				if (point.getY() == from.getY()) {
					return Relationship.INCLUDED;
				} else if (point.getY() == to.getY() + 1) {
					if (sorted) {
						return Relationship.RIGHT;
					} else {
						return Relationship.LEFT;
					}
				} else if (point.getY() == to.getY() - 1) {
					if (sorted) {
						return Relationship.LEFT;
					} else {
						return Relationship.RIGHT;
					}
				}
			}
		} else if (point.getY() >= getUpper().getY() && point.getY() <= getLower().getY()) {
			if (point.getX() == from.getX()) {
				return Relationship.INCLUDED;
			} else if (point.getX() == to.getX() + 1) {
				if (sorted) {
					return Relationship.LEFT;
				} else {
					return Relationship.RIGHT;
				}
			} else if (point.getX() == to.getX() - 1) {
				if (sorted) {
					return Relationship.RIGHT;
				} else {
					return Relationship.LEFT;
				}
			}
		}
		return Relationship.OUTSIDE;
	}
}

enum Relationship {
	INCLUDED, LEFT, RIGHT, OUTSIDE
}