package solution.y2024;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.Direction;
import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day16_1 extends GridSolution<Boolean> {
	private GridElement<Boolean> goal;
	private GridElement<Boolean> start;

	public static void main(String[] args) {
		new Day16_1().solve();
	}

	@Override
	protected GridElement<Boolean> transformCell(char ch, int x, int y) {
		return switch (ch) {
		case '#' -> new GridElement<>(false, x, y);
		case '.' -> new GridElement<>(true, x, y);
		case 'E' -> {
			goal = new GridElement<>(true, x, y);
			yield goal;
		}
		case 'S' -> {
			start = new GridElement<>(true, x, y);
			yield start;
		}
		default -> throw new IllegalArgumentException("Unknown character: " + ch);
		};
	}

	@Override
	protected String computeSolution() {
		var cache = new HashSet<GridElement<Boolean>>();

		Queue<Step> queue = new PriorityQueue<>();
		queue.add(new Step(0L, start, Direction.RIGHT));
		while (!queue.isEmpty()) {
			var currentStep = queue.poll();
			if (!cache.add(currentStep.getPosition())) {
				continue;
			}

			if (currentStep.getPosition() == goal) {
				return currentStep.getSteps() + "";
			}

			var leftDirection = currentStep.getDirection().turn(Direction.LEFT);
			var leftNeighbour = currentStep.getPosition().getNeighbour(leftDirection);
			if (leftNeighbour.getValue()) {
				queue.add(new Step(currentStep.getSteps() + 1001L, leftNeighbour, leftDirection));
			}

			var straightDirection = currentStep.getDirection();
			var straightNeighbour = currentStep.getPosition().getNeighbour(straightDirection);
			if (straightNeighbour.getValue()) {
				queue.add(new Step(currentStep.getSteps() + 1L, straightNeighbour, straightDirection));
			}

			var rightDirection = currentStep.getDirection().turn(Direction.RIGHT);
			var rightNeighbour = currentStep.getPosition().getNeighbour(rightDirection);
			if (rightNeighbour.getValue()) {
				queue.add(new Step(currentStep.getSteps() + 1001L, rightNeighbour, rightDirection));
			}
		}

		throw new IllegalStateException();
	}
}

@AllArgsConstructor
@Getter
class Step implements Comparable<Step> {
	private final long steps;
	private final GridElement<Boolean> position;
	private final Direction direction;

	@Override
	public int compareTo(Step o) {
		return (int) (steps - o.steps);
	}
}
