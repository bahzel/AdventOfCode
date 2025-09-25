package solution.y2017;

import java.util.HashMap;
import java.util.Map;

import utils.Direction;
import utils.Point;
import utils.soution.Solution;

public class Day22_2 extends Solution {
	public static void main(String[] args) {
		new Day22_2().solve();
	}

	@Override
	protected String doSolve() {
		var infectedPoints = getInfectedPoints();
		var currentLocation = new Point(input.getFirst().length() / 2, input.size() / 2);
		var currentDirection = Direction.UP;
		var infectionCounter = 0;

		for (int i = 0; i < 10000000; i++) {
			var currentStatus = infectedPoints.getOrDefault(currentLocation, InfectionStatus.Clean);
			switch (currentStatus) {
			case Clean:
				currentDirection = currentDirection.turn(Direction.LEFT);
				infectedPoints.put(currentLocation, InfectionStatus.Weakened);
				break;
			case Weakened:
				infectedPoints.put(currentLocation, InfectionStatus.Infected);
				infectionCounter++;
				break;
			case Infected:
				currentDirection = currentDirection.turn(Direction.RIGHT);
				infectedPoints.put(currentLocation, InfectionStatus.Flagged);
				break;
			case Flagged:
				currentDirection = currentDirection.turn(Direction.DOWN);
				infectedPoints.remove(currentLocation);
			}
			currentLocation = currentLocation.getNeighbour(currentDirection);
		}

		return infectionCounter + "";
	}

	private Map<Point, InfectionStatus> getInfectedPoints() {
		Map<Point, InfectionStatus> infectedPoints = new HashMap<>();

		for (int x = 0; x < input.getFirst().length(); x++) {
			for (int y = 0; y < input.size(); y++) {
				if ('#' == input.get(y).charAt(x)) {
					infectedPoints.put(new Point(x, y), InfectionStatus.Infected);
				}
			}
		}

		return infectedPoints;
	}
}

enum InfectionStatus {
	Clean, Weakened, Infected, Flagged
}
