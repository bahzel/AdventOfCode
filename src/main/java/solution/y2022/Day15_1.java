package solution.y2022;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import utils.Point;
import utils.soution.InstructionSolution;

public class Day15_1 extends InstructionSolution<Pair<Point, Point>, BeaconRowWithWhitelist> {
	private final int ROW_TO_CHECK = 2000000;

	public static void main(String[] args) {
		new Day15_1().solve();
	}

	@Override
	protected BeaconRowWithWhitelist initializeValue() {
		return new BeaconRowWithWhitelist();
	}

	@Override
	protected Pair<Point, Point> transformInstruction(String instruction) {
		var instructions = instruction.replace(", y=", "=").replace(": closest beacon is at x=", "=").split("=");
		return Pair.of(new Point(Integer.parseInt(instructions[1]), Integer.parseInt(instructions[2])),
				new Point(Integer.parseInt(instructions[3]), Integer.parseInt(instructions[4])));
	}

	@Override
	protected boolean performInstruction(Pair<Point, Point> pointPointPair, BeaconRowWithWhitelist beaconRow) {
		if (pointPointPair.getRight().getY() == ROW_TO_CHECK) {
			beaconRow.addBeacon(pointPointPair.getRight().getX());
		}

		var distanceToBeacon = pointPointPair.getLeft().computeManhattanDistance(pointPointPair.getRight());
		var distanceToRowToCheck = pointPointPair	.getLeft()
													.computeManhattanDistance(
															new Point(pointPointPair.getLeft().getX(), ROW_TO_CHECK));
		var difference = distanceToBeacon - distanceToRowToCheck;

		if (difference >= 0) {
			beaconRow.addInterval(pointPointPair.getLeft().getX() - difference,
					pointPointPair.getLeft().getX() + difference);
		}
		return false;
	}

	@Override
	protected String getSolution(BeaconRowWithWhitelist beaconRow) {
		return beaconRow.countImpossiblePositions() + "";
	}
}

class BeaconRowWithWhitelist {
	private final List<Pair<Integer, Integer>> impossibleIntervals = new ArrayList<>();
	private final Set<Integer> beacons = new HashSet<>();

	public void addInterval(int min, int max) {
		impossibleIntervals.add(Pair.of(min, max));
		impossibleIntervals.sort(
				Comparator.comparing((Pair<Integer, Integer> p) -> p.getLeft()).thenComparing(Pair::getRight));
		for (var i = 0; i < impossibleIntervals.size() - 1; i++) {
			if (impossibleIntervals.get(i + 1).getLeft() <= impossibleIntervals.get(i).getRight() + 1) {
				var newInterval = Pair.of(impossibleIntervals.get(i).getLeft(),
						Math.max(impossibleIntervals.get(i).getRight(), impossibleIntervals.get(i + 1).getRight()));
				impossibleIntervals.set(i, newInterval);
				impossibleIntervals.remove(i + 1);
				i--;
			}
		}
	}

	public void addBeacon(int position) {
		beacons.add(position);
	}

	public int countImpossiblePositions() {
		return impossibleIntervals.stream().mapToInt((pair) -> pair.getRight() - pair.getLeft() + 1).sum()
				- beacons.size();
	}
}