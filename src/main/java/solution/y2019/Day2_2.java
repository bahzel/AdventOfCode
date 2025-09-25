package solution.y2019;

import java.util.ArrayList;
import java.util.Arrays;

import utils.soution.Solution;

public class Day2_2 extends Solution {
	public static void main(String[] args) {
		new Day2_2().solve();
	}

	@Override
	protected String doSolve() {
		var registerInput = new ArrayList<>(
				Arrays.stream(input.getFirst().split(",")).mapToLong(Long::parseLong).boxed().toList());

		for (var noun = 0L; noun < 100; noun++) {
			for (var verb = 0L; verb < 100; verb++) {
				var register = new ArrayList<>(registerInput);
				register.set(1, noun);
				register.set(2, verb);

				new IntCodeInterpreter().withRegister(register).performComputation();
				if (register.getFirst() == 19690720) {
					return noun * 100 + verb + "";
				}
			}
		}

		throw new IllegalStateException();
	}
}
