package solution.y2020;

import java.util.HashSet;
import java.util.Set;

import utils.Point4D;
import utils.soution.Solution;

public class Day17_2 extends Solution {
	public static void main(String[] args) {
		new Day17_2().solve();
	}

	@Override
	protected String doSolve() {
		var currentState = getInitialState();
		for (var i = 0; i < 6; i++) {
			var nextState = new HashSet<Point4D>();
			var maxX = currentState.stream().mapToInt(Point4D::getX).max().orElseThrow();
			var maxY = currentState.stream().mapToInt(Point4D::getY).max().orElseThrow();
			var maxZ = currentState.stream().mapToInt(Point4D::getZ).max().orElseThrow();
			var maxT = currentState.stream().mapToInt(Point4D::getT).max().orElseThrow();
			for (var x = currentState.stream().mapToInt(Point4D::getX).min().orElseThrow() - 1; x <= maxX + 1; x++) {
				for (var y = currentState.stream().mapToInt(Point4D::getY).min().orElseThrow() - 1; y <= maxY
						+ 1; y++) {
					for (var z = currentState.stream().mapToInt(Point4D::getZ).min().orElseThrow() - 1; z <= maxZ
							+ 1; z++) {
						for (var t = currentState.stream().mapToInt(Point4D::getT).min().orElseThrow() - 1; t <= maxT
								+ 1; t++) {
							var candidate = new Point4D(x, y, z, t);
							var neighbours = countNeighbours(candidate, currentState);
							if (neighbours == 3 || neighbours == 2 && currentState.contains(candidate)) {
								nextState.add(candidate);
							}
						}
					}
				}
			}
			currentState = nextState;
		}
		return currentState.size() + "";
	}

	private int countNeighbours(Point4D point, Set<Point4D> currentState) {
		var counter = 0;
		for (int x = point.getX() - 1; x <= point.getX() + 1; x++) {
			for (int y = point.getY() - 1; y <= point.getY() + 1; y++) {
				for (int z = point.getZ() - 1; z <= point.getZ() + 1; z++) {
					for (int t = point.getT() - 1; t <= point.getT() + 1; t++) {
						var neighbour = new Point4D(x, y, z, t);
						if (neighbour.equals(point)) {
							continue;
						}
						if (currentState.contains(neighbour)) {
							counter++;
						}
					}
				}
			}
		}
		return counter;
	}

	private Set<Point4D> getInitialState() {
		var initialState = new HashSet<Point4D>();
		for (var i = 0; i < input.size(); i++) {
			for (var j = 0; j < input.get(i).length(); j++) {
				if (input.get(i).charAt(j) == '#') {
					initialState.add(new Point4D(i, j, 0, 0));
				}
			}
		}
		return initialState;
	}
}
