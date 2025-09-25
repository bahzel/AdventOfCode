package solution.y2021;

import utils.soution.Solution;

public class Day17_2 extends Solution {
	private final int MAX_Y = 200;

	public static void main(String[] args) {
		new Day17_2().solve();
	}

	@Override
	protected String doSolve() {
		var instructions = input.getFirst().replace("..", "=").replace(", ", "=").split("=");
		var goalXMin = Integer.parseInt(instructions[1]);
		var goalXMax = Integer.parseInt(instructions[2]);
		var goalYMin = Integer.parseInt(instructions[4]);
		var goalYMax = Integer.parseInt(instructions[5]);
		var minX = getMinX(goalXMin);
		var maxX = goalXMax;
		var minY = goalYMin;
		var count = 0;

		for (var y = MAX_Y; y >= minY; y--) {
			for (var x = minX; x <= maxX; x++) {
				if (reachesGoal(goalXMin, goalXMax, goalYMin, goalYMax, x, y)) {
					count++;
				}
			}
		}
		return count + "";
	}

	private int getMinX(int goalXMin) {
		var minX = 0;
		var addition = 0;
		while (minX < goalXMin) {
			addition++;
			minX += addition;
		}
		return addition;
	}

	private boolean reachesGoal(int goalXMin, int goalXMax, int goalYMin, int goalYMax, int xVelocity, int yVelocity) {
		var x = 0;
		var y = 0;
		while (x <= goalXMax && y >= goalYMin) {
			if (x >= goalXMin && y <= goalYMax) {
				return true;
			}

			x += xVelocity;
			y += yVelocity;
			xVelocity += Integer.compare(0, xVelocity);
			yVelocity--;
		}
		return false;
	}
}
