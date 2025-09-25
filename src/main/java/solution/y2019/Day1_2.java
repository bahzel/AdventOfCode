package solution.y2019;

import java.util.concurrent.atomic.AtomicLong;

import utils.soution.InstructionSolution;

public class Day1_2 extends InstructionSolution<Long, AtomicLong> {
	public static void main(String[] args) {
		new Day1_2().solve();
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
		var addedFuel = aLong;
		while (true) {
			var nextFuel = addedFuel / 3 - 2;
			if (nextFuel > 0) {
				atomicLong.addAndGet(nextFuel);
				addedFuel = nextFuel;
			} else {
				break;
			}
		}

		return false;
	}

	@Override
	protected String getSolution(AtomicLong atomicLong) {
		return atomicLong.toString();
	}
}
