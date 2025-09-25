package solution.y2018;

import java.util.HashSet;

import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day18_2 extends GridSolution<LandScape> {
	private final int MINUTES = 1000000000;

	public static void main(String[] args) {
		new Day18_2().solve();
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
		var cache = new HashSet<Long>();

		var i = 0;
		var firstCacheHit = 0;
		long firstCacheValue = 0;
		for (; i < MINUTES; i++) {
			var resourceValue = computeResourceValue();
			if (!cache.add(resourceValue)) {
				if (firstCacheHit == 0 || firstCacheValue != resourceValue) {
					firstCacheHit = i;
					firstCacheValue = resourceValue;
				} else {
					var cycleSize = i - firstCacheHit;
					i += cycleSize * ((MINUTES - i) / cycleSize);
				}
				cache.clear();
			}

			computeNextMinute();
		}

		return computeResourceValue() + "";
	}

	private long computeResourceValue() {
		return stream().filter(neighbour -> neighbour.getValue() == LandScape.TREE).count()
				* stream().filter(neighbour -> neighbour.getValue() == LandScape.LUMBERYARD).count();
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
