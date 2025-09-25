package solution.y2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

import utils.soution.Solution;

public class Day5_1 extends Solution {
	public static void main(String[] args) {
		new Day5_1().solve();
	}

	@Override
	protected String doSolve() {
		var register = Arrays.stream(input.getFirst().split(",")).mapToInt(Integer::parseInt).toArray();

		var output = new ConcurrentLinkedQueue<Integer>();
		IntCodeInterpreter.performComputation(register, 1, output);
		return new ArrayList<>(output).getLast() + "";
	}
}
