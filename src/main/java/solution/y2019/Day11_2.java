package solution.y2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import lombok.SneakyThrows;
import utils.Point;
import utils.soution.Solution;

public class Day11_2 extends Solution {
	public static void main(String[] args) {
		new Day11_2().solve();
	}

	@Override
	@SneakyThrows
	protected String doSolve() {
		var register = new ArrayList<>(
				Arrays.stream(input.getFirst().split(",")).mapToLong(Long::parseLong).boxed().toList());

		var hull = new HashMap<Point, Long>();
		hull.put(new Point(0, 0), 1L);
		var intCodeInterpreter = new IntCodeInterpreter().withRegister(register);
		var intCodeInterpreterThread = new Thread(intCodeInterpreter::performComputation);
		var paintingRobot = new PaintingRobot(intCodeInterpreter, hull);
		var paintingRobotThread = new Thread(paintingRobot);

		intCodeInterpreterThread.start();
		paintingRobotThread.start();
		intCodeInterpreterThread.join();
		paintingRobotThread.interrupt();

		return print(hull);
	}

	private String print(Map<Point, Long> hull) {
		var solution = new StringBuilder();

		var minX = hull.keySet().stream().mapToInt(Point::getX).min().orElseThrow();
		var maxX = hull.keySet().stream().mapToInt(Point::getX).max().orElseThrow();
		var minY = hull.keySet().stream().mapToInt(Point::getY).min().orElseThrow();
		var maxY = hull.keySet().stream().mapToInt(Point::getY).max().orElseThrow();

		for (int y = minY; y <= maxY; y++) {
			for (int x = minX; x <= maxX; x++) {
				if (hull.getOrDefault(new Point(x, y), 0L) == 1) {
					solution.append('█');
				} else {
					solution.append(' ');
				}
			}
			solution.append('\n');
		}

		return solution.toString();
	}
}
