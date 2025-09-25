package solution.y2018;

import utils.Point;
import utils.soution.Solution;

public class Day11_1 extends Solution {
	public static void main(String[] args) {
		new Day11_1().solve();
	}

	@Override
	protected String doSolve() {
		var serialNumber = Integer.parseInt(input.getFirst());
		var maximumPowerLevel = Integer.MIN_VALUE;
		Point maximumCoordinate = null;

		for (int x = 1; x <= 298; x++) {
			for (int y = 1; y <= 298; y++) {
				var powerLevel = computePowerLevelOfGrid(x, y, serialNumber);
				if (powerLevel > maximumPowerLevel) {
					maximumPowerLevel = powerLevel;
					maximumCoordinate = new Point(x, y);
				}
			}
		}

		return maximumCoordinate.getX() + "," + maximumCoordinate.getY();
	}

	private int computePowerLevelOfGrid(int x, int y, int serialNumber) {
		var powerLevel = 0;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				powerLevel += computePowerLevelOfCell(x + i, y + j, serialNumber);
			}
		}

		return powerLevel;
	}

	private int computePowerLevelOfCell(int x, int y, int serialNumber) {
		var rackId = x + 10;
		return (((rackId * y + serialNumber) * rackId / 100) % 10) - 5;
	}
}
