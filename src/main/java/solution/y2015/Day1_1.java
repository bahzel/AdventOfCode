package solution.y2015;

import utils.InstructionSolution;
import utils.StringTransformer;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Day1_1 extends InstructionSolution<String, AtomicLong> {
	public static void main(String[] args) {
		new Day1_1().solve();
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		return StringTransformer.fromString(instructions.getFirst());
	}

	@Override
	protected AtomicLong initializeValue() {
		return new AtomicLong();
	}

	@Override
	protected String transformInstruction(String instruction) {
		return instruction;
	}

	@Override
	protected boolean performInstruction(String instruction, AtomicLong value) {
		switch (instruction) {
		case "(":
			value.incrementAndGet();
			break;
		case ")":
			value.decrementAndGet();
			break;
		default:
			throw new IllegalArgumentException("Invalid instruction: " + instruction);
		}
		return false;
	}

	@Override
	protected String getSolution(AtomicLong value) {
		return value.toString();
	}
}
