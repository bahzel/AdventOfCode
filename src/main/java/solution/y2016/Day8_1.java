package solution.y2016;

import lombok.AllArgsConstructor;
import utils.soution.GridElement;
import utils.soution.GridInstruction;

import java.util.function.Consumer;

public class Day8_1 extends GridInstruction<Boolean, PixelRotation> {
	public static void main(String[] args) {
		new Day8_1().solve();
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
		return stream().filter(GridElement::getValue).count() + "";
	}
}

abstract class PixelRotation implements Consumer<GridElement<Boolean>[][]> {
	public static PixelRotation build(String instruction) {
		if (instruction.startsWith("rect")) {
			var instructions = instruction.split(" ")[1].split("x");
			return new RectangleRotation(Integer.parseInt(instructions[0]), Integer.parseInt(instructions[1]));
		} else if (instruction.startsWith("rotate row")) {
			var instructions = instruction.split("y=")[1].split(" by ");
			return new RowRotation(Integer.parseInt(instructions[0]), Integer.parseInt(instructions[1]));
		} else if (instruction.startsWith("rotate column")) {
			var instructions = instruction.split("x=")[1].split(" by ");
			return new ColumnRotation(Integer.parseInt(instructions[0]), Integer.parseInt(instructions[1]));
		} else {
			throw new IllegalArgumentException("Invalid instruction: " + instruction);
		}
	}
}

@AllArgsConstructor
class RectangleRotation extends PixelRotation {
	private final int width, height;

	@Override
	public void accept(GridElement<Boolean>[][] gridElements) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				gridElements[i][j].setValue(true);
			}
		}
	}
}

@AllArgsConstructor
class ColumnRotation extends PixelRotation {
	private final int row, amount;

	@Override
	public void accept(GridElement<Boolean>[][] gridElements) {
		for (int i = 0; i < amount; i++) {
			rotate(gridElements);
		}
	}

	private void rotate(GridElement<Boolean>[][] gridElements) {
		boolean lastCell = gridElements[row][gridElements[0].length - 1].getValue();
		for (int i = gridElements[0].length - 1; i > 0; i--) {
			gridElements[row][i].setValue(gridElements[row][i - 1].getValue());
		}
		gridElements[row][0].setValue(lastCell);
	}
}

@AllArgsConstructor
class RowRotation extends PixelRotation {
	private final int column, amount;

	@Override
	public void accept(GridElement<Boolean>[][] gridElements) {
		for (int i = 0; i < amount; i++) {
			rotate(gridElements);
		}
	}

	private void rotate(GridElement<Boolean>[][] gridElements) {
		boolean lastCell = gridElements[gridElements.length - 1][column].getValue();
		for (int i = gridElements.length - 1; i > 0; i--) {
			gridElements[i][column].setValue(gridElements[i - 1][column].getValue());
		}
		gridElements[0][column].setValue(lastCell);
	}
}
