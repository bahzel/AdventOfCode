package solution.y2022;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import utils.Direction;
import utils.Point;
import utils.soution.InstructionSolution;

public class Day9_2 extends InstructionSolution<Pair<Direction, Integer>, Pair<List<Point>, Set<Point>>> {
	public static void main(String[] args) {
		new Day9_2().solve();
	}

	@Override
	protected Pair<List<Point>, Set<Point>> initializeValue() {
		var rope = new ArrayList<Point>();
		for (var i = 0; i < 10; i++) {
			rope.add(new Point(0, 0));
		}
		return Pair.of(rope, new HashSet<>());
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
			Pair<List<Point>, Set<Point>> points) {
		var rope = points.getLeft();

		for (var i = 0; i < directionIntegerPair.getRight(); i++) {
			rope.set(0, rope.getFirst().getNeighbour(directionIntegerPair.getLeft()));
			for (var j = 0; j < 9; j++) {
				if (!isNeighbour(rope.get(j), rope.get(j + 1))) {
					if (rope.get(j + 1).getX() < rope.get(j).getX()) {
						rope.set(j + 1, rope.get(j + 1).getRightNeighbour());
					} else if (rope.get(j + 1).getX() > rope.get(j).getX()) {
						rope.set(j + 1, rope.get(j + 1).getLeftNeighbour());
					}

					if (rope.get(j + 1).getY() < rope.get(j).getY()) {
						rope.set(j + 1, rope.get(j + 1).getLowerNeighbour());
					} else if (rope.get(j + 1).getY() > rope.get(j).getY()) {
						rope.set(j + 1, rope.get(j + 1).getUpperNeighbour());
					}
				}
			}
			points.getRight().add(rope.getLast());
		}

		return false;
	}

	private boolean isNeighbour(Point point1, Point point2) {
		return Math.abs(point1.getX() - point2.getX()) < 2 && Math.abs(point1.getY() - point2.getY()) < 2;
	}

	@Override
	protected String getSolution(Pair<List<Point>, Set<Point>> points) {
		return points.getRight().size() + "";
	}
}
