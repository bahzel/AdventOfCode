package solution.y2021;

import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day11_1 extends GridSolution<Integer> {
	public static void main(String[] args) {
		new Day11_1().solve();
	}

	@Override
	protected GridElement<Integer> transformCell(char ch, int x, int y) {
		return new GridElement<>(Integer.parseInt(ch + ""), x, y);
	}

	@Override
	protected String computeSolution() {
		var count = 0;

		for (var i = 0; i < 100; i++) {
			stream().forEach(octopus -> octopus.setValue(octopus.getValue() + 1));
			var energizedOctopuses = stream().filter(octopus -> octopus.getValue() == 10).toList();
			while (!energizedOctopuses.isEmpty()) {
				count += energizedOctopuses.size();
				energizedOctopuses.forEach(octopus -> {
					octopus.getAllNeighbours().forEach(neighbour -> neighbour.setValue(neighbour.getValue() + 1));
					octopus.setNextValue(1);
				});
				energizedOctopuses = stream().filter(
						octopus -> octopus.getValue() >= 10 && octopus.getNextValue() == null).toList();
			}
			stream().filter(octopus -> octopus.getNextValue() != null).forEach(octopus -> {
				octopus.setValue(0);
				octopus.setNextValue(null);
			});
		}

		return count + "";
	}
}
