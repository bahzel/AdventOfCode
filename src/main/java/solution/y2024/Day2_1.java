package solution.y2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import utils.soution.InstructionSolution;

public class Day2_1 extends InstructionSolution<List<Integer>, AtomicInteger> {
	public static void main(String[] args) {
		new Day2_1().solve();
	}

	@Override
	protected AtomicInteger initializeValue() {
		return new AtomicInteger();
	}

	@Override
	protected List<Integer> transformInstruction(String instruction) {
		return new ArrayList<>(Arrays.stream(instruction.split(" ")).mapToInt(Integer::parseInt).boxed().toList());
	}

	@Override
	protected boolean performInstruction(List<Integer> integers, AtomicInteger atomicInteger) {
		if (integers.getFirst() > integers.getLast()) {
			integers = integers.reversed();
		}

		var isSafe = true;
		var lastValue = integers.getFirst();
		for (int i = 1; i < integers.size(); i++) {
			var nextValue = integers.get(i);
			if (nextValue <= lastValue || nextValue - lastValue > 3) {
				isSafe = false;
				break;
			}
			lastValue = nextValue;
		}

		if (isSafe) {
			atomicInteger.incrementAndGet();
		}

		return false;
	}

	@Override
	protected String getSolution(AtomicInteger atomicInteger) {
		return atomicInteger.toString();
	}
}
