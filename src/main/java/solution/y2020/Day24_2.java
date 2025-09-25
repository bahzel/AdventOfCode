package solution.y2020;

import java.util.ArrayList;
import java.util.List;

import utils.CardinalDirection;
import utils.Point;
import utils.soution.Solution;

public class Day24_2 extends Solution {
	public static void main(String[] args) {
		new Day24_2().solve();
	}

	@Override
	protected String doSolve() {
		var currentTiles = getStartingTiles();
		for (var i = 0; i < 100; i++) {
			var minX = currentTiles.stream().mapToInt(Point::getX).min().orElseThrow() - 1;
			var maxX = currentTiles.stream().mapToInt(Point::getX).max().orElseThrow() + 1;
			var minY = currentTiles.stream().mapToInt(Point::getY).min().orElseThrow() - 1;
			var maxY = currentTiles.stream().mapToInt(Point::getY).max().orElseThrow() + 1;
			var nextTiles = new ArrayList<Point>();

			for (var x = minX; x <= maxX; x++) {
				for (var y = minY; y <= maxY; y++) {
					var candidate = new Point(x, y);
					var countNeighbours = countNeighbours(currentTiles, candidate);
					if (countNeighbours == 2 || countNeighbours == 1 && currentTiles.contains(candidate)) {
						nextTiles.add(candidate);
					}
				}
			}

			currentTiles = nextTiles;
		}
		return currentTiles.size() + "";
	}

	private int countNeighbours(List<Point> tiles, Point tile) {
		var count = 0;
		if (tiles.contains(new Point(tile.getX() - 1, tile.getY()))) {
			count++;
		}
		if (tiles.contains(new Point(tile.getX() + 1, tile.getY()))) {
			count++;
		}
		if (tiles.contains(new Point(tile.getX(), tile.getY() - 1))) {
			count++;
		}
		if (tiles.contains(new Point(tile.getX() - 1, tile.getY() - 1))) {
			count++;
		}
		if (tiles.contains(new Point(tile.getX(), tile.getY() + 1))) {
			count++;
		}
		if (tiles.contains(new Point(tile.getX() + 1, tile.getY() + 1))) {
			count++;
		}
		return count;
	}

	private List<Point> getStartingTiles() {
		List<Point> tiles = new ArrayList<>();
		for (var directionList : getDirectionLists()) {
			var x = 0;
			var y = 0;
			for (var direction : directionList) {
				switch (direction) {
				case EAST:
					x++;
					break;
				case WEST:
					x--;
					break;
				case NORTH_WEST:
					x--;
					y--;
					break;
				case NORTH_EAST:
					y--;
					break;
				case SOUTH_WEST:
					y++;
					break;
				case SOUTH_EAST:
					y++;
					x++;
					break;
				default:
					throw new IllegalStateException("Unexpected value: " + direction);
				}
			}
			var newTile = new Point(x, y);
			if (tiles.contains(newTile)) {
				tiles.remove(newTile);
			} else {
				tiles.add(newTile);
			}
		}

		return tiles;
	}

	private List<List<CardinalDirection>> getDirectionLists() {
		var directionList = new ArrayList<List<CardinalDirection>>();
		for (var line : input) {
			var directions = new ArrayList<CardinalDirection>();
			for (var i = 0; i < line.length(); i++) {
				switch (line.charAt(i)) {
				case 'e':
					directions.add(CardinalDirection.EAST);
					break;
				case 'w':
					directions.add(CardinalDirection.WEST);
					break;
				case 'n':
					switch (line.charAt(i + 1)) {
					case 'e':
						directions.add(CardinalDirection.NORTH_EAST);
						break;
					case 'w':
						directions.add(CardinalDirection.NORTH_WEST);
						break;
					default:
						throw new IllegalArgumentException("Invalid direction: " + line);
					}
					i++;
					break;
				case 's':
					switch (line.charAt(i + 1)) {
					case 'e':
						directions.add(CardinalDirection.SOUTH_EAST);
						break;
					case 'w':
						directions.add(CardinalDirection.SOUTH_WEST);
						break;
					default:
						throw new IllegalArgumentException("Invalid direction: " + line);
					}
					i++;
					break;
				}
			}
			directionList.add(directions);
		}
		return directionList;
	}
}
