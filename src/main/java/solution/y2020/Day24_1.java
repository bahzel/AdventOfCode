package solution.y2020;

import java.util.ArrayList;
import java.util.List;

import utils.CardinalDirection;
import utils.Point;
import utils.soution.Solution;

public class Day24_1 extends Solution {
	public static void main(String[] args) {
		new Day24_1().solve();
	}

	@Override
	protected String doSolve() {
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

		return tiles.size() + "";
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
