package solution.y2024;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.lang3.tuple.Pair;
import utils.Point;
import utils.soution.GridElement;
import utils.soution.GridInstruction;

public class Day18_1 extends GridInstruction<Boolean, Point> {
	private final int numberOfPixels;

	public static void main(String[] args) {
		new Day18_1().solve();
	}

	public Day18_1() {
		this(1024);
	}

	public Day18_1(int numberOfPixels) {
		super();
		this.numberOfPixels = numberOfPixels;
	}

	@Override
	protected int getWidth() {
		return 71;
	}

	@Override
	protected int getHeigth() {
		return 71;
	}

	@Override
	protected Boolean initialValue() {
		return true;
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		return instructions.subList(0, numberOfPixels);
	}

	@Override
	protected Point transformInstruction(String instruction) {
		var instructions = instruction.split(",");
		return new Point(Integer.parseInt(instructions[0]), Integer.parseInt(instructions[1]));
	}

	@Override
	protected void performInstruction(Point point, GridElement<Boolean>[][] grid) {
		grid[point.getX()][point.getY()].setValue(false);
	}

	@Override
	protected String getSolution(GridElement<Boolean>[][] grid) {
		var cache = new HashSet<GridElement<Boolean>>();

		Queue<Pair<GridElement<Boolean>, Integer>> queue = new LinkedList<>();
		queue.add(Pair.of(grid[0][0], 0));
		while (!queue.isEmpty()) {
			var currentLocation = queue.poll();
			if (!currentLocation.getLeft().getValue() || !cache.add(currentLocation.getLeft())) {
				continue;
			}

			if (currentLocation.getLeft() == grid[70][70]) {
				return currentLocation.getRight() + "";
			}

			currentLocation.getLeft()
						   .getBorderingNeighbours()
						   .forEach(neighbour -> queue.add(Pair.of(neighbour, currentLocation.getRight() + 1)));
		}

		throw new IllegalStateException("No solution found");
	}
}
