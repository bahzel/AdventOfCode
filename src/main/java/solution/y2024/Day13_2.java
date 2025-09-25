package solution.y2024;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import utils.Point;
import utils.soution.Solution;

public class Day13_2 extends Solution {
	private static final int COST_A = 3;
	private static final int COST_B = 1;

	public static void main(String[] args) {
		new Day13_2().solve();
	}

	@Override
	protected String doSolve() {
		return getClawMachines().stream().mapToLong(this::getCostForWinning).sum() + "";
	}

	private long getCostForWinning(Triple<Point, Point, Pair<Long, Long>> clawMachine) {
		var b = ((double) clawMachine.getRight().getRight() * clawMachine.getLeft().getX()
				- clawMachine.getRight().getLeft() * clawMachine.getLeft().getY())
				/ (clawMachine.getMiddle().getY() * clawMachine.getLeft().getX()
						- clawMachine.getMiddle().getX() * clawMachine.getLeft().getY());

		var a = (clawMachine.getRight().getLeft() - b * clawMachine.getMiddle().getX()) / clawMachine.getLeft().getX();

		var aInt = Math.round(a);
		var bInt = Math.round(b);

		if (aInt >= 0 && bInt >= 0
				&& clawMachine.getLeft().getX() * aInt
						+ clawMachine.getMiddle().getX() * bInt == clawMachine.getRight().getLeft()
				&& clawMachine.getLeft().getY() * aInt
						+ clawMachine.getMiddle().getY() * bInt == clawMachine.getRight().getRight()) {
			return aInt * COST_A + bInt * COST_B;
		} else {
			return 0L;
		}
	}

	private List<Triple<Point, Point, Pair<Long, Long>>> getClawMachines() {
		var clawMachines = new ArrayList<Triple<Point, Point, Pair<Long, Long>>>();

		for (int i = 0; i < input.size(); i += 4) {
			var instructionA = input.get(i).replace(", Y", "").split("\\+");
			var buttonA = new Point(Integer.parseInt(instructionA[1]), Integer.parseInt(instructionA[2]));

			var instructionB = input.get(i + 1).replace(", Y", "").split("\\+");
			var buttonB = new Point(Integer.parseInt(instructionB[1]), Integer.parseInt(instructionB[2]));

			var instructionPrize = input.get(i + 2).replace(", Y", "").split("=");
			var prize = Pair.of(Long.parseLong(instructionPrize[1]) + 10000000000000L,
					Long.parseLong(instructionPrize[2]) + 10000000000000L);

			clawMachines.add(Triple.of(buttonA, buttonB, prize));
		}

		return clawMachines;
	}
}
