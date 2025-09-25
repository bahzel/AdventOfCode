package solution.y2024;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;
import utils.Direction;
import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day16_2 extends GridSolution<Boolean> {
	private GridElement<Boolean> goal;
	private GridElement<Boolean> start;

	public static void main(String[] args) {
		new Day16_2().solve();
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
		var cache = new HashMap<Pair<GridElement<Boolean>, Direction>, Long>();
		var bestPath = Long.MAX_VALUE;
		var tilesAtBestPath = new HashSet<GridElement<Boolean>>();

		Queue<StepWithPath> queue = new PriorityQueue<>();
		queue.add(new StepWithPath(0L, List.of(start), Direction.RIGHT));
		while (!queue.isEmpty()) {
			var currentStep = queue.poll();

			if (currentStep.getSteps() > bestPath) {
				break;
			}

			if (cache.computeIfAbsent(Pair.of(currentStep.getPosition(), currentStep.getDirection()),
					entry -> currentStep.getSteps()) < currentStep.getSteps()) {
				continue;
			}

			if (currentStep.getPosition() == goal) {
				bestPath = currentStep.getSteps();
				tilesAtBestPath.addAll(currentStep.getPath());
				continue;
			}

			var leftDirection = currentStep.getDirection().turn(Direction.LEFT);
			var leftNeighbour = currentStep.getPosition().getNeighbour(leftDirection);
			if (leftNeighbour.getValue()) {
				var path = new ArrayList<>(currentStep.getPath());
				path.add(leftNeighbour);
				queue.add(new StepWithPath(currentStep.getSteps() + 1001L, path, leftDirection));
			}

			var straightDirection = currentStep.getDirection();
			var straightNeighbour = currentStep.getPosition().getNeighbour(straightDirection);
			if (straightNeighbour.getValue()) {
				var path = new ArrayList<>(currentStep.getPath());
				path.add(straightNeighbour);
				queue.add(new StepWithPath(currentStep.getSteps() + 1L, path, straightDirection));
			}

			var rightDirection = currentStep.getDirection().turn(Direction.RIGHT);
			var rightNeighbour = currentStep.getPosition().getNeighbour(rightDirection);
			if (rightNeighbour.getValue()) {
				var path = new ArrayList<>(currentStep.getPath());
				path.add(rightNeighbour);
				queue.add(new StepWithPath(currentStep.getSteps() + 1001L, path, rightDirection));
			}
		}

		return tilesAtBestPath.size() + "";
	}
}

@AllArgsConstructor
@Getter
class StepWithPath implements Comparable<StepWithPath> {
	private final long steps;
	private final List<GridElement<Boolean>> path;
	private final Direction direction;

	public GridElement<Boolean> getPosition() {
		return path.getLast();
	}

	@Override
	public int compareTo(StepWithPath o) {
		return (int) (steps - o.steps);
	}
}
