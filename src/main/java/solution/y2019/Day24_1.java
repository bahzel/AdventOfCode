package solution.y2019;

import java.util.HashSet;

import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day24_1 extends GridSolution<Boolean> {
	public static void main(String[] args) {
		new Day24_1().solve();
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
		var cache = new HashSet<Long>();
		while (true) {
			computeRound();
			var score = computeScore();
			if (!cache.add(score)) {
				return score + "";
			}
		}
	}

	private void computeRound() {
		stream().forEach(tile -> {
			var neighbouringBugs = tile.getBorderingNeighbours().stream().filter(GridElement::getValue).count();
			tile.setNextValue(neighbouringBugs == 1 || !tile.getValue() && neighbouringBugs == 2);
		});
		stream().forEach(GridElement::assignNextValue);
	}

	private long computeScore() {
		return stream()	.filter(GridElement::getValue)
						.mapToLong(tile -> (long) Math.pow(2,
								tile.getCoordinates().getY() * grid.length + tile.getCoordinates().getX()))
						.sum();
	}
}
