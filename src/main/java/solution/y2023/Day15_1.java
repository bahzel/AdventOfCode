package solution.y2023;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import utils.soution.InstructionSolution;

public class Day15_1 extends InstructionSolution<String, AtomicInteger> {
	public static void main(String[] args) {
		new Day15_1().solve();
	}

	@Override
	protected AtomicInteger initializeValue() {
		return new AtomicInteger();
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		return Arrays.asList(instructions.getFirst().split(","));
	}

	@Override
	protected String transformInstruction(String instruction) {
		return instruction;
	}

	@Override
	protected boolean performInstruction(String s, AtomicInteger atomicInteger) {
		var value = 0;
		for (var i = 0; i < s.length(); i++) {
			value += s.charAt(i);
			value *= 17;
			value %= 256;
		}
		atomicInteger.addAndGet(value);

		return false;
	}

	@Override
	protected String getSolution(AtomicInteger atomicInteger) {
		return atomicInteger.toString();
	}
}