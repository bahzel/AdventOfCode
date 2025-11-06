package solution.y2023;

import java.util.List;
import java.util.function.BiFunction;

import org.apache.commons.lang3.tuple.Pair;

import utils.Direction;
import utils.soution.GridElement;

public class Day16_2 extends Day16_1 {
	public static void main(String[] args) {
		new Day16_2().solve();
	}

	@Override
	protected String computeSolution() {
		var maxValue = 0L;
		for (GridElement<BiFunction<GridElement<?>, Direction, List<Pair<GridElement<?>, Direction>>>>[] gridElements : grid) {
			maxValue = Math.max(maxValue, countIlluminatedTiles(gridElements[0], Direction.DOWN));
			maxValue = Math.max(maxValue, countIlluminatedTiles(gridElements[gridElements.length - 1], Direction.UP));
		}
		for (var i = 0; i < grid[0].length; i++) {
			maxValue = Math.max(maxValue, countIlluminatedTiles(grid[0][i], Direction.RIGHT));
			maxValue = Math.max(maxValue, countIlluminatedTiles(grid[grid.length - 1][i], Direction.LEFT));
		}
		return maxValue + "";
	}
}