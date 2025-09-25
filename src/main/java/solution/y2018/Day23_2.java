package solution.y2018;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import utils.Point3D;
import utils.soution.MapSolution;

public class Day23_2 extends MapSolution<List<Pair<Point3D, Integer>>> {
	public static void main(String[] args) {
		new Day23_2().solve();
	}

	@Override
	protected List<Pair<Point3D, Integer>> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<Pair<Point3D, Integer>> pairs) {
		var instructions = instruction.replace("pos=<", "").replace(">, r=", ",").split(",");
		pairs.add(Pair.of(new Point3D(Integer.parseInt(instructions[0]), Integer.parseInt(instructions[1]),
				Integer.parseInt(instructions[2])), Integer.parseInt(instructions[3])));
	}

	@Override
	protected String computeSolution(List<Pair<Point3D, Integer>> pairs) {
		var strongestCoordinates = getBestPointInRangeOf100(new Point3D(0, 0, 0), shrinkCopy(pairs, 1000000));
		strongestCoordinates = getBestPointInRangeOf100(inflateCopy(strongestCoordinates, 10),
				shrinkCopy(pairs, 100000));
		strongestCoordinates = getBestPointInRangeOf100(inflateCopy(strongestCoordinates, 10),
				shrinkCopy(pairs, 10000));
		strongestCoordinates = getBestPointInRangeOf100(inflateCopy(strongestCoordinates, 10), shrinkCopy(pairs, 1000));
		strongestCoordinates = getBestPointInRangeOf100(inflateCopy(strongestCoordinates, 10), shrinkCopy(pairs, 100));
		strongestCoordinates = getBestPointInRangeOf100(inflateCopy(strongestCoordinates, 10), shrinkCopy(pairs, 10));
		strongestCoordinates = getBestPointInRangeOf100(inflateCopy(strongestCoordinates, 10), pairs);

		return "" + strongestCoordinates.computeManhattanDistance(new Point3D(0, 0, 0));
	}

	private Point3D inflateCopy(Point3D point, int factor) {
		return new Point3D(point.getX() * factor, point.getY() * factor, point.getZ() * factor);
	}

	private List<Pair<Point3D, Integer>> shrinkCopy(List<Pair<Point3D, Integer>> pairs, int factor) {
		var copy = new ArrayList<Pair<Point3D, Integer>>();

		for (var pair : pairs) {
			copy.add(Pair.of(new Point3D(pair.getLeft().getX() / factor, pair.getLeft().getY() / factor,
					pair.getLeft().getZ() / factor), pair.getRight() / factor));
		}

		return copy;
	}

	private Point3D getBestPointInRangeOf100(Point3D startingPoint, List<Pair<Point3D, Integer>> pairs) {
		Point3D maxPoint = null;
		var maxNanobotsInRange = 0;

		for (int deltaX = -100; deltaX <= 100; deltaX++) {
			for (int deltaY = -100; deltaY <= 100; deltaY++) {
				for (int deltaZ = -100; deltaZ <= 100; deltaZ++) {
					var testPoint = new Point3D(startingPoint.getX() + deltaX, startingPoint.getY() + deltaY,
							startingPoint.getZ() + deltaZ);
					var nanobotsInRange = countNanobotsInRange(testPoint, pairs);
					if (nanobotsInRange > maxNanobotsInRange) {
						maxPoint = testPoint;
						maxNanobotsInRange = nanobotsInRange;
					}
				}
			}
		}

		return maxPoint;
	}

	private int countNanobotsInRange(Point3D point, List<Pair<Point3D, Integer>> nanoBots) {
		return (int) nanoBots	.stream()
								.filter(nanobot -> nanobot	.getLeft()
															.computeManhattanDistance(point) <= nanobot.getRight())
								.count();
	}
}
