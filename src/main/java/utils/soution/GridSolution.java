package utils.soution;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public abstract class GridSolution<T> extends Solution {
	protected final GridElement<T>[][] grid;

	protected GridSolution() {
		super();
		input = getInstructions(input);
		grid = new GridElement[input.getFirst().length()][input.size()];
	}

	protected List<String> getInstructions(List<String> instructions) {
		return instructions;
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
				grid[j][i] = transformCell(row.charAt(j), j, i);
			}
		}

		initializeNeighbours();
	}

	protected void initializeNeighbours() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (i > 0) {
					if (j > 0) {
						grid[i][j].getAllNeighbours().add(grid[i - 1][j - 1]);
					}
					grid[i][j].setLeftNeighbour(grid[i - 1][j]);
					if (j < grid[0].length - 1) {
						grid[i][j].getAllNeighbours().add(grid[i - 1][j + 1]);
					}
				}
				if (i < grid.length - 1) {
					if (j > 0) {
						grid[i][j].getAllNeighbours().add(grid[i + 1][j - 1]);
					}
					grid[i][j].setRightNeighbour(grid[i + 1][j]);
					if (j < grid[0].length - 1) {
						grid[i][j].getAllNeighbours().add(grid[i + 1][j + 1]);
					}
				}
				if (j > 0) {
					grid[i][j].setUpperNeighbour(grid[i][j - 1]);
				}
				if (j < grid[0].length - 1) {
					grid[i][j].setLowerNeighbour(grid[i][j + 1]);
				}
			}
		}
	}

	public Stream<GridElement<T>> stream() {
		return Arrays.stream(grid).flatMap(Arrays::stream);
	}

	public void assignNextValue() {
		stream().forEach(GridElement::assignNextValue);
	}

	protected abstract GridElement<T> transformCell(char ch, int x, int y);

	protected abstract String computeSolution();
}
