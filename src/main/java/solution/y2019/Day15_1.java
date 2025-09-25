package solution.y2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;
import utils.Direction;
import utils.soution.GridElement;
import utils.soution.MapSolution;

public class Day15_1 extends MapSolution<IntCodeInterpreter> {
	private GridElement<Boolean> oxygenLocation = null;
	private Thread thread = null;

	public static void main(String[] args) {
		new Day15_1().solve();
	}

	@Override
	protected IntCodeInterpreter initializeMapping() {
		return new IntCodeInterpreter();
	}

	@Override
	@SneakyThrows
	protected void transformInstruction(String instruction, IntCodeInterpreter intCodeInterpreter) {
		var register = new ArrayList<>(
				Arrays.stream(input.getFirst().split(",")).mapToLong(Long::parseLong).boxed().toList());
		thread = new Thread(() -> intCodeInterpreter.withRegister(register).performComputation());
		thread.start();
	}

	@Override
	protected String computeSolution(IntCodeInterpreter intCodeInterpreter) {
		var startingLocation = new GridElement<>(true, 0, 0);
		var currentLocation = startingLocation;
		GridElement<Boolean> currentGoal = getClosestUnknownTile(currentLocation);
		while (currentGoal != null) {
			while (currentLocation != currentGoal) {
				currentLocation = computeRound(intCodeInterpreter, currentLocation,
						moveToward(currentLocation, currentGoal));
			}
			currentLocation = testBorder(intCodeInterpreter, currentLocation);
			currentGoal = getClosestUnknownTile(currentLocation);
		}

		thread.interrupt();
		return computeDistance(startingLocation, oxygenLocation) + "";
	}

	private GridElement<Boolean> getClosestUnknownTile(GridElement<Boolean> currentLocation) {
		if (currentLocation.getBorderingNeighbours().size() < 4) {
			return currentLocation;
		}

		var cache = new HashSet<GridElement<Boolean>>();
		cache.add(currentLocation);
		Queue<GridElement<Boolean>> queue = new LinkedList<>(currentLocation.getBorderingNeighbours());
		while (!queue.isEmpty()) {
			GridElement<Boolean> neighbour = queue.remove();
			if (!neighbour.getValue() || !cache.add(neighbour)) {
				continue;
			}

			if (neighbour.getBorderingNeighbours().size() < 4) {
				return neighbour;
			}
			queue.addAll(neighbour.getBorderingNeighbours());
		}

		return null;
	}

	private Direction moveToward(GridElement<Boolean> currentLocation, GridElement<Boolean> goal) {
		var cache = new HashSet<GridElement<Boolean>>();
		Queue<GridElement<Boolean>> queue = new LinkedList<>();
		queue.add(goal);
		while (!queue.isEmpty()) {
			GridElement<Boolean> neighbour = queue.remove();
			if (!neighbour.getValue() || !cache.add(neighbour)) {
				continue;
			}

			if (neighbour.getUpperNeighbour() == currentLocation) {
				return Direction.DOWN;
			} else if (neighbour.getRightNeighbour() == currentLocation) {
				return Direction.LEFT;
			} else if (neighbour.getLowerNeighbour() == currentLocation) {
				return Direction.UP;
			} else if (neighbour.getLeftNeighbour() == currentLocation) {
				return Direction.RIGHT;
			}
			queue.addAll(neighbour.getBorderingNeighbours());
		}

		throw new IllegalStateException();
	}

	private int computeDistance(GridElement<Boolean> currentLocation, GridElement<Boolean> goal) {
		var cache = new HashSet<GridElement<Boolean>>();
		Queue<Pair<GridElement<Boolean>, Integer>> queue = new LinkedList<>();
		queue.add(Pair.of(currentLocation, 0));
		while (!queue.isEmpty()) {
			var next = queue.remove();
			if (!next.getLeft().getValue() || !cache.add(next.getLeft())) {
				continue;
			}

			if (next.getLeft() == goal) {
				return next.getRight();
			}

			next.getLeft()
				.getBorderingNeighbours()
				.forEach(neighbour -> queue.add(Pair.of(neighbour, next.getRight() + 1)));
		}

		throw new IllegalStateException();
	}

	private GridElement<Boolean> testBorder(IntCodeInterpreter intCodeInterpreter,
			GridElement<Boolean> currentLocation) {
		if (currentLocation.getUpperNeighbour() == null) {
			return computeRound(intCodeInterpreter, currentLocation, Direction.UP);
		} else if (currentLocation.getRightNeighbour() == null) {
			return computeRound(intCodeInterpreter, currentLocation, Direction.RIGHT);
		} else if (currentLocation.getLowerNeighbour() == null) {
			return computeRound(intCodeInterpreter, currentLocation, Direction.DOWN);
		} else if (currentLocation.getLeftNeighbour() == null) {
			return computeRound(intCodeInterpreter, currentLocation, Direction.LEFT);
		}
		throw new IllegalStateException();
	}

	@SneakyThrows
	private GridElement<Boolean> computeRound(IntCodeInterpreter intCodeInterpreter,
			GridElement<Boolean> currentLocation, Direction direction) {
		intCodeInterpreter.getInput().add(mapDirection(direction));
		long output = intCodeInterpreter.getOutput().take();
		if (output == 0L) {
			currentLocation.setNeighbour(direction, false);
			return currentLocation;
		} else if (output == 1L) {
			if (currentLocation.getNeighbour(direction) == null) {
				return currentLocation.setNeighbour(direction, true);
			} else {
				return currentLocation.getNeighbour(direction);
			}
		} else if (output == 2L) {
			if (currentLocation.getNeighbour(direction) == null) {
				oxygenLocation = currentLocation.setNeighbour(direction, true);
				return oxygenLocation;
			} else {
				return currentLocation.getNeighbour(direction);
			}
		} else {
			throw new IllegalStateException();
		}
	}

	private long mapDirection(Direction direction) {
		return switch (direction) {
			case UP -> 1L;
			case DOWN -> 2L;
			case LEFT -> 3L;
			case RIGHT -> 4L;
		};
	}
}
