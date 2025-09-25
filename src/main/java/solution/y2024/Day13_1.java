package solution.y2024;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Triple;

import utils.Point;
import utils.soution.Solution;

public class Day13_1 extends Solution {
	private static final int COST_A = 3;
	private static final int COST_B = 1;

	public static void main(String[] args) {
		new Day13_1().solve();
	}

	@Override
	protected String doSolve() {
		return getClawMachines().stream().mapToLong(this::getCostForWinning).sum() + "";
	}

	private int getCostForWinning(Triple<Point, Point, Point> clawMachine) {
		if (new Point(0, 0).computeManhattanDistance(
				clawMachine.getMiddle()) <= new Point(0, 0).computeManhattanDistance(clawMachine.getLeft()) / COST_A) {
			for (int a = 100; a >= 0; a--) {
				var b = (clawMachine.getRight().getX() - clawMachine.getLeft().getX() * a)
						/ clawMachine.getMiddle().getX();
				if (b > 100) {
					return 0;
				}

				if (b > 0
						&& clawMachine.getMiddle().getX() * b
								+ clawMachine.getLeft().getX() * a == clawMachine.getRight().getX()
						&& clawMachine.getMiddle().getY() * b
								+ clawMachine.getLeft().getY() * a == clawMachine.getRight().getY()) {
					return a * COST_A + b * COST_B;
				}
			}
		} else {

			for (int b = 100; b >= 0; b--) {
				var a = (clawMachine.getRight().getX() - clawMachine.getMiddle().getX() * b)
						/ clawMachine.getLeft().getX();
				if (a > 100) {
					return 0;
				}

				if (a > 0
						&& clawMachine.getMiddle().getX() * b
								+ clawMachine.getLeft().getX() * a == clawMachine.getRight().getX()
						&& clawMachine.getMiddle().getY() * b
								+ clawMachine.getLeft().getY() * a == clawMachine.getRight().getY()) {
					return a * COST_A + b * COST_B;
				}
			}
		}

		return 0;
	}

	private List<Triple<Point, Point, Point>> getClawMachines() {
		var clawMachines = new ArrayList<Triple<Point, Point, Point>>();

		for (int i = 0; i < input.size(); i += 4) {
			var instructionA = input.get(i).replace(", Y", "").split("\\+");
			var buttonA = new Point(Integer.parseInt(instructionA[1]), Integer.parseInt(instructionA[2]));

			var instructionB = input.get(i + 1).replace(", Y", "").split("\\+");
			var buttonB = new Point(Integer.parseInt(instructionB[1]), Integer.parseInt(instructionB[2]));

			var instructionPrize = input.get(i + 2).replace(", Y", "").split("=");
			var prize = new Point(Integer.parseInt(instructionPrize[1]), Integer.parseInt(instructionPrize[2]));

			clawMachines.add(Triple.of(buttonA, buttonB, prize));
		}

		return clawMachines;
	}
}
