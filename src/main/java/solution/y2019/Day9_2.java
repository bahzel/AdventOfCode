package solution.y2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

import utils.soution.Solution;

public class Day9_2 extends Solution {
	public static void main(String[] args) {
		new Day9_2().solve();
	}

	@Override
	protected String doSolve() {
		var register = new ArrayList<>(
				Arrays.stream(input.getFirst().split(",")).mapToLong(Long::parseLong).boxed().toList());

		var output = new LinkedBlockingQueue<Long>();
		new IntCodeInterpreter().withRegister(register).withInput(2L).withOutput(output).performComputation();

		return output.poll() + "";
	}
}
