package solution.y2020;

import java.util.concurrent.atomic.AtomicInteger;

import utils.soution.InstructionSolution;

public class Day2_2 extends InstructionSolution<Password, AtomicInteger> {
	public static void main(String[] args) {
		new Day2_2().solve();
	}

	@Override
	protected AtomicInteger initializeValue() {
		return new AtomicInteger();
	}

	@Override
	protected Password transformInstruction(String instruction) {
		var instructions = instruction.replace("-", " ").replace(":", "").split(" ");
		return new Password(Integer.parseInt(instructions[0]), Integer.parseInt(instructions[1]),
				instructions[2].charAt(0), instructions[3]);
	}

	@Override
	protected boolean performInstruction(Password password, AtomicInteger atomicInteger) {
		if (password.getPassword().charAt(password.getFrom()) == password.getCharacter()
				^ password.getPassword().charAt(password.getTo()) == password.getCharacter()) {
			atomicInteger.incrementAndGet();
		}
		return false;
	}

	@Override
	protected String getSolution(AtomicInteger atomicInteger) {
		return atomicInteger.toString();
	}
}
