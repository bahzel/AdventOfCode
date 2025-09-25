package solution.y2019;

import java.util.Arrays;

import utils.soution.Solution;

public class Day2_1 extends Solution {
	public static void main(String[] args) {
		new Day2_1().solve();
	}

	@Override
	protected String doSolve() {
		var register = Arrays.stream(input.getFirst().split(",")).mapToInt(Integer::parseInt).toArray();
		register[1] = 12;
		register[2] = 2;

		return IntCodeInterpreter.performComputation(register) + "";
	}
}
