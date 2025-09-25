package solution.y2015;

import java.util.Arrays;

import org.apache.commons.lang3.tuple.Triple;

import utils.Point;
import utils.soution.InstructionSolution;

public class Day6_2 extends InstructionSolution<Triple<Action, Point, Point>, int[][]> {
	public static void main(String[] args) {
		new Day6_2().solve();
	}

	@Override
	protected int[][] initializeValue() {
		return new int[1000][1000];
	}

	@Override
	protected Triple<Action, Point, Point> transformInstruction(String instruction) {
		var instructions = instruction.replace("turn ", "").split(" ");
		Action action = switch (instructions[0]) {
		case "on" -> Action.ON;
		case "off" -> Action.OFF;
		case "toggle" -> Action.TOGGLE;
		default -> throw new IllegalArgumentException("Invalid instruction: " + instruction);
		};

		var point1 = instructions[1].split(",");
		var point2 = instructions[3].split(",");

		return Triple.of(action, new Point(Integer.parseInt(point1[0]), Integer.parseInt(point1[1])),
				new Point(Integer.parseInt(point2[0]), Integer.parseInt(point2[1])));
	}

	@Override
	protected boolean performInstruction(Triple<Action, Point, Point> instruction, int[][] value) {
		if (instruction.getLeft() == Action.ON) {
			switchOn(instruction.getMiddle(), instruction.getRight(), value);
		} else if (instruction.getLeft() == Action.OFF) {
			switchOff(instruction.getMiddle(), instruction.getRight(), value);
		} else if (instruction.getLeft() == Action.TOGGLE) {
			toggle(instruction.getMiddle(), instruction.getRight(), value);
		}
		return false;
	}

	private void switchOn(Point point1, Point point2, int[][] value) {
		for (int i = point1.getX(); i <= point2.getX(); i++) {
			for (int j = point1.getY(); j <= point2.getY(); j++) {
				value[i][j] += 1;
			}
		}
	}

	private void switchOff(Point point1, Point point2, int[][] value) {
		for (int i = point1.getX(); i <= point2.getX(); i++) {
			for (int j = point1.getY(); j <= point2.getY(); j++) {
				if (value[i][j] > 0) {
					value[i][j] -= 1;
				}
			}
		}
	}

	private void toggle(Point point1, Point point2, int[][] value) {
		for (int i = point1.getX(); i <= point2.getX(); i++) {
			for (int j = point1.getY(); j <= point2.getY(); j++) {
				value[i][j] += 2;
			}
		}
	}

	@Override
	protected String getSolution(int[][] value) {
		return Arrays.stream(value).flatMapToInt(Arrays::stream).sum() + "";
	}
}
