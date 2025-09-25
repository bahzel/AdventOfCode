package utils;

public enum Direction {
	UP, DOWN, LEFT, RIGHT;

	public Direction turn(Direction turnTo) {
		if (turnTo == UP) {
			return this;
		} else if (turnTo == DOWN) {
			return switch (this) {
			case UP -> DOWN;
			case DOWN -> UP;
			case LEFT -> RIGHT;
			case RIGHT -> LEFT;
			};
		} else if (turnTo == RIGHT) {
			return switch (this) {
			case UP -> RIGHT;
			case DOWN -> LEFT;
			case LEFT -> UP;
			case RIGHT -> DOWN;
			};
		} else if (turnTo == LEFT) {
			return switch (this) {
			case UP -> LEFT;
			case DOWN -> RIGHT;
			case LEFT -> DOWN;
			case RIGHT -> UP;
			};
		} else {
			throw new IllegalArgumentException("Invalid direction: " + turnTo);
		}
	}
}
