package solution.y2016;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.lang3.tuple.Pair;

import utils.Direction;
import utils.Point;
import utils.soution.InstructionSolution;

public class Day1_2 extends InstructionSolution<Pair<Direction, Integer>, Pair<AtomicReference<Direction>, Point>> {
	private final Set<Point> CACHE = new HashSet<>();

	public static void main(String[] args) {
		new Day1_2().solve();
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		return Arrays.asList(instructions.getFirst().split(", "));
	}

	@Override
	protected Pair<AtomicReference<Direction>, Point> initializeValue() {
		return Pair.of(new AtomicReference<>(Direction.UP), new Point(0, 0));
	}

	@Override
	protected Pair<Direction, Integer> transformInstruction(String instruction) {
		return Pair.of(mapDirection(instruction.charAt(0)), Integer.parseInt(instruction.substring(1)));
	}

	private Direction mapDirection(char ch) {
		return switch (ch) {
		case 'R' -> Direction.RIGHT;
		case 'L' -> Direction.LEFT;
		default -> throw new IllegalArgumentException("Invalid direction: " + ch);
		};
	}

	@Override
	protected boolean performInstruction(Pair<Direction, Integer> directionIntegerPair,
			Pair<AtomicReference<Direction>, Point> atomicReferencePointPair) {
		atomicReferencePointPair.getLeft()
								.set(atomicReferencePointPair.getLeft().get().turn(directionIntegerPair.getLeft()));
		for (int i = 0; i < directionIntegerPair.getRight(); i++) {
			switch (atomicReferencePointPair.getLeft().get()) {
			case RIGHT -> atomicReferencePointPair.getRight().setX(atomicReferencePointPair.getRight().getX() + 1);
			case LEFT -> atomicReferencePointPair.getRight().setX(atomicReferencePointPair.getRight().getX() - 1);
			case UP -> atomicReferencePointPair.getRight().setY(atomicReferencePointPair.getRight().getY() + 1);
			case DOWN -> atomicReferencePointPair.getRight().setY(atomicReferencePointPair.getRight().getY() - 1);
			}
			if (!CACHE.add(new Point(atomicReferencePointPair.getRight()))) {
				return true;
			}
		}

		return false;
	}

	@Override
	protected String getSolution(Pair<AtomicReference<Direction>, Point> atomicReferencePointPair) {
		return atomicReferencePointPair.getRight().computeManhattanDistance(new Point(0, 0)) + "";
	}
}
