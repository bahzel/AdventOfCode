package solution.y2015;

import org.apache.commons.lang3.tuple.Triple;
import utils.InstructionSolution;
import utils.Point;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Day6_1 extends InstructionSolution<Triple<Action, Point, Point>, boolean[][]> {
	public static void main(String[] args) {
		new Day6_1().solve();
	}

	@Override
	protected boolean[][] initializeValue() {
		return new boolean[1000][1000];
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
	protected boolean performInstruction(Triple<Action, Point, Point> instruction, boolean[][] value) {
		if (instruction.getLeft() == Action.ON) {
			switchOn(instruction.getMiddle(), instruction.getRight(), value);
		} else if (instruction.getLeft() == Action.OFF) {
			switchOff(instruction.getMiddle(), instruction.getRight(), value);
		} else if (instruction.getLeft() == Action.TOGGLE) {
			toggle(instruction.getMiddle(), instruction.getRight(), value);
		}
		return false;
	}

	private void switchOn(Point point1, Point point2, boolean[][] value) {
		for (int i = point1.getX(); i <= point2.getX(); i++) {
			for (int j = point1.getY(); j <= point2.getY(); j++) {
				value[i][j] = true;
			}
		}
	}

	private void switchOff(Point point1, Point point2, boolean[][] value) {
		for (int i = point1.getX(); i <= point2.getX(); i++) {
			for (int j = point1.getY(); j <= point2.getY(); j++) {
				value[i][j] = false;
			}
		}
	}

	private void toggle(Point point1, Point point2, boolean[][] value) {
		for (int i = point1.getX(); i <= point2.getX(); i++) {
			for (int j = point1.getY(); j <= point2.getY(); j++) {
				value[i][j] = !value[i][j];
			}
		}
	}

	@Override
	protected String getSolution(boolean[][] value) {
		return Arrays.stream(value)
					 .flatMap(lights -> IntStream.range(0, lights.length).mapToObj(light -> lights[light]))
					 .filter(light -> light)
					 .count() + "";
	}
}

enum Action {
	ON, OFF, TOGGLE
}
