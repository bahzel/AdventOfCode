package solution.y2019;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import utils.Point3D;
import utils.soution.MapSolution;

public class Day12_1 extends MapSolution<List<Pair<Point3D, Point3D>>> {
	public static void main(String[] args) {
		new Day12_1().solve();
	}

	@Override
	protected List<Pair<Point3D, Point3D>> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<Pair<Point3D, Point3D>> pairs) {
		var instructions = instruction	.replace("<x=", "")
										.replace(" y=", "")
										.replace(" z=", "")
										.replace(">", "")
										.split(",");
		pairs.add(Pair.of(new Point3D(Integer.parseInt(instructions[0]), Integer.parseInt(instructions[1]),
				Integer.parseInt(instructions[2])), new Point3D(0, 0, 0)));
	}

	@Override
	protected String computeSolution(List<Pair<Point3D, Point3D>> moons) {
		for (var i = 0; i < 1000; i++) {
			computeNextStep(moons);
		}

		return moons.stream()
					.mapToLong(moon -> (long) (Math.abs(moon.getLeft().getX()) + Math.abs(moon.getLeft().getY())
							+ Math.abs(moon.getLeft().getZ()))
							* (Math.abs(moon.getRight().getX()) + Math.abs(moon.getRight().getY())
									+ Math.abs(moon.getRight().getZ())))
					.sum()
				+ "";
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
