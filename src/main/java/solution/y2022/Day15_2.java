package solution.y2022;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import utils.Point;
import utils.soution.InstructionSolution;

public class Day15_2 extends InstructionSolution<Pair<Point, Point>, List<BeaconRow>> {
	private final int MAXIMUM_INDEX = 4000000;

	public static void main(String[] args) {
		new Day15_2().solve();
	}

	@Override
	protected List<BeaconRow> initializeValue() {
		var rows = new ArrayList<BeaconRow>();
		for (var i = 0; i <= MAXIMUM_INDEX; i++) {
			rows.add(new BeaconRow());
		}
		return rows;
	}

	@Override
	protected Pair<Point, Point> transformInstruction(String instruction) {
		var instructions = instruction.replace(", y=", "=").replace(": closest beacon is at x=", "=").split("=");
		return Pair.of(new Point(Integer.parseInt(instructions[1]), Integer.parseInt(instructions[2])),
				new Point(Integer.parseInt(instructions[3]), Integer.parseInt(instructions[4])));
	}

	@Override
	protected boolean performInstruction(Pair<Point, Point> pointPointPair, List<BeaconRow> beaconRow) {
		for (var i = 0; i < beaconRow.size(); i++) {
			var distanceToBeacon = pointPointPair.getLeft().computeManhattanDistance(pointPointPair.getRight());
			var distanceToRowToCheck = pointPointPair	.getLeft()
														.computeManhattanDistance(
																new Point(pointPointPair.getLeft().getX(), i));
			var difference = distanceToBeacon - distanceToRowToCheck;

			if (difference >= 0) {
				beaconRow	.get(i)
							.addInterval(pointPointPair.getLeft().getX() - difference,
									pointPointPair.getLeft().getX() + difference);
			}
		}
		return false;
	}

	@Override
	protected String getSolution(List<BeaconRow> beaconRow) {
		for (var i = 0; i < beaconRow.size(); i++) {
			var position = beaconRow.get(i).getPossiblePosition();
			if (position > -1) {
				return position * 4000000L + i + "";
			}
		}

		throw new RuntimeException("No solution found");
	}
}

class BeaconRow {
	private final int MINIMUM_INDEX = 0;
	private final int MAXIMUM_INDEX = 4000000;

	private final List<Pair<Integer, Integer>> impossibleIntervals = new ArrayList<>();

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

	public int getPossiblePosition() {
		for (var interval : impossibleIntervals) {
			if (interval.getRight() >= MINIMUM_INDEX && interval.getRight() < MAXIMUM_INDEX) {
				return interval.getRight() + 1;
			}
		}
		return -1;
	}
}