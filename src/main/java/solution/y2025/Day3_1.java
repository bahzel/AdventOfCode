package solution.y2025;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import utils.StringTransformer;
import utils.soution.InstructionSolution;

public class Day3_1 extends InstructionSolution<List<Integer>, AtomicLong> {
	public static void main(String[] args) {
		new Day3_1().solve();
	}

	@Override
	protected AtomicLong initializeValue() {
		return new AtomicLong();
	}

	@Override
	protected List<Integer> transformInstruction(String instruction) {
		return StringTransformer.splitString(instruction).stream().map(Integer::parseInt).toList();
	}

	@Override
	protected boolean performInstruction(List<Integer> integers, AtomicLong atomicLong) {
		var firstNumber = integers	.subList(0, integers.size() - 1)
									.stream()
									.mapToInt(Integer::intValue)
									.max()
									.orElseThrow();
		var secondNumber = integers	.subList(integers.indexOf(firstNumber) + 1, integers.size())
									.stream()
									.mapToInt(Integer::intValue)
									.max()
									.orElseThrow();
		atomicLong.addAndGet(firstNumber * 10L + secondNumber);

		return false;
	}

	@Override
	protected String getSolution(AtomicLong atomicLong) {
		return atomicLong.toString();
	}
}
