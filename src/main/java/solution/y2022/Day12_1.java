package solution.y2022;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.lang3.tuple.Pair;

import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day12_1 extends GridSolution<Integer> {
	private GridElement<Integer> start;
	private GridElement<Integer> goal;

	public static void main(String[] args) {
		new Day12_1().solve();
	}

	@Override
	protected GridElement<Integer> transformCell(char ch, int x, int y) {
		if (ch == 'S') {
			start = new GridElement<>((int) 'a', x, y);
			return start;
		} else if (ch == 'E') {
			goal = new GridElement<>((int) 'z', x, y);
			return goal;
		} else {
			return new GridElement<>((int) ch, x, y);
		}
	}

	@Override
	protected String computeSolution() {
		var cache = new HashSet<GridElement<Integer>>();

		Queue<Pair<GridElement<Integer>, Integer>> queue = new LinkedList<>();
		queue.add(Pair.of(start, 0));

		while (!queue.isEmpty()) {
			var currentPosition = queue.poll();
			if (!cache.add(currentPosition.getLeft())) {
				continue;
			}
			if (currentPosition.getLeft() == goal) {
				return String.valueOf(currentPosition.getRight());
			}

			currentPosition.getLeft().getBorderingNeighbours().forEach(neighbour -> {
				if (neighbour.getValue() <= currentPosition.getLeft().getValue() + 1) {
					queue.add(Pair.of(neighbour, currentPosition.getRight() + 1));
				}
			});
		}

		throw new IllegalStateException("No solution found");
	}
}
