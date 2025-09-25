package solution.y2024;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day12_2 extends GridSolution<Character> {
	public static void main(String[] args) {
		new Day12_2().solve();
	}

	@Override
	protected GridElement<Character> transformCell(char ch, int x, int y) {
		return new GridElement<>(ch, x, y);
	}

	@Override
	protected String computeSolution() {
		return stream().mapToLong(this::computeCost).sum() + "";
	}

	private long computeCost(GridElement<Character> plot) {
		if (plot.getValue() == 0) {
			return 0;
		}

		var region = new HashSet<GridElement<Character>>();
		Queue<GridElement<Character>> queue = new LinkedList<>();
		queue.add(plot);
		while (!queue.isEmpty()) {
			GridElement<Character> neighbour = queue.remove();
			if (neighbour.getValue() == plot.getValue() && region.add(neighbour)) {
				queue.addAll(neighbour.getBorderingNeighbours());
			}
		}

		var area = region.size();
		var countSides = 0L;

		var xMin = region.stream().mapToInt(tile -> tile.getCoordinates().getX()).min().orElseThrow();
		var xMax = region.stream().mapToInt(tile -> tile.getCoordinates().getX()).max().orElseThrow();
		var yMin = region.stream().mapToInt(tile -> tile.getCoordinates().getY()).min().orElseThrow();
		var yMax = region.stream().mapToInt(tile -> tile.getCoordinates().getY()).max().orElseThrow();
		for (int y = yMin; y <= yMax; y++) {

			var insideUpperSide = false;
			var insideLowerSide = false;
			for (int x = xMin; x <= xMax; x++) {
				if (region.contains(grid[x][y])) {
					if (grid[x][y].getUpperNeighbour() == null
							|| grid[x][y].getUpperNeighbour().getValue() != plot.getValue()) {
						if (!insideUpperSide) {
							countSides++;
							insideUpperSide = true;
						}
					} else {
						insideUpperSide = false;
					}

					if (grid[x][y].getLowerNeighbour() == null
							|| grid[x][y].getLowerNeighbour().getValue() != plot.getValue()) {
						if (!insideLowerSide) {
							countSides++;
							insideLowerSide = true;
						}
					} else {
						insideLowerSide = false;
					}
				} else {
					insideUpperSide = false;
					insideLowerSide = false;
				}
			}
		}

		for (int x = xMin; x <= xMax; x++) {

			var insideLeftSide = false;
			var insideRightSide = false;
			for (int y = yMin; y <= yMax; y++) {
				if (region.contains(grid[x][y])) {
					if (grid[x][y].getLeftNeighbour() == null
							|| grid[x][y].getLeftNeighbour().getValue() != plot.getValue()) {
						if (!insideLeftSide) {
							countSides++;
							insideLeftSide = true;
						}
					} else {
						insideLeftSide = false;
					}

					if (grid[x][y].getRightNeighbour() == null
							|| grid[x][y].getRightNeighbour().getValue() != plot.getValue()) {
						if (!insideRightSide) {
							countSides++;
							insideRightSide = true;
						}
					} else {
						insideRightSide = false;
					}
				} else {
					insideLeftSide = false;
					insideRightSide = false;
				}
			}
		}

		region.forEach(tile -> tile.setValue((char) 0));
		return area * countSides;
	}
}
