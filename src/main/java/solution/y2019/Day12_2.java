package solution.y2019;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import utils.MathUtils;
import utils.Point3D;
import utils.soution.MapSolution;

public class Day12_2 extends MapSolution<List<Pair<Point3D, Point3D>>> {
	public static void main(String[] args) {
		new Day12_2().solve();
	}

	@Override
	protected List<Pair<Point3D, Point3D>> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<Pair<Point3D, Point3D>> pairs) {
		var instructions = instruction.replace("<x=", "")
									  .replace(" y=", "")
									  .replace(" z=", "")
									  .replace(">", "")
									  .split(",");
		pairs.add(Pair.of(new Point3D(Integer.parseInt(instructions[0]), Integer.parseInt(instructions[1]),
				Integer.parseInt(instructions[2])), new Point3D(0, 0, 0)));
	}

	@Override
	protected String computeSolution(List<Pair<Point3D, Point3D>> moons) {
		var cache = new HashSet<CachedAxis>();
		var moonCopy = copy(moons);
		var xRepetitions = new ArrayList<Long>();
		var firstRepetition = 0L;
		var previousI = 0L;

		for (var i = 0L; ; i++) {
			if (!cache.add(new CachedAxis(moonCopy.getFirst().getLeft().getX(), moonCopy.get(1).getLeft().getX(),
					moonCopy.get(2).getLeft().getX(), moonCopy.get(3).getLeft().getX()))) {
				if (i - previousI == firstRepetition) {
					break;
				} else if (firstRepetition == 0) {
					firstRepetition = i - previousI;
				}
				xRepetitions.add(i - previousI);
				previousI = i;
				cache.clear();
				cache.add(new CachedAxis(moonCopy.getFirst().getLeft().getX(), moonCopy.get(1).getLeft().getX(),
						moonCopy.get(2).getLeft().getX(), moonCopy.get(3).getLeft().getX()));
			}
			computeNextStep(moonCopy);
		}

		cache.clear();
		moonCopy = copy(moons);
		var yRepetitions = new ArrayList<Long>();
		firstRepetition = 0L;
		previousI = 0L;

		for (var i = 0L; ; i++) {
			if (!cache.add(new CachedAxis(moonCopy.getFirst().getLeft().getY(), moonCopy.get(1).getLeft().getY(),
					moonCopy.get(2).getLeft().getY(), moonCopy.get(3).getLeft().getY()))) {
				if (i - previousI == firstRepetition) {
					break;
				} else if (firstRepetition == 0) {
					firstRepetition = i - previousI;
				}
				yRepetitions.add(i - previousI);
				previousI = i;
				cache.clear();
				cache.add(new CachedAxis(moonCopy.getFirst().getLeft().getY(), moonCopy.get(1).getLeft().getY(),
						moonCopy.get(2).getLeft().getY(), moonCopy.get(3).getLeft().getY()));
			}
			computeNextStep(moonCopy);
		}

		cache.clear();
		moonCopy = copy(moons);
		var zRepetitions = new ArrayList<Long>();
		firstRepetition = 0L;
		previousI = 0L;

		for (var i = 0L; ; i++) {
			if (!cache.add(new CachedAxis(moonCopy.getFirst().getLeft().getZ(), moonCopy.get(1).getLeft().getZ(),
					moonCopy.get(2).getLeft().getZ(), moonCopy.get(3).getLeft().getZ()))) {
				if (i - previousI == firstRepetition) {
					break;
				} else if (firstRepetition == 0) {
					firstRepetition = i - previousI;
				}
				zRepetitions.add(i - previousI);
				previousI = i;
				cache.clear();
				cache.add(new CachedAxis(moonCopy.getFirst().getLeft().getZ(), moonCopy.get(1).getLeft().getZ(),
						moonCopy.get(2).getLeft().getZ(), moonCopy.get(3).getLeft().getZ()));
			}
			computeNextStep(moonCopy);
		}

		return MathUtils.leastCommonMultiple(xRepetitions.stream().mapToLong(Long::longValue).sum(),
				MathUtils.leastCommonMultiple(yRepetitions.stream().mapToLong(Long::longValue).sum(),
						zRepetitions.stream().mapToLong(Long::longValue).sum())) + "";
	}

	private List<Pair<Point3D, Point3D>> copy(List<Pair<Point3D, Point3D>> moons) {
		var copy = new ArrayList<Pair<Point3D, Point3D>>();
		moons.forEach(moon -> copy.add(Pair.of(new Point3D(moon.getLeft()), new Point3D(moon.getRight()))));
		return copy;
	}

	private void computeNextStep(List<Pair<Point3D, Point3D>> moons) {
		moons.forEach(moon -> moons.forEach(neighbour -> {
			var deltaX = neighbour.getLeft().getX() - moon.getLeft().getX();
			var deltaY = neighbour.getLeft().getY() - moon.getLeft().getY();
			var deltaZ = neighbour.getLeft().getZ() - moon.getLeft().getZ();
			moon.getRight().setX(moon.getRight().getX() + Integer.compare(deltaX, 0));
			moon.getRight().setY(moon.getRight().getY() + Integer.compare(deltaY, 0));
			moon.getRight().setZ(moon.getRight().getZ() + Integer.compare(deltaZ, 0));
		}));

		moons.forEach(moon -> {
			moon.getLeft().setX(moon.getLeft().getX() + moon.getRight().getX());
			moon.getLeft().setY(moon.getLeft().getY() + moon.getRight().getY());
			moon.getLeft().setZ(moon.getLeft().getZ() + moon.getRight().getZ());
		});
	}
}

record CachedAxis(int firstMoon, int secondMoon, int thirdMoon, int fourthMoon) {
}