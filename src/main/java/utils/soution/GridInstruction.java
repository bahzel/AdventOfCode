package utils.soution;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public abstract class GridInstruction<Element, Instruction> extends Solution {
	private GridElement<Element>[][] grid;
	private List<Instruction> instructions;

	protected GridInstruction() {
		super();
	}

	@Override
	protected void initialize() {
		grid = new GridElement[getWidth()][getHeigth()];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				grid[i][j] = new GridElement<>(initialValue(), i, j);
			}
		}
		initializeNeighbours();
		instructions = getInstructions(input).stream().map(this::transformInstruction).toList();
	}

	protected abstract int getWidth();

	protected abstract int getHeigth();

	protected abstract Element initialValue();

	protected void initializeNeighbours() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (i > 0) {
					grid[i][j].setLeftNeighbour(grid[i - 1][j]);
				}
				if (i < grid.length - 1) {
					grid[i][j].setRightNeighbour(grid[i + 1][j]);
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

	protected List<String> getInstructions(List<String> instructions) {
		return instructions;
	}

	protected abstract Instruction transformInstruction(String instruction);

	@Override
	public String doSolve() {
		instructions.forEach(instruction -> performInstruction(instruction, grid));
		return getSolution(grid);
	}

	protected abstract void performInstruction(Instruction instruction, GridElement<Element>[][] grid);

	protected abstract String getSolution(GridElement<Element>[][] grid);

	public Stream<GridElement<Element>> stream() {
		return Arrays.stream(grid).flatMap(Arrays::stream);
	}
}
