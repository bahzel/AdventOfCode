package solution.y2022;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import utils.Point3D;
import utils.soution.MapSolution;

public class Day18_2 extends MapSolution<Set<Point3D>> {
	public static void main(String[] args) {
		new Day18_2().solve();
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
		var minX = point3DS.stream().mapToInt(Point3D::getX).min().orElseThrow();
		var maxX = point3DS.stream().mapToInt(Point3D::getX).max().orElseThrow();
		var minY = point3DS.stream().mapToInt(Point3D::getY).min().orElseThrow();
		var maxY = point3DS.stream().mapToInt(Point3D::getY).max().orElseThrow();
		var minZ = point3DS.stream().mapToInt(Point3D::getZ).min().orElseThrow();
		var maxZ = point3DS.stream().mapToInt(Point3D::getZ).max().orElseThrow();

		return point3DS	.stream()
						.mapToInt((point) -> (isSideExposed(new Point3D(point.getX() - 1, point.getY(), point.getZ()),
								point3DS, minX, maxX, minY, maxY, minZ, maxZ) ? 1 : 0)
								+ (isSideExposed(new Point3D(point.getX() + 1, point.getY(), point.getZ()), point3DS,
										minX, maxX, minY, maxY, minZ, maxZ) ? 1 : 0)
								+ (isSideExposed(new Point3D(point.getX(), point.getY() + 1, point.getZ()), point3DS,
										minX, maxX, minY, maxY, minZ, maxZ) ? 1 : 0)
								+ (isSideExposed(new Point3D(point.getX(), point.getY() - 1, point.getZ()), point3DS,
										minX, maxX, minY, maxY, minZ, maxZ) ? 1 : 0)
								+ (isSideExposed(new Point3D(point.getX(), point.getY(), point.getZ() - 1), point3DS,
										minX, maxX, minY, maxY, minZ, maxZ) ? 1 : 0)
								+ (isSideExposed(new Point3D(point.getX(), point.getY(), point.getZ() + 1), point3DS,
										minX, maxX, minY, maxY, minZ, maxZ) ? 1 : 0))
						.sum()
				+ "";
	}

	private boolean isSideExposed(Point3D point, Set<Point3D> metalPoints, int minX, int maxX, int minY, int maxY,
			int minZ, int maxZ) {
		Queue<Point3D> queue = new LinkedList<>();
		queue.add(point);
		Set<Point3D> visited = new HashSet<>();

		while (!queue.isEmpty()) {
			var current = queue.poll();
			if (metalPoints.contains(current) || !visited.add(current)) {
				continue;
			}

			if (current.getX() < minX || current.getX() > maxX || current.getY() < minY || current.getY() > maxY
					|| current.getZ() < minZ || current.getZ() > maxZ) {
				return true;
			}

			queue.add(new Point3D(current.getX() - 1, current.getY(), current.getZ()));
			queue.add(new Point3D(current.getX() + 1, current.getY(), current.getZ()));
			queue.add(new Point3D(current.getX(), current.getY() - 1, current.getZ()));
			queue.add(new Point3D(current.getX(), current.getY() + 1, current.getZ()));
			queue.add(new Point3D(current.getX(), current.getY(), current.getZ() - 1));
			queue.add(new Point3D(current.getX(), current.getY(), current.getZ() + 1));
		}
		return false;
	}
}