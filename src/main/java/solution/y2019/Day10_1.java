package solution.y2019;

import java.util.HashSet;

import utils.MathUtils;
import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day10_1 extends GridSolution<Integer> {
	public static void main(String[] args) {
		new Day10_1().solve();
	}

	@Override
	protected GridElement<Integer> transformCell(char ch, int x, int y) {
		return switch (ch) {
		case '#' -> new GridElement<>(0, x, y);
		case '.' -> new GridElement<>(-1, x, y);
		default -> throw new IllegalArgumentException("Invalid character: " + ch);
		};
	}

	@Override
	protected String computeSolution() {
		stream().forEach(tile -> {
			if (tile.getValue() == -1) {
				return;
			}

			var seenAsteroids = new HashSet<GridElement<Integer>>();
			var hiddenAsteroids = new HashSet<GridElement<Integer>>();

			for (int y = tile.getCoordinates().getY(); y >= 0; y--) {
				for (int x = tile.getCoordinates().getX(); x >= 0; x--) {
					var asteroid = grid[x][y];
					if (asteroid.getValue() == -1 || asteroid == tile || hiddenAsteroids.contains(asteroid)) {
						continue;
					}

					seenAsteroids.add(asteroid);

					var deltaX = tile.getCoordinates().getX() - asteroid.getCoordinates().getX();
					var deltaY = tile.getCoordinates().getY() - asteroid.getCoordinates().getY();
					var greatestCommonDivisor = MathUtils.greatestCommonDivisor(deltaX, deltaY);
					deltaX /= greatestCommonDivisor;
					deltaY /= greatestCommonDivisor;
					for (int i = 2;; i++) {
						var newX = tile.getCoordinates().getX() - deltaX * i;
						var newY = tile.getCoordinates().getY() - deltaY * i;
						if (newX < 0 || newY < 0) {
							break;
						}

						if (grid[newX][newY].getValue() > -1) {
							hiddenAsteroids.add(grid[newX][newY]);
						}
					}
				}
			}

			for (int y = tile.getCoordinates().getY(); y >= 0; y--) {
				for (int x = tile.getCoordinates().getX() + 1; x < grid.length; x++) {
					var asteroid = grid[x][y];
					if (asteroid.getValue() == -1 || hiddenAsteroids.contains(asteroid)) {
						continue;
					}

					seenAsteroids.add(asteroid);

					var deltaX = asteroid.getCoordinates().getX() - tile.getCoordinates().getX();
					var deltaY = tile.getCoordinates().getY() - asteroid.getCoordinates().getY();
					var greatestCommonDivisor = MathUtils.greatestCommonDivisor(deltaX, deltaY);
					deltaX /= greatestCommonDivisor;
					deltaY /= greatestCommonDivisor;
					for (int i = 2;; i++) {
						var newX = tile.getCoordinates().getX() + deltaX * i;
						var newY = tile.getCoordinates().getY() - deltaY * i;
						if (newX >= grid.length || newY < 0) {
							break;
						}

						if (grid[newX][newY].getValue() > -1) {
							hiddenAsteroids.add(grid[newX][newY]);
						}
					}
				}
			}

			for (int y = tile.getCoordinates().getY() + 1; y < grid[0].length; y++) {
				for (int x = tile.getCoordinates().getX(); x >= 0; x--) {
					var asteroid = grid[x][y];
					if (asteroid.getValue() == -1 || hiddenAsteroids.contains(asteroid)) {
						continue;
					}

					seenAsteroids.add(asteroid);

					var deltaX = tile.getCoordinates().getX() - asteroid.getCoordinates().getX();
					var deltaY = asteroid.getCoordinates().getY() - tile.getCoordinates().getY();
					var greatestCommonDivisor = MathUtils.greatestCommonDivisor(deltaX, deltaY);
					deltaX /= greatestCommonDivisor;
					deltaY /= greatestCommonDivisor;
					for (int i = 2;; i++) {
						var newX = tile.getCoordinates().getX() - deltaX * i;
						var newY = tile.getCoordinates().getY() + deltaY * i;
						if (newX < 0 || newY >= grid[0].length) {
							break;
						}

						if (grid[newX][newY].getValue() > -1) {
							hiddenAsteroids.add(grid[newX][newY]);
						}
					}
				}
			}

			for (int y = tile.getCoordinates().getY() + 1; y < grid[0].length; y++) {
				for (int x = tile.getCoordinates().getX() + 1; x < grid.length; x++) {
					var asteroid = grid[x][y];
					if (asteroid.getValue() == -1 || hiddenAsteroids.contains(asteroid)) {
						continue;
					}

					seenAsteroids.add(asteroid);

					var deltaX = asteroid.getCoordinates().getX() - tile.getCoordinates().getX();
					var deltaY = asteroid.getCoordinates().getY() - tile.getCoordinates().getY();
					var greatestCommonDivisor = MathUtils.greatestCommonDivisor(deltaX, deltaY);
					deltaX /= greatestCommonDivisor;
					deltaY /= greatestCommonDivisor;
					for (int i = 1;; i++) {
						var newX = asteroid.getCoordinates().getX() + deltaX * i;
						var newY = asteroid.getCoordinates().getY() + deltaY * i;
						if (newX >= grid.length || newY >= grid[0].length) {
							break;
						}

						if (grid[newX][newY].getValue() > -1) {
							hiddenAsteroids.add(grid[newX][newY]);
						}
					}
				}
			}

			tile.setValue(seenAsteroids.size());
		});

		return stream().filter(asteroid -> asteroid.getValue() > 0).mapToLong(GridElement::getValue).max().orElseThrow()
				+ "";
	}
}
