package solution.y2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import utils.soution.InstructionSolution;

public class Day2_2 extends InstructionSolution<List<Integer>, AtomicInteger> {
	public static void main(String[] args) {
		new Day2_2().solve();
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
		if (isSafe(integers)) {
			atomicInteger.incrementAndGet();
		} else {
			for (int i = 0; i < integers.size(); i++) {
				var copy = new ArrayList<>(integers);
				copy.remove(i);
				if (isSafe(copy)) {
					atomicInteger.incrementAndGet();
					break;
				}
			}
		}

		return false;
	}

	private boolean isSafe(List<Integer> levels) {
		if (levels.getFirst() > levels.getLast()) {
			levels = levels.reversed();
		}

		var lastValue = levels.getFirst();
		for (int i = 1; i < levels.size(); i++) {
			var nextValue = levels.get(i);
			if (nextValue <= lastValue || nextValue - lastValue > 3) {
				return false;
			}
			lastValue = nextValue;
		}

		return true;
	}

	@Override
	protected String getSolution(AtomicInteger atomicInteger) {
		return atomicInteger.toString();
	}
}
