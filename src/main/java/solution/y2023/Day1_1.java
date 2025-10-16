package solution.y2023;

import java.util.concurrent.atomic.AtomicInteger;

import utils.soution.InstructionSolution;

public class Day1_1 extends InstructionSolution<String, AtomicInteger> {
	public static void main(String[] args) {
		new Day1_1().solve();
	}

	@Override
	protected AtomicInteger initializeValue() {
		return new AtomicInteger(0);
	}

	@Override
	protected String transformInstruction(String instruction) {
		return instruction;
	}

	@Override
	protected boolean performInstruction(String instruction, AtomicInteger atomicInteger) {
		var firstIndex = 0;
		var lastIndex = 0;
		for (var i = 0; i < instruction.length(); i++) {
			if (Character.isDigit(instruction.charAt(i))) {
				firstIndex = i;
				break;
			}
		}
		for (var i = instruction.length() - 1; i >= 0; i--) {
			if (Character.isDigit(instruction.charAt(i))) {
				lastIndex = i;
				break;
			}
		}
		atomicInteger.addAndGet(Integer.parseInt("" + instruction.charAt(firstIndex) + instruction.charAt(lastIndex)));

		return false;
	}

	@Override
	protected String getSolution(AtomicInteger atomicInteger) {
		return atomicInteger.toString();
	}
}