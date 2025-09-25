package solution.y2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import utils.soution.MapSolution;

public class Day13_1 extends MapSolution<List<Long>> {
	public static void main(String[] args) {
		new Day13_1().solve();
	}

	@Override
	protected List<Long> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<Long> longs) {
		var register = new ArrayList<>(
				Arrays.stream(input.getFirst().split(",")).mapToLong(Long::parseLong).boxed().toList());
		var output = new LinkedBlockingQueue<Long>();
		new IntCodeInterpreter().withRegister(register).withOutput(output).performComputation();
		longs.addAll(output);
	}

	@Override
	protected String computeSolution(List<Long> longs) {
		var solution = 0;

		for (int i = 2; i < longs.size(); i = i + 3) {
			if (longs.get(i) == 2) {
				solution++;
			}
		}

		return solution + "";
	}
}
