package solution.y2020;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import utils.Point;
import utils.soution.InstructionSolution;

public class Day12_2 extends InstructionSolution<Pair<Character, Integer>, MutablePair<Point, Point>> {
	public static void main(String[] args) {
		new Day12_2().solve();
	}

	@Override
	protected MutablePair<Point, Point> initializeValue() {
		return MutablePair.of(new Point(0, 0), new Point(10, -1));
	}

	@Override
	protected Pair<Character, Integer> transformInstruction(String instruction) {
		return Pair.of(instruction.charAt(0), Integer.parseInt(instruction.substring(1)));
	}

	@Override
	protected boolean performInstruction(Pair<Character, Integer> characterIntegerPair,
			MutablePair<Point, Point> ship) {
		switch (characterIntegerPair.getLeft()) {
		case 'N':
			ship.getRight().setY(ship.getRight().getY() - characterIntegerPair.getRight());
			break;
		case 'S':
			ship.getRight().setY(ship.getRight().getY() + characterIntegerPair.getRight());
			break;
		case 'E':
			ship.getRight().setX(ship.getRight().getX() + characterIntegerPair.getRight());
			break;
		case 'W':
			ship.getRight().setX(ship.getRight().getX() - characterIntegerPair.getRight());
			break;
		case 'L':
			for (var i = 0; i < characterIntegerPair.getRight(); i += 90) {
				var xL = ship.getRight().getY();
				var yL = -ship.getRight().getX();
				ship.getRight().setX(xL);
				ship.getRight().setY(yL);
			}
			break;
		case 'R':
			for (var i = 0; i < characterIntegerPair.getRight(); i += 90) {
				var xR = -ship.getRight().getY();
				var yR = ship.getRight().getX();
				ship.getRight().setX(xR);
				ship.getRight().setY(yR);
			}
			break;
		case 'F':
			ship.getLeft().setX(ship.getLeft().getX() + ship.getRight().getX() * characterIntegerPair.getRight());
			ship.getLeft().setY(ship.getLeft().getY() + ship.getRight().getY() * characterIntegerPair.getRight());
		}

		return false;
	}

	@Override
	protected String getSolution(MutablePair<Point, Point> ship) {
		return ship.getLeft().computeManhattanDistance(new Point(0, 0)) + "";
	}
}
