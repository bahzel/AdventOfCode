package solution.y2015;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import utils.Direction;
import utils.Point;
import utils.StringTransformer;
import utils.soution.InstructionSolution;

public class Day3_1 extends InstructionSolution<Direction, Pair<Point, Set<Point>>> {
	public static void main(String[] args) {
		new Day3_1().solve();
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		return StringTransformer.splitString(instructions.getFirst());
	}

	@Override
	protected Pair<Point, Set<Point>> initializeValue() {
		return Pair.of(new Point(0, 0), new HashSet<>(List.of(new Point(0, 0))));
	}

	@Override
	protected Direction transformInstruction(String instruction) {
		return switch (instruction) {
			case "^" -> Direction.UP;
			case "v" -> Direction.DOWN;
			case ">" -> Direction.RIGHT;
			case "<" -> Direction.LEFT;
			default -> throw new IllegalArgumentException("Invalid instruction: " + instruction);
		};
	}

	@Override
	protected boolean performInstruction(Direction instruction, Pair<Point, Set<Point>> value) {
		switch (instruction) {
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

		value.getRight().add(new Point(value.getLeft()));
		return false;
	}

	@Override
	protected String getSolution(Pair<Point, Set<Point>> value) {
		return value.getRight().size() + "";
	}
}
