package solution.y2018;

import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day18_1 extends GridSolution<LandScape> {
	public static void main(String[] args) {
		new Day18_1().solve();
	}

	@Override
	protected GridElement<LandScape> transformCell(char ch, int x, int y) {
		return switch (ch) {
		case '.' -> new GridElement<>(LandScape.OPEN_GROUND, x, y);
		case '|' -> new GridElement<>(LandScape.TREE, x, y);
		case '#' -> new GridElement<>(LandScape.LUMBERYARD, x, y);
		default -> throw new IllegalArgumentException("Invalid character: " + ch);
		};
	}

	@Override
	protected String computeSolution() {
		for (int i = 0; i < 10; i++) {
			computeNextMinute();
		}

		return stream().filter(neighbour -> neighbour.getValue() == LandScape.TREE).count()
				* stream().filter(neighbour -> neighbour.getValue() == LandScape.LUMBERYARD).count() + "";
	}

	private void computeNextMinute() {
		stream().forEach(acre -> {
			switch (acre.getValue()) {
			case LandScape.OPEN_GROUND:
				acre.setNextValue(countNeighboursWithLandscape(acre, LandScape.TREE) >= 3 ? LandScape.TREE
						: LandScape.OPEN_GROUND);
				break;
			case LandScape.TREE:
				acre.setNextValue(countNeighboursWithLandscape(acre, LandScape.LUMBERYARD) >= 3 ? LandScape.LUMBERYARD
						: LandScape.TREE);
				break;
			case LandScape.LUMBERYARD:
				acre.setNextValue(countNeighboursWithLandscape(acre, LandScape.LUMBERYARD) >= 1
						&& countNeighboursWithLandscape(acre, LandScape.TREE) >= 1 ? LandScape.LUMBERYARD
								: LandScape.OPEN_GROUND);
				break;
			}
		});
		stream().forEach(GridElement::assignNextValue);
	}

	private long countNeighboursWithLandscape(GridElement<LandScape> acre, LandScape landscape) {
		return acre.getAllNeighbours().stream().filter(neighbour -> neighbour.getValue() == landscape).count();
	}
}

enum LandScape {
	OPEN_GROUND, TREE, LUMBERYARD
}
