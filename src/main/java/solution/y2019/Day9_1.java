package solution.y2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import utils.soution.Solution;

public class Day9_1 extends Solution {
	public static void main(String[] args) {
		new Day9_1().solve();
	}

	@Override
	protected String doSolve() {
		var register = new ArrayList<>(
				Arrays.stream(input.getFirst().split(",")).mapToLong(Long::parseLong).boxed().toList());

		var output = new LinkedBlockingQueue<Long>();
		IntCodeInterpreter.performComputation(register, new LinkedBlockingQueue<>(List.of(1L)), output);

		return output.poll() + "";
	}
}
