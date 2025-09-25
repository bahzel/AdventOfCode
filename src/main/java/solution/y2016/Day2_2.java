package solution.y2016;

import org.apache.commons.lang3.tuple.Pair;
import utils.Direction;
import utils.InstructionSolution;
import utils.Point;

import java.util.List;

public class Day2_2 extends InstructionSolution<List<Direction>, Pair<StringBuilder, Point>> {
	private final char[][] KEYPAD = new char[5][5];

	public static void main(String[] args) {
		new Day2_2().solve();
	}

	@Override
	protected Pair<StringBuilder, Point> initializeValue() {
		KEYPAD[2][0] = '1';
		KEYPAD[1][1] = '2';
		KEYPAD[2][1] = '3';
		KEYPAD[3][1] = '4';
		KEYPAD[0][2] = '5';
		KEYPAD[1][2] = '6';
		KEYPAD[2][2] = '7';
		KEYPAD[3][2] = '8';
		KEYPAD[4][2] = '9';
		KEYPAD[1][3] = 'A';
		KEYPAD[2][3] = 'B';
		KEYPAD[3][3] = 'C';
		KEYPAD[2][4] = 'D';
		return Pair.of(new StringBuilder(), new Point(0, 2));
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
				if (point.getY() > 0 && point.getX() == 2 || point.getY() > 1 && (point.getX() == 1
						|| point.getX() == 3) || point.getY() > 2) {
					point.setY(point.getY() - 1);
				}
				break;
			case DOWN:
				if (point.getY() < 4 && point.getX() == 2 || point.getY() < 3 && (point.getX() == 1
						|| point.getX() == 3) || point.getY() < 2) {
					point.setY(point.getY() + 1);
				}
				break;
			case LEFT:
				if (point.getX() > 0 && point.getY() == 2 || point.getX() > 1 && (point.getY() == 1
						|| point.getY() == 3) || point.getX() > 2) {
					point.setX(point.getX() - 1);
				}
				break;
			case RIGHT:
				if (point.getX() < 4 && point.getY() == 2 || point.getX() < 3 && (point.getY() == 1
						|| point.getY() == 3) || point.getX() < 2) {
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
