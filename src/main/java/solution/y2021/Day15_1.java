package solution.y2021;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day15_1 extends GridSolution<Integer> {
	public static void main(String[] args) {
		new Day15_1().solve();
	}

	@Override
	protected GridElement<Integer> transformCell(char ch, int x, int y) {
		return new GridElement<>(Integer.parseInt(String.valueOf(ch)), x, y);
	}

	@Override
	protected String computeSolution() {
		var goal = grid[grid.length - 1][grid[0].length - 1];
		var cache = new HashSet<GridElement<Integer>>();

		Queue<ChitonStatus> queue = new PriorityQueue<>();
		queue.add(new ChitonStatus(0, grid[0][0]));
		while (!queue.isEmpty()) {
			var current = queue.poll();
			if (current.getCurrentElement() == goal) {
				return current.getCost() + "";
			}

			current.getCurrentElement().getBorderingNeighbours().forEach(neighbour -> {
				if (cache.add(neighbour)) {
					queue.add(new ChitonStatus(current.getCost() + neighbour.getValue(), neighbour));
				}
			});
		}

		throw new IllegalStateException("No solution found");
	}
}

@AllArgsConstructor
@Getter
class ChitonStatus implements Comparable<ChitonStatus> {
	private final int cost;
	private final GridElement<Integer> currentElement;

	@Override
	public int compareTo(ChitonStatus o) {
		return cost - o.cost;
	}
}
