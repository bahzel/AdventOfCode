package solution.y2018;

import org.apache.commons.lang3.tuple.Triple;
import utils.soution.Solution;

public class Day11_2 extends Solution {
	private final int GRID_SIZE = 300;

	public static void main(String[] args) {
		new Day11_2().solve();
	}

	@Override
	protected String doSolve() {
		var serialNumber = Integer.parseInt(input.getFirst());
		var maximumPowerLevel = Long.MIN_VALUE;
		Triple<Integer, Integer, Integer> maximumCoordinate = null;

		for (int x = 1; x <= GRID_SIZE; x++) {
			for (int y = 1; y <= GRID_SIZE; y++) {
				for (int size = 1; size <= GRID_SIZE - x + 1 && size <= GRID_SIZE - y + 1; size++) {
					var powerLevel = computePowerLevelOfGrid(x, y, size, serialNumber);
					if (powerLevel > maximumPowerLevel) {
						maximumPowerLevel = powerLevel;
						maximumCoordinate = Triple.of(x, y, size);
					}
				}
			}
		}

		return maximumCoordinate.toString().replace("(", "").replace(")", "");
	}

	private long computePowerLevelOfGrid(int x, int y, int size, int serialNumber) {
		var powerLevel = 0L;

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				powerLevel += computePowerLevelOfCell(x + i, y + j, serialNumber);
			}
		}

		return powerLevel;
	}

	private long computePowerLevelOfCell(int x, int y, int serialNumber) {
		var rackId = x + 10L;
		return (((rackId * y + serialNumber) * rackId / 100) % 10) - 5;
	}
}
