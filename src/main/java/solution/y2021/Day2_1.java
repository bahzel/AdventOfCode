package solution.y2021;

import org.apache.commons.lang3.tuple.Pair;

import utils.Direction;
import utils.Point;
import utils.soution.InstructionSolution;

public class Day2_1 extends InstructionSolution<Pair<Direction, Integer>, Point> {
	public static void main(String[] args) {
		new Day2_1().solve();
	}

	@Override
	protected Point initializeValue() {
		return new Point(0, 0);
	}

	@Override
	protected Pair<Direction, Integer> transformInstruction(String instruction) {
		var instructions = instruction.split(" ");
		return switch (instructions[0]) {
		case "forward" -> Pair.of(Direction.RIGHT, Integer.parseInt(instructions[1]));
		case "up" -> Pair.of(Direction.UP, Integer.parseInt(instructions[1]));
		case "down" -> Pair.of(Direction.DOWN, Integer.parseInt(instructions[1]));
		default -> throw new IllegalArgumentException("Invalid instruction: " + instruction);
		};
	}

	@Override
	protected boolean performInstruction(Pair<Direction, Integer> directionIntegerPair, Point point) {
		switch (directionIntegerPair.getLeft()) {
		case RIGHT -> point.setX(point.getX() + directionIntegerPair.getRight());
		case UP -> point.setY(point.getY() - directionIntegerPair.getRight());
		case DOWN -> point.setY(point.getY() + directionIntegerPair.getRight());
		}
		return false;
	}

	@Override
	protected String getSolution(Point point) {
		return (long) Math.abs(point.getX()) * Math.abs(point.getY()) + "";
	}
}
