package solution.y2023;

import java.util.HashSet;
import java.util.PriorityQueue;

import utils.Direction;
import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day17_1 extends GridSolution<Integer> {
	public static void main(String[] args) {
		new Day17_1().solve();
	}

	@Override
	protected GridElement<Integer> transformCell(char ch, int x, int y) {
		return new GridElement<>(Integer.parseInt(String.valueOf(ch)), x, y);
	}

	@Override
	protected String computeSolution() {
		var cache = new HashSet<Step>();

		var queue = new PriorityQueue<Step>();
		queue.add(new Step(0, grid[0][0], Direction.DOWN));
		queue.add(new Step(0, grid[0][0], Direction.RIGHT));

		var goal = grid[grid.length - 1][grid[0].length - 1];
		while (!queue.isEmpty()) {
			var step = queue.poll();
			if (!cache.add(step)) {
				continue;
			}
			println(step.heatLoss());

			if (step.position() == goal) {
				return step.heatLoss() + "";
			}

			var newDirection = step.direction().turn(Direction.LEFT);
			var nextStep = step.position().getNeighbour(newDirection);
			var heatLoss = step.heatLoss();
			for (var i = 0; i < 3 && nextStep != null; i++) {
				heatLoss += nextStep.getValue();
				queue.add(new Step(heatLoss, nextStep, newDirection));
				nextStep = nextStep.getNeighbour(newDirection);
			}

			newDirection = step.direction().turn(Direction.RIGHT);
			nextStep = step.position().getNeighbour(newDirection);
			heatLoss = step.heatLoss();
			for (var i = 0; i < 3 && nextStep != null; i++) {
				heatLoss += nextStep.getValue();
				queue.add(new Step(heatLoss, nextStep, newDirection));
				nextStep = nextStep.getNeighbour(newDirection);
			}
		}

		throw new IllegalArgumentException("Invalid solution");
	}
}

record Step(int heatLoss, GridElement<Integer> position, Direction direction) implements Comparable<Step> {
	@Override
	public int compareTo(Step o) {
		return heatLoss - o.heatLoss;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Step other)) {
			return false;
		}

		return other.position == this.position && other.direction == this.direction;
	}
}