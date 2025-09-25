package solution.y2019;

import java.util.Arrays;
import java.util.List;

import utils.soution.Solution;

public class Day5_2 extends Solution {
	private final int inputValue;

	public static void main(String[] args) {
		new Day5_2().solve();
	}

	public Day5_2() {
		super();
		inputValue = 5;
	}

	public Day5_2(List<String> input, int inputValue) {
		super(input);
		this.inputValue = inputValue;
	}

	@Override
	protected String doSolve() {
		var register = Arrays.stream(input.getFirst().split(",")).mapToInt(Integer::parseInt).toArray();

		var output = IntCodeInterpreter.performComputation(register, inputValue);
		return output.getLast() + "";
	}
}
