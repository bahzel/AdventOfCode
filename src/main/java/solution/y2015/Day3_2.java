package solution.y2015;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import utils.Direction;
import utils.soution.InstructionSolution;
import utils.Point;
import utils.StringTransformer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day3_2 extends InstructionSolution<Pair<Direction, Direction>, Triple<Point, Point, Set<Point>>> {
	public static void main(String[] args) {
		new Day3_2().solve();
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		return StringTransformer.fromString(instructions.getFirst(), 2);
	}

	@Override
	protected Triple<Point, Point, Set<Point>> initializeValue() {
		return Triple.of(new Point(0, 0), new Point(0, 0), new HashSet<>(List.of(new Point(0, 0))));
	}

	@Override
	protected Pair<Direction, Direction> transformInstruction(String instruction) {
		var instructions = instruction.toCharArray();
		return Pair.of(mapDirection(instructions[0]), mapDirection(instructions[1]));
	}

	private Direction mapDirection(char direction) {
		return switch (direction) {
			case '^' -> Direction.UP;
			case 'v' -> Direction.DOWN;
			case '>' -> Direction.RIGHT;
			case '<' -> Direction.LEFT;
			default -> throw new IllegalArgumentException("Invalid instruction: " + direction);
		};
	}

	@Override
	protected boolean performInstruction(Pair<Direction, Direction> instruction,
			Triple<Point, Point, Set<Point>> value) {
		switch (instruction.getLeft()) {
		case UP:
			value.getLeft().setY(value.getLeft().getY() + 1);
			break;
		case DOWN:
			value.getLeft().setY(value.getLeft().getY() - 1);
			break;
		case RIGHT:
			value.getLeft().setX(value.getLeft().getX() + 1);
			break;
		case LEFT:
			value.getLeft().setX(value.getLeft().getX() - 1);
			break;
		}

		switch (instruction.getRight()) {
		case UP:
			value.getMiddle().setY(value.getMiddle().getY() + 1);
			break;
		case DOWN:
			value.getMiddle().setY(value.getMiddle().getY() - 1);
			break;
		case RIGHT:
			value.getMiddle().setX(value.getMiddle().getX() + 1);
			break;
		case LEFT:
			value.getMiddle().setX(value.getMiddle().getX() - 1);
			break;
		}

		value.getRight().add(new Point(value.getLeft()));
		value.getRight().add(new Point(value.getMiddle()));
		return false;
	}

	@Override
	protected String getSolution(Triple<Point, Point, Set<Point>> value) {
		return value.getRight().size() + "";
	}
}
