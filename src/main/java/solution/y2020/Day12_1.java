package solution.y2020;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import utils.Direction;
import utils.Point;
import utils.soution.InstructionSolution;

public class Day12_1 extends InstructionSolution<Pair<Character, Integer>, MutablePair<Point, Direction>> {
	public static void main(String[] args) {
		new Day12_1().solve();
	}

	@Override
	protected MutablePair<Point, Direction> initializeValue() {
		return MutablePair.of(new Point(0, 0), Direction.RIGHT);
	}

	@Override
	protected Pair<Character, Integer> transformInstruction(String instruction) {
		return Pair.of(instruction.charAt(0), Integer.parseInt(instruction.substring(1)));
	}

	@Override
	protected boolean performInstruction(Pair<Character, Integer> characterIntegerPair,
			MutablePair<Point, Direction> ship) {
		switch (characterIntegerPair.getLeft()) {
		case 'N':
			ship.getLeft().setY(ship.getLeft().getY() - characterIntegerPair.getRight());
			break;
		case 'S':
			ship.getLeft().setY(ship.getLeft().getY() + characterIntegerPair.getRight());
			break;
		case 'E':
			ship.getLeft().setX(ship.getLeft().getX() + characterIntegerPair.getRight());
			break;
		case 'W':
			ship.getLeft().setX(ship.getLeft().getX() - characterIntegerPair.getRight());
			break;
		case 'L':
			for (var i = 0; i < characterIntegerPair.getRight(); i += 90) {
				ship.setRight(ship.getRight().turn(Direction.LEFT));
			}
			break;
		case 'R':
			for (var i = 0; i < characterIntegerPair.getRight(); i += 90) {
				ship.setRight(ship.getRight().turn(Direction.RIGHT));
			}
			break;
		case 'F':
			switch (ship.getRight()) {
			case LEFT:
				ship.getLeft().setX(ship.getLeft().getX() - characterIntegerPair.getRight());
				break;
			case RIGHT:
				ship.getLeft().setX(ship.getLeft().getX() + characterIntegerPair.getRight());
				break;
			case UP:
				ship.getLeft().setY(ship.getLeft().getY() - characterIntegerPair.getRight());
				break;
			case DOWN:
				ship.getLeft().setY(ship.getLeft().getY() + characterIntegerPair.getRight());
				break;
			}
		}

		return false;
	}

	@Override
	protected String getSolution(MutablePair<Point, Direction> ship) {
		return ship.getLeft().computeManhattanDistance(new Point(0, 0)) + "";
	}
}
