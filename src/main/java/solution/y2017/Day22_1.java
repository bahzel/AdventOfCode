package solution.y2017;

import java.util.HashSet;
import java.util.Set;

import utils.Direction;
import utils.Point;
import utils.soution.Solution;

public class Day22_1 extends Solution {
	public static void main(String[] args) {
		new Day22_1().solve();
	}

	@Override
	protected String doSolve() {
		var infectedPoints = getInfectedPoints();
		var currentLocation = new Point(input.getFirst().length() / 2, input.size() / 2);
		var currentDirection = Direction.UP;
		var infectionCounter = 0;

		for (int i = 0; i < 10000; i++) {
			var wasInfected = infectedPoints.remove(currentLocation);
			if (wasInfected) {
				currentDirection = currentDirection.turn(Direction.RIGHT);
			} else {
				currentDirection = currentDirection.turn(Direction.LEFT);
				infectedPoints.add(currentLocation);
				infectionCounter++;
			}
			currentLocation = currentLocation.getNeighbour(currentDirection);
		}

		return infectionCounter + "";
	}

	private Set<Point> getInfectedPoints() {
		Set<Point> infectedPoints = new HashSet<>();

		for (int x = 0; x < input.getFirst().length(); x++) {
			for (int y = 0; y < input.size(); y++) {
				if ('#' == input.get(y).charAt(x)) {
					infectedPoints.add(new Point(x, y));
				}
			}
		}

		return infectedPoints;
	}
}
