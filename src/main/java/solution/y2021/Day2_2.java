package solution.y2021;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.tuple.Pair;

import utils.Direction;
import utils.Point;
import utils.soution.InstructionSolution;

public class Day2_2 extends InstructionSolution<Pair<Direction, Integer>, Pair<Point, AtomicInteger>> {
	public static void main(String[] args) {
		new Day2_2().solve();
	}

	@Override
	protected Pair<Point, AtomicInteger> initializeValue() {
		return Pair.of(new Point(0, 0), new AtomicInteger());
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
	protected boolean performInstruction(Pair<Direction, Integer> directionIntegerPair,
			Pair<Point, AtomicInteger> point) {
		switch (directionIntegerPair.getLeft()) {
		case RIGHT -> {
			var position = point.getLeft();
			position.setX(position.getX() + directionIntegerPair.getRight());
			position.setY(position.getY() + directionIntegerPair.getRight() * point.getRight().get());
		}
		case UP -> point.getRight().addAndGet(-directionIntegerPair.getRight());
		case DOWN -> point.getRight().addAndGet(directionIntegerPair.getRight());
		}
		return false;
	}

	@Override
	protected String getSolution(Pair<Point, AtomicInteger> point) {
		return (long) Math.abs(point.getLeft().getX()) * Math.abs(point.getLeft().getY()) + "";
	}
}
