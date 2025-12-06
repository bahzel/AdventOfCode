package solution.y2025;

import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day4_1 extends GridSolution<Boolean> {
	public static void main(String[] args) {
		new Day4_1().solve();
	}

	@Override
	protected GridElement<Boolean> transformCell(char ch, int x, int y) {
		return switch (ch) {
		case '@' -> new GridElement<>(true, x, y);
		case '.' -> new GridElement<>(false, x, y);
		default -> throw new IllegalArgumentException("Invalid character " + ch);
		};
	}

	@Override
	protected String computeSolution() {
		return stream()	.filter(GridElement::getValue)
						.map(GridElement::getAllNeighbours)
						.filter(neighbours -> neighbours.stream().filter(GridElement::getValue).count() < 4)
						.count()
				+ "";
	}
}
