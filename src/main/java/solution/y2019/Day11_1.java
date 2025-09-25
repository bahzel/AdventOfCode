package solution.y2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import utils.Direction;
import utils.Point;
import utils.soution.Solution;

public class Day11_1 extends Solution {
	public static void main(String[] args) {
		new Day11_1().solve();
	}

	@Override
	@SneakyThrows
	protected String doSolve() {
		var register = new ArrayList<>(
				Arrays.stream(input.getFirst().split(",")).mapToLong(Long::parseLong).boxed().toList());

		var hull = new HashMap<Point, Long>();
		var intCodeInterpreter = new IntCodeInterpreter().withRegister(register);
		var intCodeInterpreterThread = new Thread(intCodeInterpreter::performComputation);
		var paintingRobot = new PaintingRobot(intCodeInterpreter, hull);
		var paintingRobotThread = new Thread(paintingRobot);

		intCodeInterpreterThread.start();
		paintingRobotThread.start();
		intCodeInterpreterThread.join();
		paintingRobotThread.interrupt();

		return hull.size() + "";
	}
}

@AllArgsConstructor
class PaintingRobot implements Runnable {
	private final IntCodeInterpreter intCodeInterpreter;
	private final Map<Point, Long> hull;

	@Override
	public void run() {
		var currentPoint = new Point(0, 0);
		var currentDirection = Direction.UP;

		while (true) {
			intCodeInterpreter.getInput().add(hull.getOrDefault(currentPoint, 0L));
			try {
				hull.put(currentPoint, intCodeInterpreter.getOutput().take());
				if (intCodeInterpreter.getOutput().take() == 0) {
					currentDirection = currentDirection.turn(Direction.LEFT);
				} else {
					currentDirection = currentDirection.turn(Direction.RIGHT);
				}
			} catch (InterruptedException e) {
				return;
			}
			currentPoint = currentPoint.getNeighbour(currentDirection);
		}
	}
}
