package solution.y2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

import lombok.SneakyThrows;
import utils.soution.Solution;

public class Day19_1 extends Solution {
	public static void main(String[] args) {
		new Day19_1().solve();
	}

	@Override
	@SneakyThrows
	protected String doSolve() {
		var register = new ArrayList<>(
				Arrays.stream(input.getFirst().split(",")).mapToLong(Long::parseLong).boxed().toList());
		var input = new LinkedBlockingQueue<Long>();
		var output = new LinkedBlockingQueue<Long>();

		var solution = 0;

		for (var y = 0L; y < 50; y++) {
			print("y=" + y + ":");
			for (var x = 0L; x < 50; x++) {
				input.put(x);
				input.put(y);
				new IntCodeInterpreter().withRegister(new ArrayList<>(register))
										.withInput(input)
										.withOutput(output)
										.performComputation();
				if (output.poll() == 1) {
					print(" " + x);
					solution++;
				}
			}
			println();
		}
		return solution + "";
	}
}
