package solution.y2016;

import utils.GridElement;
import utils.GridInstruction;

public class Day8_2 extends GridInstruction<Boolean, PixelRotation> {
	public static void main(String[] args) {
		new Day8_2().solve();
	}

	@Override
	protected int getWidth() {
		return 50;
	}

	@Override
	protected int getHeigth() {
		return 6;
	}

	@Override
	protected Boolean initialValue() {
		return false;
	}

	@Override
	protected PixelRotation transformInstruction(String instruction) {
		return PixelRotation.build(instruction);
	}

	@Override
	protected void performInstruction(PixelRotation pixelRotation, GridElement<Boolean>[][] grid) {
		pixelRotation.accept(grid);
	}

	@Override
	protected String getSolution(GridElement<Boolean>[][] grid) {
		var solution = new StringBuilder();
		for (int i = 0; i < grid[0].length; i++) {
			for (GridElement<Boolean>[] gridElements : grid) {
				solution.append(gridElements[i].getValue() ? "X" : " ");
			}
			solution.append("\n");
		}
		return solution.toString();
	}
}
