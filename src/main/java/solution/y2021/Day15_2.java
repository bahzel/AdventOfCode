package solution.y2021;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day15_2 extends GridSolution<Integer> {
	public static void main(String[] args) {
		new Day15_2().solve();
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		var input = new StringBuilder();
		for (var yRepeat = 0; yRepeat < 5; yRepeat++) {
			for (var y = 0; y < instructions.size(); y++) {
				for (var xRepeat = 0; xRepeat < 5; xRepeat++) {
					for (var x = 0; x < instructions.getFirst().length(); x++) {
						var value = Integer.parseInt(instructions.get(y).charAt(x) + "") + yRepeat + xRepeat;
						while (value > 9) {
							value -= 9;
						}
						input.append(value);
					}
				}
				input.append("\n");
			}
		}
		return Arrays.asList(input.toString().split("\n"));
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
