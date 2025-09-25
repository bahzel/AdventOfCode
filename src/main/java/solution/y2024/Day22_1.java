package solution.y2024;

import java.util.concurrent.atomic.AtomicLong;

import utils.soution.InstructionSolution;

public class Day22_1 extends InstructionSolution<Long, AtomicLong> {
	public static void main(String[] args) {
		new Day22_1().solve();
	}

	@Override
	protected AtomicLong initializeValue() {
		return new AtomicLong();
	}

	@Override
	protected Long transformInstruction(String instruction) {
		return Long.parseLong(instruction);
	}

	@Override
	protected boolean performInstruction(Long previousNumber, AtomicLong solution) {
		for (int i = 0; i < 2000; i++) {
			previousNumber = calculateNextSecretNumber(previousNumber);
		}
		solution.addAndGet(previousNumber);

		return false;
	}

	private long calculateNextSecretNumber(long secretNumber) {
		secretNumber = mix(secretNumber * 64, secretNumber);
		secretNumber = prune(secretNumber);
		secretNumber = mix(secretNumber / 32, secretNumber);
		secretNumber = prune(secretNumber);
		secretNumber = mix(secretNumber * 2048, secretNumber);
		return prune(secretNumber);
	}

	private long mix(long givenNumber, long secretNumber) {
		return givenNumber ^ secretNumber;
	}

	private long prune(long secretNumber) {
		return secretNumber % 16777216;
	}

	@Override
	protected String getSolution(AtomicLong solution) {
		return solution.toString();
	}
}
