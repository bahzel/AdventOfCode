package solution.y2015;

import utils.soution.InstructionSolution;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class Day5_2 extends InstructionSolution<String, AtomicInteger> {
	private final Pattern PAIR = Pattern.compile("(..).*\\1");
	private final Pattern SANDWICH = Pattern.compile("(.).\\1");

	public static void main(String[] args) {
		new Day5_2().solve();
	}

	@Override
	protected AtomicInteger initializeValue() {
		return new AtomicInteger();
	}

	@Override
	protected String transformInstruction(String instruction) {
		return instruction;
	}

	@Override
	protected boolean performInstruction(String instruction, AtomicInteger value) {
		if (!PAIR.matcher(instruction).find()) {
			return false;
		}

		if (SANDWICH.matcher(instruction).find()) {
			value.incrementAndGet();
		}
		return false;
	}

	@Override
	protected String getSolution(AtomicInteger value) {
		return value.toString();
	}
}
