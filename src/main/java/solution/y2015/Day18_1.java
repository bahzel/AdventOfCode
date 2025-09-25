package solution.y2015;

import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day18_1 extends GridSolution<Boolean> {
	public static void main(String[] args) {
		new Day18_1().solve();
	}

	@Override
	protected GridElement<Boolean> transformCell(char ch, int x, int y) {
		return switch (ch) {
			case '#' -> new GridElement<>(true, x, y);
			case '.' -> new GridElement<>(false, x, y);
			default -> throw new IllegalArgumentException("Invalid character: " + ch);
		};
	}

	@Override
	protected String computeSolution() {
		for (int i = 0; i < 100; i++) {
			performStep();
		}

		return stream().filter(GridElement::getValue).count() + "";
	}

	private void performStep() {
		stream().forEach(this::computeNextValue);
		assignNextValue();
	}

	private void computeNextValue(GridElement<Boolean> element) {
		var activeNeighbours = element.getAllNeighbours().stream().filter(GridElement::getValue).count();
		if (element.getValue()) {
			element.setNextValue(activeNeighbours == 2 || activeNeighbours == 3);
		} else {
			element.setNextValue(activeNeighbours == 3);
		}
	}
}
