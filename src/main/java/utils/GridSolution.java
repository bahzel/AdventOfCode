package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public abstract class GridSolution<T> extends Solution {
	protected final GridElement<T>[][] grid;

	protected GridSolution() {
		super();
		grid = new GridElement[input.size()][input.getFirst().length()];
	}

	@Override
	protected void initialize() {
		initializeGrid();
	}

	@Override
	public String doSolve() {
		return computeSolution();
	}

	private void initializeGrid() {
		for (int i = 0; i < input.size(); i++) {
			var row = input.get(i);
			for (int j = 0; j < row.length(); j++) {
				grid[i][j] = transformCell(row.charAt(j));
			}
		}

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				var neighbours = new ArrayList<GridElement<T>>();
				if (i > 0) {
					if (j > 0) {
						neighbours.add(grid[i - 1][j - 1]);
					}
					neighbours.add(grid[i - 1][j]);
					if (j < grid[0].length - 1) {
						neighbours.add(grid[i - 1][j + 1]);
					}
				}
				if (i < grid.length - 1) {
					if (j > 0) {
						neighbours.add(grid[i + 1][j - 1]);
					}
					neighbours.add(grid[i + 1][j]);
					if (j < grid[0].length - 1) {
						neighbours.add(grid[i + 1][j + 1]);
					}
				}
				if (j > 0) {
					neighbours.add(grid[i][j - 1]);
				}
				if (j < grid[0].length - 1) {
					neighbours.add(grid[i][j + 1]);
				}
				grid[i][j].setNeighbours(neighbours);
			}
		}
	}

	public Stream<GridElement<T>> stream() {
		return Arrays.stream(grid).flatMap(Arrays::stream);
	}

	public void assignNextValue() {
		stream().forEach(GridElement::assignNextValue);
	}

	protected abstract GridElement<T> transformCell(char ch);

	protected abstract String computeSolution();
}
