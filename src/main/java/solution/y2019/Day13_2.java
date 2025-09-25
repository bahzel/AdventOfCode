package solution.y2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.SneakyThrows;
import utils.Point;
import utils.soution.MapSolution;

public class Day13_2 extends MapSolution<IntCodeInterpreter> {
	private long currentScore = 0L;
	private Set<Point> walls;
	private Point paddle;

	public static void main(String[] args) {
		new Day13_2().solve();
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
		register.set(0, 2L);
		new Thread(() -> intCodeInterpreter.withRegister(register).performComputation()).start();
		intCodeInterpreter.getStatus().take();
		walls = getWalls(new ArrayList<>(intCodeInterpreter.getOutput()));
	}

	@Override
	@SneakyThrows
	protected String computeSolution(IntCodeInterpreter intCodeInterpreter) {
		while (true) {
			println("--- Next Round ---");

			var buffer = new ArrayList<Long>();
			intCodeInterpreter.getOutput().drainTo(buffer);

			updateScore(buffer);
			updateWalls(buffer);

			if (walls.isEmpty()) {
				return currentScore + "";
			}

			var ballPosition = getBallPosition(buffer);
			updatePaddlePosition(buffer);
			println("Paddle: " + paddle);
			intCodeInterpreter	.getInput()
								.add(ballPosition < paddle.getX() ? -1L : ballPosition > paddle.getX() ? 1L : 0L);

			if (!intCodeInterpreter.getStatus().take()) {
				updateScore(new ArrayList<>(intCodeInterpreter.getOutput()));
				return currentScore + "";
			}
		}
	}

	private void updateScore(List<Long> output) {
		for (int i = 0; i < output.size(); i = i + 3) {
			if (output.get(i) == -1 && output.get(i + 1) == 0) {
				currentScore = output.get(i + 2);
				return;
			}
		}
	}

	private void updateWalls(List<Long> output) {
		for (int i = 0; i < output.size(); i = i + 3) {
			if (output.get(i + 2) == 0) {
				walls.remove(new Point(Math.toIntExact(output.get(i)), Math.toIntExact(output.get(i + 1))));
			}
		}
	}

	private Set<Point> getWalls(List<Long> output) {
		var walls = new HashSet<Point>();

		for (int i = 0; i < output.size(); i = i + 3) {
			if (output.get(i + 2) == 2) {
				walls.add(new Point(Math.toIntExact(output.get(i)), Math.toIntExact(output.get(i + 1))));
			}
		}

		return walls;
	}

	private void updatePaddlePosition(List<Long> output) {
		for (int i = 0; i < output.size(); i = i + 3) {
			if (output.get(i + 2) == 3) {
				paddle = new Point(Math.toIntExact(output.get(i)), Math.toIntExact(output.get(i + 1)));
			}
		}
	}

	private long getBallPosition(List<Long> output) {
		for (int i = 0; i < output.size(); i = i + 3) {
			if (output.get(i + 2) == 4) {
				println("Ball: " + output.get(i) + "/" + output.get(i + 1));
				return output.get(i);
			}
		}

		throw new IllegalStateException();
	}
}
