package solution.y2018;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;
import utils.Point;
import utils.soution.GridElement;
import utils.soution.GridInstruction;

public class Day22_2 extends GridInstruction<Long, String> {
	private final int BORDER = 14;
	private final Set<Pair<Point, Gadget>> CACHE = new HashSet<>();

	public static void main(String[] args) {
		new Day22_2().solve();
	}

	@Override
	protected int getWidth() {
		return Integer.parseInt(input.get(1).split(" ")[1].split(",")[0]) + 1 + BORDER;
	}

	@Override
	protected int getHeigth() {
		return Integer.parseInt(input.get(1).split(" ")[1].split(",")[1]) + 1 + BORDER;
	}

	@Override
	protected Long initialValue() {
		return 0L;
	}

	@Override
	protected String transformInstruction(String instruction) {
		return instruction;
	}

	@Override
	protected void performInstruction(String s, GridElement<Long>[][] grid) {
	}

	@Override
	protected String getSolution(GridElement<Long>[][] grid) {
		var goalString = input.get(1).split(" ")[1].split(",");
		var goal = new Point(Integer.parseInt(goalString[0]), Integer.parseInt(goalString[1]));
		computeCave(grid, goal);
		var queue = new PriorityQueue<Step>();
		queue.add(new Step(grid[0][0], 0, Gadget.TORCH));

		while (!queue.isEmpty()) {
			var step = queue.poll();
			if (!CACHE.add(Pair.of(step.getLocation().getCoordinates(), step.getGadget()))) {
				continue;
			}
			if (step.getLocation().getCoordinates().equals(goal)) {
				if (step.getGadget() != Gadget.TORCH) {
					queue.add(new Step(step.getLocation(), step.getDuration() + 7, Gadget.TORCH));
				} else {
					return step.getDuration() + "";
				}
				continue;
			}

			addNeighbour(step.getLocation().getUpperNeighbour(), step, queue);
			addNeighbour(step.getLocation().getRightNeighbour(), step, queue);
			addNeighbour(step.getLocation().getLeftNeighbour(), step, queue);
			addNeighbour(step.getLocation().getLowerNeighbour(), step, queue);
		}

		throw new IllegalStateException();
	}

	private void addNeighbour(GridElement<Long> neighbour, Step step, Queue<Step> queue) {
		if (neighbour == null) {
			return;
		}

		switch (step.getGadget()) {
		case TORCH:
			if (neighbour.getValue() == 0 || neighbour.getValue() == 2) {
				queue.add(new Step(neighbour, step.getDuration() + 1, Gadget.TORCH));
			} else if (step.getLocation().getValue() == 0) {
				queue.add(new Step(neighbour, step.getDuration() + 8, Gadget.CLIMBING_GEAR));
			} else if (step.getLocation().getValue() == 2) {
				queue.add(new Step(neighbour, step.getDuration() + 8, Gadget.NONE));
			}
			break;
		case CLIMBING_GEAR:
			if (neighbour.getValue() == 0 || neighbour.getValue() == 1) {
				queue.add(new Step(neighbour, step.getDuration() + 1, Gadget.CLIMBING_GEAR));
			} else if (step.getLocation().getValue() == 0) {
				queue.add(new Step(neighbour, step.getDuration() + 8, Gadget.TORCH));
			} else if (step.getLocation().getValue() == 1) {
				queue.add(new Step(neighbour, step.getDuration() + 8, Gadget.NONE));
			}
			break;
		case NONE:
			if (neighbour.getValue() == 1 || neighbour.getValue() == 2) {
				queue.add(new Step(neighbour, step.getDuration() + 1, Gadget.NONE));
			} else if (step.getLocation().getValue() == 1) {
				queue.add(new Step(neighbour, step.getDuration() + 8, Gadget.CLIMBING_GEAR));
			} else if (step.getLocation().getValue() == 2) {
				queue.add(new Step(neighbour, step.getDuration() + 8, Gadget.TORCH));
			}
			break;
		}
	}

	private void computeCave(GridElement<Long>[][] grid, Point goal) {
		var depth = Integer.parseInt(input.getFirst().split(" ")[1]);

		for (var y = 0; y < grid[0].length; y++) {
			for (var x = 0; x < grid.length; x++) {
				long geolocicalIndex;
				if (x == 0 && y == 0) {
					geolocicalIndex = 0;
				} else if (x == goal.getX() && y == goal.getY()) {
					geolocicalIndex = 0;
				} else if (y == 0) {
					geolocicalIndex = x * 16807L;
				} else if (x == 0) {
					geolocicalIndex = y * 48271L;
				} else {
					geolocicalIndex = grid[x - 1][y].getValue() * grid[x][y - 1].getValue();
				}
				grid[x][y].setValue((geolocicalIndex + depth) % 20183);
			}
		}
		stream().forEach(element -> element.setValue(element.getValue() % 3));
	}
}

@AllArgsConstructor
@Getter
class Step implements Comparable<Step> {
	private final GridElement<Long> location;
	private final int duration;
	private final Gadget gadget;

	@Override
	public int compareTo(Step other) {
		return duration - other.duration;
	}
}

enum Gadget {
	TORCH, CLIMBING_GEAR, NONE
}
