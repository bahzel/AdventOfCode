package solution.y2024;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day12_1 extends GridSolution<Character> {
	public static void main(String[] args) {
		new Day12_1().solve();
	}

	@Override
	protected GridElement<Character> transformCell(char ch, int x, int y) {
		return new GridElement<>(ch, x, y);
	}

	@Override
	protected String computeSolution() {
		return stream().mapToLong(this::computeCost).sum() + "";
	}

	private long computeCost(GridElement<Character> plot) {
		if (plot.getValue() == 0) {
			return 0;
		}

		var region = new HashSet<GridElement<Character>>();
		Queue<GridElement<Character>> queue = new LinkedList<>();
		queue.add(plot);
		while (!queue.isEmpty()) {
			GridElement<Character> neighbour = queue.remove();
			if (neighbour.getValue() == plot.getValue() && region.add(neighbour)) {
				queue.addAll(neighbour.getBorderingNeighbours());
			}
		}

		var area = region.size();
		var perimeter = region.stream()
							  .mapToLong(tile -> tile.getBorderingNeighbours()
													 .stream()
													 .filter(neighbour -> neighbour.getValue() != plot.getValue())
													 .count() + 4 - tile.getBorderingNeighbours().size())
							  .sum();
		region.forEach(tile -> tile.setValue((char) 0));
		return area * perimeter;
	}
}
