package solution.y2017;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import utils.Point3D;
import utils.soution.MapSolution;

public class Day20_1 extends MapSolution<List<Pair<Point3D, Point3D>>> {
	public static void main(String[] args) {
		new Day20_1().solve();
	}

	@Override
	protected List<Pair<Point3D, Point3D>> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<Pair<Point3D, Point3D>> point3DS) {
		var instructions = instruction.replace(">", "").split(", v=<")[1].split(", a=<");
		var velocity = instructions[0].split(",");
		var acceleration = instructions[1].split(",");

		point3DS.add(Pair.of(
				new Point3D(Integer.parseInt(acceleration[0]), Integer.parseInt(acceleration[1]),
						Integer.parseInt(acceleration[2])),
				new Point3D(Integer.parseInt(velocity[0]), Integer.parseInt(velocity[1]),
						Integer.parseInt(velocity[2]))));
	}

	@Override
	protected String computeSolution(List<Pair<Point3D, Point3D>> point3DS) {
		var zero = new Point3D(0, 0, 0);
		var slowestAcceleration = point3DS	.stream()
											.map(point -> point.getLeft().computeManhattanDistance(zero))
											.mapToInt(value -> value)
											.min()
											.orElseThrow();
		var slowestPoint = point3DS	.stream()
									.filter(point -> point	.getLeft()
															.computeManhattanDistance(zero) == slowestAcceleration)
									.min((p1, p2) -> p1.getLeft().getX() + 1000 * p1.getRight().getX()
											+ p1.getLeft().getY() + 1000 * p1.getRight().getY() + p1.getLeft().getZ()
											+ 1000 * p1.getRight().getZ() - p2.getLeft().getX()
											- 1000 * p2.getRight().getX() - p2.getLeft().getY()
											- 1000 * p2.getRight().getY() - p2.getLeft().getZ()
											- 1000 * p2.getRight().getZ())
									.orElseThrow();
		return point3DS.indexOf(slowestPoint) + "";
	}
}
