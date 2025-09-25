package solution.y2019;

import java.util.ArrayList;
import java.util.Arrays;

import utils.soution.Solution;

public class Day21_1 extends Solution {
	public static void main(String[] args) {
		new Day21_1().solve();
	}

	@Override
	protected String doSolve() {
		var register = new ArrayList<>(
				Arrays.stream(input.getFirst().split(",")).mapToLong(Long::parseLong).boxed().toList());
		var intCodeInterpreter = new IntCodeInterpreter().withRegister(register);
		intCodeInterpreter.inputString("NOT C T");
		intCodeInterpreter.inputString("NOT B J");
		intCodeInterpreter.inputString("OR T J");
		intCodeInterpreter.inputString("NOT A T");
		intCodeInterpreter.inputString("OR T J");
		intCodeInterpreter.inputString("AND D J");
		intCodeInterpreter.inputString("WALK");
		intCodeInterpreter.performComputation();
		return new ArrayList<>(intCodeInterpreter.getOutput()).getLast().toString();
	}
}
