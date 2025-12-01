package solution.y2025;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import utils.soution.InstructionWithInputSolution;

public class Day1_1 extends InstructionWithInputSolution<Integer, AtomicInteger, AtomicInteger> {
	public static void main(String[] args) {
		new Day1_1().solve();
	}

	@Override
	protected AtomicInteger initializeValue() {
		return new AtomicInteger(0);
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		return input;
	}

	@Override
	protected Integer transformInstruction(String instruction) {
		var steps = Integer.parseInt(instruction.substring(1));
		return switch (instruction.charAt(0)) {
		case 'L' -> -steps;
		case 'R' -> steps;
		default -> throw new IllegalArgumentException("Invalid instruction");
		};
	}

	@Override
	protected List<String> getInput(List<String> instructions) {
		return null;
	}

	@Override
	protected AtomicInteger transformInput(List<String> input) {
		return new AtomicInteger(50);
	}

	@Override
	protected void performInstruction(Integer integer, AtomicInteger value, AtomicInteger solution) {
		value.addAndGet(integer);
		while (value.get() < 0) {
			value.addAndGet(100);
		}
		value.set(value.get() % 100);
		if (value.get() == 0) {
			solution.incrementAndGet();
		}
	}

	@Override
	protected String getSolution(AtomicInteger atomicInteger) {
		return atomicInteger.toString();
	}
}
