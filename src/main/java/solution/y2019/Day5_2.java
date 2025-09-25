package solution.y2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

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
		var register = new ArrayList<>(
				Arrays.stream(input.getFirst().split(",")).mapToLong(Long::parseLong).boxed().toList());

		var output = new LinkedBlockingQueue<Long>();
		new IntCodeInterpreter().withRegister(register).withInput(inputValue).withOutput(output).performComputation();
		return output.poll() + "";
	}
}
