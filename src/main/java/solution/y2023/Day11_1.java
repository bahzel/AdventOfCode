package solution.y2023;

import java.util.ArrayList;
import java.util.List;

import utils.Point;
import utils.soution.Solution;

public class Day11_1 extends Solution {
	public static void main(String[] args) {
		new Day11_1().solve();
	}

	@Override
	protected String doSolve() {
		var emptyRows = getEmptyRows();
		var emptyColumns = getEmptyColumns();
		var galaxies = new ArrayList<Point>();

		for (var x = 0; x < input.getFirst().length(); x++) {
			for (var y = 0; y < input.size(); y++) {
				if (input.get(y).charAt(x) != '#') {
					continue;
				}

				var finalX = x;
				var finalY = y;
				var actualX = x
						+ getAdditionalSize() * (int) emptyColumns.stream().filter(column -> column < finalX).count();
				var actualY = y + getAdditionalSize() * (int) emptyRows.stream().filter(row -> row < finalY).count();
				galaxies.add(new Point(actualX, actualY));
			}
		}

		var solution = 0L;
		for (var i = 0; i < galaxies.size(); i++) {
			for (var j = i + 1; j < galaxies.size(); j++) {
				solution += galaxies.get(i).computeManhattanDistance(galaxies.get(j));
			}
		}

		return solution + "";
	}

	protected int getAdditionalSize() {
		return 1;
	}

	private List<Integer> getEmptyRows() {
		var emptyRows = new ArrayList<Integer>();
		for (var i = 0; i < input.size(); i++) {
			if (!input.get(i).contains("#")) {
				emptyRows.add(i);
			}
		}
		return emptyRows;
	}

	private List<Integer> getEmptyColumns() {
		var emptyColumns = new ArrayList<Integer>();
		for (var i = 0; i < input.getFirst().length(); i++) {
			var index = i;
			if (input.stream().map(line -> line.substring(index, index + 1)).noneMatch(s -> s.equals("#"))) {
				emptyColumns.add(i);
			}
		}
		return emptyColumns;
	}
}