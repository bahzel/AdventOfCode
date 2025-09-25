package solution.y2024;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import utils.Point;
import utils.soution.MapSolution;

public class Day14_1 extends MapSolution<List<Pair<Point, Point>>> {
	private static final int WIDTH = 101;
	private static final int HEIGHT = 103;
	private static final int SECONDS = 100;

	public static void main(String[] args) {
		new Day14_1().solve();
	}

	@Override
	protected List<Pair<Point, Point>> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<Pair<Point, Point>> pairs) {
		var instructions = instruction.replace("p=", "").replace(" v=", ",").split(",");
		pairs.add(Pair.of(new Point(Integer.parseInt(instructions[0]), Integer.parseInt(instructions[1])),
				new Point(Integer.parseInt(instructions[2]), Integer.parseInt(instructions[3]))));
	}

	@Override
	protected String computeSolution(List<Pair<Point, Point>> pairs) {
		var positions = pairs.stream().map(robot -> computePositionAfter(robot, SECONDS)).toList();
		var quadrant1 = positions.stream()
								 .filter(position -> position.getX() < WIDTH / 2 && position.getY() < HEIGHT / 2)
								 .count();
		var quadrant2 = positions.stream()
								 .filter(position -> position.getX() > WIDTH / 2 && position.getY() < HEIGHT / 2)
								 .count();
		var quadrant3 = positions.stream()
								 .filter(position -> position.getX() > WIDTH / 2 && position.getY() > HEIGHT / 2)
								 .count();
		var quadrant4 = positions.stream()
								 .filter(position -> position.getX() < WIDTH / 2 && position.getY() > HEIGHT / 2)
								 .count();
		return quadrant1 * quadrant2 * quadrant3 * quadrant4 + "";
	}

	private Point computePositionAfter(Pair<Point, Point> robot, int seconds) {
		var x = robot.getLeft().getX() + seconds * robot.getRight().getX();
		var y = robot.getLeft().getY() + seconds * robot.getRight().getY();
		x = x % WIDTH;
		y = y % HEIGHT;
		if (x < 0) {
			x += WIDTH;
		}
		if (y < 0) {
			y += HEIGHT;
		}
		return new Point(x, y);
	}
}
