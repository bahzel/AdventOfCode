package solution.y2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

import utils.soution.Solution;

public class Day5_1 extends Solution {
	public static void main(String[] args) {
		new Day5_1().solve();
	}

	@Override
	protected String doSolve() {
		var register = new ArrayList<>(
				Arrays.stream(input.getFirst().split(",")).mapToLong(Long::parseLong).boxed().toList());

		var output = new LinkedBlockingQueue<Long>();
		new IntCodeInterpreter().withRegister(register).withInput(1L).withOutput(output).performComputation();
		return new ArrayList<>(output).getLast() + "";
	}
}
