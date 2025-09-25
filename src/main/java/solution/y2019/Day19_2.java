package solution.y2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.SneakyThrows;
import utils.soution.Solution;

public class Day19_2 extends Solution {
	private List<Long> register;

	public static void main(String[] args) {
		new Day19_2().solve();
	}

	@Override
	protected String doSolve() {
		register = new ArrayList<>(
				Arrays.stream(input.getFirst().split(",")).mapToLong(Long::parseLong).boxed().toList());

		var currentX = 0L;
		var currentY = 3L;
		for (; ; currentY++) {
			for (; !checkPosition(currentX, currentY); currentX++) {
			}
			for (; checkPosition(currentX, currentY); currentX++) {
			}
			if (checkPosition(currentX - 100, currentY + 99)) {
				return (currentX - 100) * 10000 + currentY + "";
			}
			currentX--;
		}
	}

	@SneakyThrows
	private boolean checkPosition(long x, long y) {
		var intCodeInterpreter = new IntCodeInterpreter();
		intCodeInterpreter.getInput().put(x);
		intCodeInterpreter.getInput().put(y);
		intCodeInterpreter.withRegister(new ArrayList<>(register)).performComputation();
		return intCodeInterpreter.getOutput().poll() == 1;
	}
}
