package solution.y2022;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import utils.Direction;
import utils.Point;
import utils.soution.Solution;

public class Day24_1 extends Solution {
	private int minX;
	private int maxX;
	private int minY;
	private int maxY;

	public static void main(String[] args) {
		new Day24_1().solve();
	}

	@Override
	protected String doSolve() {
		var plateau = initializePlateau();
		minX = 1;
		maxX = input.getFirst().length() - 2;
		minY = 1;
		maxY = input.size() - 2;
		var currentPositions = Set.of(new Point(1, 0));
		var goal = new Point(maxX, maxY + 1);

		for (var i = 0;; i++) {
			plateau = computeNextRound(plateau);
			var occupiedPoints = plateau.stream().map(Pair::getLeft).collect(Collectors.toSet());
			var nextPositions = new HashSet<Point>();

			for (var currentPosition : currentPositions) {
				if (currentPosition.getY() < minY) {
					nextPositions.add(currentPosition.getLowerNeighbour()); // maybe too restrictive?
					break;
				}
				if (!occupiedPoints.contains(currentPosition)) {
					nextPositions.add(currentPosition);
				}
				if (currentPosition.getY() < maxY) {
					var lowerNeighbour = currentPosition.getLowerNeighbour();
					if (!occupiedPoints.contains(lowerNeighbour)) {
						nextPositions.add(lowerNeighbour);
					}
				} else if (currentPosition.getLowerNeighbour().equals(goal)) {
					return String.valueOf(i + 1);
				}
				if (currentPosition.getY() > minY) {
					var upperNeighbour = currentPosition.getUpperNeighbour();
					if (!occupiedPoints.contains(upperNeighbour)) {
						nextPositions.add(upperNeighbour);
					}
				}
				if (currentPosition.getX() > minX) {
					var leftNeighbour = currentPosition.getLeftNeighbour();
					if (!occupiedPoints.contains(leftNeighbour)) {
						nextPositions.add(leftNeighbour);
					}
				}
				if (currentPosition.getX() < maxX) {
					var rightNeighbour = currentPosition.getRightNeighbour();
					if (!occupiedPoints.contains(rightNeighbour)) {
						nextPositions.add(rightNeighbour);
					}
				}
			}
			currentPositions = nextPositions;
		}
	}

	private List<Pair<Point, Direction>> initializePlateau() {
		var points = new ArrayList<Pair<Point, Direction>>();
		for (var y = 0; y < input.size(); y++) {
			var row = input.get(y);
			for (var x = 0; x < row.length(); x++) {
				switch (row.charAt(x)) {
				case '^' -> points.add(Pair.of(new Point(x, y), Direction.UP));
				case 'v' -> points.add(Pair.of(new Point(x, y), Direction.DOWN));
				case '<' -> points.add(Pair.of(new Point(x, y), Direction.LEFT));
				case '>' -> points.add(Pair.of(new Point(x, y), Direction.RIGHT));
				}
			}
		}
		return points;
	}

	private List<Pair<Point, Direction>> computeNextRound(List<Pair<Point, Direction>> plateau) {
		var points = new ArrayList<Pair<Point, Direction>>();
		for (var blizzard : plateau) {
			var nextBlizzard = blizzard.getLeft().getNeighbour(blizzard.getRight());
			if (nextBlizzard.getX() < minX) {
				points.add(Pair.of(new Point(maxX, nextBlizzard.getY()), blizzard.getRight()));
			} else if (nextBlizzard.getX() > maxX) {
				points.add(Pair.of(new Point(minX, nextBlizzard.getY()), blizzard.getRight()));
			} else if (nextBlizzard.getY() < minY) {
				points.add(Pair.of(new Point(nextBlizzard.getX(), maxY), blizzard.getRight()));
			} else if (nextBlizzard.getY() > maxY) {
				points.add(Pair.of(new Point(nextBlizzard.getX(), minY), blizzard.getRight()));
			} else {
				points.add(Pair.of(nextBlizzard, blizzard.getRight()));
			}
		}
		return points;
	}
}