package solution.y2019;

import java.util.Arrays;

import utils.soution.Solution;

public class Day5_1 extends Solution {
	public static void main(String[] args) {
		new Day5_1().solve();
	}

	@Override
	protected String doSolve() {
		var register = Arrays.stream(input.getFirst().split(",")).mapToInt(Integer::parseInt).toArray();

		var output = IntCodeInterpreter.performComputation(register, 1);
		return output.getLast() + "";
	}
}
