package solution.y2017;

import utils.Direction;
import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day19_2 extends GridSolution<Character> {
	public static void main(String[] args) {
		new Day19_2().solve();
	}

	@Override
	protected GridElement<Character> transformCell(char ch, int x, int y) {
		return new GridElement<>(ch, x, y);
	}

	@Override
	protected String computeSolution() {
		var startingPoint = stream().filter(point -> point.getCoordinates().getY() == 0 && point.getValue() == '|')
									.findAny()
									.orElseThrow();
		return walkPath(startingPoint, Direction.DOWN) + "";
	}

	private long walkPath(GridElement<Character> currentPoint, Direction incomingDirection) {
		var solution = 0L;

		while (currentPoint.getValue() != ' ') {
			solution++;
			if (currentPoint.getValue() == '+') {
				switch (incomingDirection) {
				case UP:
				case DOWN:
					if (currentPoint.getRightNeighbour().getValue() == '-') {
						currentPoint = currentPoint.getRightNeighbour();
						incomingDirection = Direction.RIGHT;
					} else {
						currentPoint = currentPoint.getLeftNeighbour();
						incomingDirection = Direction.LEFT;
					}
					break;
				case LEFT:
				case RIGHT:
					if (currentPoint.getUpperNeighbour().getValue() == '|') {
						currentPoint = currentPoint.getUpperNeighbour();
						incomingDirection = Direction.UP;
					} else {
						currentPoint = currentPoint.getLowerNeighbour();
						incomingDirection = Direction.DOWN;
					}
					break;
				}
			} else {
				switch (incomingDirection) {
				case UP:
					currentPoint = currentPoint.getUpperNeighbour();
					break;
				case DOWN:
					currentPoint = currentPoint.getLowerNeighbour();
					break;
				case LEFT:
					currentPoint = currentPoint.getLeftNeighbour();
					break;
				case RIGHT:
					currentPoint = currentPoint.getRightNeighbour();
				}
			}
		}

		return solution;
	}
}
