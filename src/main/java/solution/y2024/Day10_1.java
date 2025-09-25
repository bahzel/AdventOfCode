package solution.y2024;

import java.util.HashSet;
import java.util.LinkedList;

import utils.Point;
import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day10_1 extends GridSolution<Integer> {
	public static void main(String[] args) {
		new Day10_1().solve();
	}

	@Override
	protected GridElement<Integer> transformCell(char ch, int x, int y) {
		return new GridElement<>(Integer.parseInt(ch + ""), x, y);
	}

	@Override
	protected String computeSolution() {
		return stream().filter(tile -> tile.getValue() == 0).mapToInt(this::computeHikingScore).sum() + "";
	}

	private int computeHikingScore(GridElement<Integer> trailHead) {
		var reachedSummits = new HashSet<Point>();
		var queue = new LinkedList<GridElement<Integer>>();
		queue.add(trailHead);

		while (!queue.isEmpty()) {
			var current = queue.remove();
			if (current.getValue() == 9) {
				reachedSummits.add(current.getCoordinates());
				continue;
			}

			current	.getBorderingNeighbours()
					.stream()
					.filter(neighbour -> neighbour.getValue() == current.getValue() + 1)
					.forEach(queue::add);
		}

		return reachedSummits.size();
	}
}
