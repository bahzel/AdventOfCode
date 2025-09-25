package solution.y2020;

import java.util.HashSet;
import java.util.Set;

import utils.Point3D;
import utils.soution.Solution;

public class Day17_1 extends Solution {
	public static void main(String[] args) {
		new Day17_1().solve();
	}

	@Override
	protected String doSolve() {
		var currentState = getInitialState();
		for (var i = 0; i < 6; i++) {
			var nextState = new HashSet<Point3D>();
			var maxX = currentState.stream().mapToInt(Point3D::getX).max().orElseThrow();
			var maxY = currentState.stream().mapToInt(Point3D::getY).max().orElseThrow();
			var maxZ = currentState.stream().mapToInt(Point3D::getZ).max().orElseThrow();
			for (var x = currentState.stream().mapToInt(Point3D::getX).min().orElseThrow() - 1; x <= maxX + 1; x++) {
				for (var y = currentState.stream().mapToInt(Point3D::getY).min().orElseThrow() - 1; y <= maxY
						+ 1; y++) {
					for (var z = currentState.stream().mapToInt(Point3D::getZ).min().orElseThrow() - 1; z <= maxZ
							+ 1; z++) {
						var candidate = new Point3D(x, y, z);
						var neighbours = countNeighbours(candidate, currentState);
						if (neighbours == 3 || neighbours == 2 && currentState.contains(candidate)) {
							nextState.add(candidate);
						}
					}
				}
			}
			currentState = nextState;
		}
		return currentState.size() + "";
	}

	private int countNeighbours(Point3D point, Set<Point3D> currentState) {
		var counter = 0;
		for (int x = point.getX() - 1; x <= point.getX() + 1; x++) {
			for (int y = point.getY() - 1; y <= point.getY() + 1; y++) {
				for (int z = point.getZ() - 1; z <= point.getZ() + 1; z++) {
					var neighbour = new Point3D(x, y, z);
					if (neighbour.equals(point)) {
						continue;
					}
					if (currentState.contains(neighbour)) {
						counter++;
					}
				}
			}
		}
		return counter;
	}

	private Set<Point3D> getInitialState() {
		var initialState = new HashSet<Point3D>();
		for (var i = 0; i < input.size(); i++) {
			for (var j = 0; j < input.get(i).length(); j++) {
				if (input.get(i).charAt(j) == '#') {
					initialState.add(new Point3D(i, j, 0));
				}
			}
		}
		return initialState;
	}
}
