package solution.y2019;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import utils.soution.Solution;

public class Day24_2 extends Solution {
	public static void main(String[] args) {
		new Day24_2().solve();
	}

	private void computeRound(List<GridElement> grid) {
		grid.forEach(tile -> {
			var neighbouringBugs = tile.getNeighbours().stream().filter(GridElement::isValue).count();
			tile.setNextValue(neighbouringBugs == 1 || !tile.isValue() && neighbouringBugs == 2);
		});
		grid.forEach(GridElement::assignNextValue);
	}

	@Override
	protected String doSolve() {
		var grid = createGrid();
		for (int i = 0; i < 200; i++) {
			computeRound(grid);
		}
		return grid.stream().filter(GridElement::isValue).count() + "";
	}

	private List<GridElement> createGrid() {
		var grid = new ArrayList<GridElement>();
		GridElement[][] level = null;
		for (int i = -100; i <= 100; i++) {

			GridElement[][] nextLevel = new GridElement[5][5];
			for (int x = 0; x < 5; x++) {
				for (int y = 0; y < 5; y++) {
					if (x == 2 && y == 2 && i != -100) {
						continue;
					}
					var element = new GridElement(i == 0 && input.get(y).charAt(x) == '#');
					grid.add(element);
					nextLevel[x][y] = element;
				}
			}

			for (int x = 0; x < 5; x++) {
				for (int y = 0; y < 5; y++) {
					if (x > 0 && !((x == 2 || x == 3) && y == 2 && i > -100)) {
						nextLevel[x][y].addNeighbour(nextLevel[x - 1][y]);
						nextLevel[x - 1][y].addNeighbour(nextLevel[x][y]);
					}
					if (y > 0 && !((y == 2 || y == 3) && x == 2 && i > -100)) {
						nextLevel[x][y].addNeighbour(nextLevel[x][y - 1]);
						nextLevel[x][y - 1].addNeighbour(nextLevel[x][y]);
					}
				}
			}

			if (i > -100) {
				nextLevel[1][2].addNeighbour(level[0][0]);
				level[0][0].addNeighbour(nextLevel[1][2]);
				nextLevel[1][2].addNeighbour(level[0][1]);
				level[0][1].addNeighbour(nextLevel[1][2]);
				nextLevel[1][2].addNeighbour(level[0][2]);
				level[0][2].addNeighbour(nextLevel[1][2]);
				nextLevel[1][2].addNeighbour(level[0][3]);
				level[0][3].addNeighbour(nextLevel[1][2]);
				nextLevel[1][2].addNeighbour(level[0][4]);
				level[0][4].addNeighbour(nextLevel[1][2]);

				nextLevel[3][2].addNeighbour(level[4][0]);
				level[4][0].addNeighbour(nextLevel[3][2]);
				nextLevel[3][2].addNeighbour(level[4][1]);
				level[4][1].addNeighbour(nextLevel[3][2]);
				nextLevel[3][2].addNeighbour(level[4][2]);
				level[4][2].addNeighbour(nextLevel[3][2]);
				nextLevel[3][2].addNeighbour(level[4][3]);
				level[4][3].addNeighbour(nextLevel[3][2]);
				nextLevel[3][2].addNeighbour(level[4][4]);
				level[4][4].addNeighbour(nextLevel[3][2]);

				nextLevel[2][1].addNeighbour(level[0][0]);
				level[0][0].addNeighbour(nextLevel[2][1]);
				nextLevel[2][1].addNeighbour(level[1][0]);
				level[1][0].addNeighbour(nextLevel[2][1]);
				nextLevel[2][1].addNeighbour(level[2][0]);
				level[2][0].addNeighbour(nextLevel[2][1]);
				nextLevel[2][1].addNeighbour(level[3][0]);
				level[3][0].addNeighbour(nextLevel[2][1]);
				nextLevel[2][1].addNeighbour(level[4][0]);
				level[4][0].addNeighbour(nextLevel[2][1]);

				nextLevel[2][3].addNeighbour(level[0][4]);
				level[0][4].addNeighbour(nextLevel[2][3]);
				nextLevel[2][3].addNeighbour(level[1][4]);
				level[1][4].addNeighbour(nextLevel[2][3]);
				nextLevel[2][3].addNeighbour(level[2][4]);
				level[2][4].addNeighbour(nextLevel[2][3]);
				nextLevel[2][3].addNeighbour(level[3][4]);
				level[3][4].addNeighbour(nextLevel[2][3]);
				nextLevel[2][3].addNeighbour(level[4][4]);
				level[4][4].addNeighbour(nextLevel[2][3]);
			}

			level = nextLevel;
		}

		return grid;
	}
}

class GridElement {
	@Getter
	private boolean value;
	@Setter
	private boolean nextValue;
	@Getter
	private final List<GridElement> neighbours = new ArrayList<>();

	public GridElement(boolean value) {
		this.value = value;
	}

	public void assignNextValue() {
		value = nextValue;
	}

	public void addNeighbour(GridElement neighbour) {
		neighbours.add(neighbour);
	}
}
