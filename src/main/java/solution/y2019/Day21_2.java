package solution.y2019;

import java.util.ArrayList;
import java.util.Arrays;

import utils.soution.Solution;

public class Day21_2 extends Solution {
	public static void main(String[] args) {
		new Day21_2().solve();
	}

	@Override
	protected String doSolve() {
		var register = new ArrayList<>(
				Arrays.stream(input.getFirst().split(",")).mapToLong(Long::parseLong).boxed().toList());
		var intCodeInterpreter = new IntCodeInterpreter().withRegister(register);
		intCodeInterpreter.inputString("NOT B J");
		intCodeInterpreter.inputString("NOT C T");
		intCodeInterpreter.inputString("OR T J");
		intCodeInterpreter.inputString("AND D J");
		intCodeInterpreter.inputString("AND H J");
		intCodeInterpreter.inputString("NOT A T");
		intCodeInterpreter.inputString("OR T J");
		intCodeInterpreter.inputString("RUN");

		intCodeInterpreter.performComputation();
		return new ArrayList<>(intCodeInterpreter.getOutput()).getLast().toString();
	}
}
