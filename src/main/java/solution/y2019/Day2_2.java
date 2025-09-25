package solution.y2019;

import java.util.Arrays;

import utils.soution.Solution;

public class Day2_2 extends Solution {
	public static void main(String[] args) {
		new Day2_2().solve();
	}

	@Override
	protected String doSolve() {
		var registerInput = Arrays.stream(input.getFirst().split(",")).mapToInt(Integer::parseInt).toArray();

		for (var noun = 0; noun < 100; noun++) {
			for (var verb = 0; verb < 100; verb++) {
				var register = Arrays.copyOf(registerInput, registerInput.length);
				register[1] = noun;
				register[2] = verb;

				IntCodeInterpreter.performComputation(register);
				if (register[0] == 19690720) {
					return noun * 100 + verb + "";
				}
			}
		}

		throw new IllegalStateException();
	}
}
