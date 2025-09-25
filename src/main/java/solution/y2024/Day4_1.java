package solution.y2024;

import java.util.ArrayList;
import java.util.List;

import utils.Point;
import utils.soution.Solution;

public class Day4_1 extends Solution {
	public static void main(String[] args) {
		new Day4_1().solve();
	}

	@Override
	protected String doSolve() {
		var wordSearch = getWordSearch();
		var directions = getDirections();
		var counter = 0;

		for (var x = 0; x < wordSearch.length; x++) {
			for (var y = 0; y < wordSearch[x].length; y++) {
				for (var direction : directions) {
					if (getCharAt(wordSearch, x, y, direction.getFirst()) == 'X'
							&& getCharAt(wordSearch, x, y, direction.get(1)) == 'M'
							&& getCharAt(wordSearch, x, y, direction.get(2)) == 'A'
							&& getCharAt(wordSearch, x, y, direction.get(3)) == 'S') {
						counter++;
					}
				}
			}
		}

		return counter + "";
	}

	private char getCharAt(char[][] wordSearch, int x, int y, Point point) {
		int adjustedX = x + point.getX();
		int adjustedY = y + point.getY();

		if (adjustedX < 0 || adjustedX >= wordSearch.length || adjustedY < 0
				|| adjustedY >= wordSearch[adjustedX].length) {
			return 0;
		} else {
			return wordSearch[adjustedX][adjustedY];
		}
	}

	private char[][] getWordSearch() {
		char[][] wordSearch = new char[input.getFirst().length()][input.size()];
		for (int y = 0; y < input.getFirst().length(); y++) {
			for (int x = 0; x < input.size(); x++) {
				wordSearch[x][y] = input.get(y).charAt(x);
			}
		}
		return wordSearch;
	}

	private List<List<Point>> getDirections() {
		var directions = new ArrayList<List<Point>>();

		directions.add(List.of(new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(3, 0)));
		directions.add(List.of(new Point(0, 0), new Point(-1, 0), new Point(-2, 0), new Point(-3, 0)));
		directions.add(List.of(new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(0, 3)));
		directions.add(List.of(new Point(0, 0), new Point(0, -1), new Point(0, -2), new Point(0, -3)));
		directions.add(List.of(new Point(0, 0), new Point(1, 1), new Point(2, 2), new Point(3, 3)));
		directions.add(List.of(new Point(0, 0), new Point(1, -1), new Point(2, -2), new Point(3, -3)));
		directions.add(List.of(new Point(0, 0), new Point(-1, 1), new Point(-2, 2), new Point(-3, 3)));
		directions.add(List.of(new Point(0, 0), new Point(-1, -1), new Point(-2, -2), new Point(-3, -3)));

		return directions;
	}
}
