package solution.y2019;

import java.util.ArrayList;
import java.util.Arrays;

import utils.soution.Solution;

public class Day2_1 extends Solution {
	public static void main(String[] args) {
		new Day2_1().solve();
	}

	@Override
	protected String doSolve() {
		var register = new ArrayList<>(
				Arrays.stream(input.getFirst().split(",")).mapToLong(Long::parseLong).boxed().toList());
		register.set(1, 12L);
		register.set(2, 2L);

		IntCodeInterpreter.performComputation(register);
		return register.getFirst() + "";
	}
}
