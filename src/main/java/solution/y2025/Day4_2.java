package solution.y2025;

import utils.soution.GridElement;

public class Day4_2 extends Day4_1 {
	public static void main(String[] args) {
		new Day4_2().solve();
	}

	@Override
	protected String computeSolution() {
		var solution = 0;
		var found = true;

		while (found) {
			var accessableRolls = stream()	.filter(GridElement::getValue)
											.filter(neighbour -> neighbour	.getAllNeighbours()
																			.stream()
																			.filter(GridElement::getValue)
																			.count() < 4)
											.toList();
			found = !accessableRolls.isEmpty();
			solution += accessableRolls.size();
			accessableRolls.forEach(neighbour -> neighbour.setValue(false));
		}

		return solution + "";
	}
}
