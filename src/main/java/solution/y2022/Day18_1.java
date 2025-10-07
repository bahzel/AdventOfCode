package solution.y2022;

import java.util.HashSet;
import java.util.Set;

import utils.Point3D;
import utils.soution.MapSolution;

public class Day18_1 extends MapSolution<Set<Point3D>> {
	public static void main(String[] args) {
		new Day18_1().solve();
	}

	@Override
	protected Set<Point3D> initializeMapping() {
		return new HashSet<>();
	}

	@Override
	protected void transformInstruction(String instruction, Set<Point3D> point3DS) {
		var instructions = instruction.split(",");
		point3DS.add(new Point3D(Integer.parseInt(instructions[0]), Integer.parseInt(instructions[1]),
				Integer.parseInt(instructions[2])));
	}

	@Override
	protected String computeSolution(Set<Point3D> point3DS) {
		return point3DS	.stream()
						.mapToInt((point) -> (point3DS.contains(
								new Point3D(point.getX() - 1, point.getY(), point.getZ())) ? 0 : 1)
								+ (point3DS.contains(new Point3D(point.getX() + 1, point.getY(), point.getZ())) ? 0 : 1)
								+ (point3DS.contains(new Point3D(point.getX(), point.getY() - 1, point.getZ())) ? 0 : 1)
								+ (point3DS.contains(new Point3D(point.getX(), point.getY() + 1, point.getZ())) ? 0 : 1)
								+ (point3DS.contains(new Point3D(point.getX(), point.getY(), point.getZ() - 1)) ? 0 : 1)
								+ (point3DS.contains(new Point3D(point.getX(), point.getY(), point.getZ() + 1)) ? 0
										: 1))
						.sum()
				+ "";
	}
}