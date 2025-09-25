package solution.y2024;

import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day20_2 extends GridSolution<Integer> {
	private final int SHORTCUT_THRESHOLD = 100;
	private GridElement<Integer> start;
	private GridElement<Integer> goal;

	public static void main(String[] args) {
		new Day20_2().solve();
	}

	@Override
	protected GridElement<Integer> transformCell(char ch, int x, int y) {
		return switch (ch) {
			case '#' -> new GridElement<>(-1, x, y);
			case 'S' -> {
				start = new GridElement<>(0, x, y);
				yield start;
			}
			case 'E' -> {
				goal = new GridElement<>(0, x, y);
				yield goal;
			}
			case '.' -> new GridElement<>(0, x, y);
			default -> throw new IllegalArgumentException("Invalid character: " + ch);
		};
	}

	@Override
	protected String computeSolution() {
		computeCost();
		var usefulCheats = 0;

		var startingTiles = stream().filter(tile -> tile.getValue() != -1).toList();
		for (var startingTile : startingTiles) {
			var x = startingTile.getCoordinates().getX();
			var y = startingTile.getCoordinates().getY();
			for (var deltaX = -20; deltaX <= 20; deltaX++) {
				for (var deltaY = -20 + Math.abs(deltaX); deltaY <= 20 - Math.abs(deltaX); deltaY++) {
					if (x + deltaX < 0 || x + deltaX >= grid.length || y + deltaY < 0 || y + deltaY >= grid[0].length) {
						continue;
					}
					if (grid[x + deltaX][y + deltaY].getValue() - startingTile.getValue() - grid[x + deltaX][y
							+ deltaY].getCoordinates().computeManhattanDistance(startingTile.getCoordinates())
							>= SHORTCUT_THRESHOLD) {
						usefulCheats++;
					}
				}
			}
		}
		return usefulCheats + "";
	}

	private void computeCost() {
		GridElement<Integer> lastElement = null;
		var currentElement = start;
		for (int i = 1; currentElement != goal; i++) {
			var finalLastElement = lastElement;
			var nextElement = currentElement.getBorderingNeighbours()
											.stream()
											.filter(neighbour -> neighbour != finalLastElement
													&& neighbour.getValue() != -1)
											.findFirst()
											.orElseThrow();
			lastElement = currentElement;
			currentElement = nextElement;
			currentElement.setValue(i);
		}
	}
}
