package solution.y2022;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Pair;

import utils.Direction;
import utils.Point;
import utils.soution.InstructionSolution;

public class Day9_1 extends InstructionSolution<Pair<Direction, Integer>, MutableTriple<Point, Point, Set<Point>>> {
	public static void main(String[] args) {
		new Day9_1().solve();
	}

	@Override
	protected MutableTriple<Point, Point, Set<Point>> initializeValue() {
		return MutableTriple.of(new Point(0, 0), new Point(0, 0), new HashSet<>(List.of(new Point(0, 0))));
	}

	@Override
	protected Pair<Direction, Integer> transformInstruction(String instruction) {
		var instructions = instruction.split(" ");
		var amount = Integer.parseInt(instructions[1]);
		return switch (instructions[0]) {
		case "U" -> Pair.of(Direction.UP, amount);
		case "D" -> Pair.of(Direction.DOWN, amount);
		case "L" -> Pair.of(Direction.LEFT, amount);
		case "R" -> Pair.of(Direction.RIGHT, amount);
		default -> throw new IllegalArgumentException("Invalid instruction: " + instruction);
		};
	}

	@Override
	protected boolean performInstruction(Pair<Direction, Integer> directionIntegerPair,
			MutableTriple<Point, Point, Set<Point>> points) {
		var head = points.getLeft();
		var tail = points.getMiddle();

		for (var i = 0; i < directionIntegerPair.getRight(); i++) {
			head = head.getNeighbour(directionIntegerPair.getLeft());
			if (!isNeighbour(head, tail)) {
				if (tail.getX() < head.getX()) {
					tail = tail.getRightNeighbour();
				} else if (tail.getX() > head.getX()) {
					tail = tail.getLeftNeighbour();
				}

				if (tail.getY() < head.getY()) {
					tail = tail.getLowerNeighbour();
				} else if (tail.getY() > head.getY()) {
					tail = tail.getUpperNeighbour();
				}
			}
			points.getRight().add(tail);
		}
		points.setLeft(head);
		points.setMiddle(tail);

		return false;
	}

	private boolean isNeighbour(Point point1, Point point2) {
		return Math.abs(point1.getX() - point2.getX()) < 2 && Math.abs(point1.getY() - point2.getY()) < 2;
	}

	@Override
	protected String getSolution(MutableTriple<Point, Point, Set<Point>> points) {
		return points.getRight().size() + "";
	}
}
