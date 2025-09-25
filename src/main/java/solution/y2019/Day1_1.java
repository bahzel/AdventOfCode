package solution.y2019;

import java.util.concurrent.atomic.AtomicLong;

import utils.soution.InstructionSolution;

public class Day1_1 extends InstructionSolution<Long, AtomicLong> {
	public static void main(String[] args) {
		new Day1_1().solve();
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
	protected boolean performInstruction(Long aLong, AtomicLong atomicLong) {
		atomicLong.addAndGet(aLong / 3 - 2);

		return false;
	}

	@Override
	protected String getSolution(AtomicLong atomicLong) {
		return atomicLong.toString();
	}
}
