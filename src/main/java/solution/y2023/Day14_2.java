package solution.y2023;

import java.util.ArrayList;

import utils.Direction;
import utils.ListUtils;

public class Day14_2 extends Day14_1 {
	private static final int LOOP_SIZE = 1000000000;

	public static void main(String[] args) {
		new Day14_2().solve();
	}

	@Override
	protected String computeSolution() {
		var loads = new ArrayList<Integer>();

		for (var i = 0; i < LOOP_SIZE; i++) {
			tiltNorth();
			tiltWest();
			tiltSouth();
			tiltEast();
			loads.add(countLoad());

			var cycleSize = ListUtils.countCycleSize(loads);
			if (cycleSize > 0) {
				loads.clear();
				i += ((LOOP_SIZE - i) / cycleSize) * cycleSize;
			}
		}

		return countLoad() + "";
	}

	protected void tiltSouth() {
		for (var i = grid[0].length - 1; i >= 0; i--) {
			for (utils.soution.GridElement<Rock>[] gridElements : grid) {
				if (gridElements[i].getValue() == Rock.ROUND) {
					move(gridElements[i], Direction.DOWN);
				}
			}
		}
	}

	protected void tiltWest() {
		for (utils.soution.GridElement<Rock>[] gridElements : grid) {
			for (var j = 0; j < grid[0].length; j++) {
				if (gridElements[j].getValue() == Rock.ROUND) {
					move(gridElements[j], Direction.LEFT);
				}
			}
		}
	}

	protected void tiltEast() {
		for (var i = grid.length - 1; i >= 0; i--) {
			for (var j = 0; j < grid[0].length; j++) {
				if (grid[i][j].getValue() == Rock.ROUND) {
					move(grid[i][j], Direction.RIGHT);
				}
			}
		}
	}

	@Override
	protected String printValue(Rock value) {
		return switch (value) {
		case ROUND -> "O";
		case CUBE -> "#";
		case EMPTY -> ".";
		};
	}
}