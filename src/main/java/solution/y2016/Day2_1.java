package solution.y2016;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import utils.Direction;
import utils.Point;
import utils.soution.InstructionSolution;

public class Day2_1 extends InstructionSolution<List<Direction>, Pair<StringBuilder, Point>> {
	private final int[][] KEYPAD = new int[3][3];

	public static void main(String[] args) {
		new Day2_1().solve();
	}

	@Override
	protected Pair<StringBuilder, Point> initializeValue() {
		KEYPAD[0][0] = 1;
		KEYPAD[1][0] = 2;
		KEYPAD[2][0] = 3;
		KEYPAD[0][1] = 4;
		KEYPAD[1][1] = 5;
		KEYPAD[2][1] = 6;
		KEYPAD[0][2] = 7;
		KEYPAD[1][2] = 8;
		KEYPAD[2][2] = 9;
		return Pair.of(new StringBuilder(), new Point(1, 1));
	}

	@Override
	protected List<Direction> transformInstruction(String instruction) {
		return instruction.chars().mapToObj(i -> (char) i).map(this::mapDirection).toList();
	}

	private Direction mapDirection(char c) {
		return switch (c) {
		case 'U' -> Direction.UP;
		case 'D' -> Direction.DOWN;
		case 'L' -> Direction.LEFT;
		case 'R' -> Direction.RIGHT;
		default -> throw new IllegalArgumentException();
		};
	}

	@Override
	protected boolean performInstruction(List<Direction> directions,
			Pair<StringBuilder, Point> stringBuilderPointPair) {
		var point = stringBuilderPointPair.getRight();
		for (Direction direction : directions) {
			switch (direction) {
			case UP:
				if (point.getY() > 0) {
					point.setY(point.getY() - 1);
				}
				break;
			case DOWN:
				if (point.getY() < 2) {
					point.setY(point.getY() + 1);
				}
				break;
			case LEFT:
				if (point.getX() > 0) {
					point.setX(point.getX() - 1);
				}
				break;
			case RIGHT:
				if (point.getX() < 2) {
					point.setX(point.getX() + 1);
				}
			}
		}

		stringBuilderPointPair.getLeft().append(KEYPAD[point.getX()][point.getY()]);
		return false;
	}

	@Override
	protected String getSolution(Pair<StringBuilder, Point> stringBuilderPointPair) {
		return stringBuilderPointPair.getLeft().toString();
	}
}
