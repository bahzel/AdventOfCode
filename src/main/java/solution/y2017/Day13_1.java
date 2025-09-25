package solution.y2017;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.tuple.Pair;

import utils.soution.InstructionSolution;

public class Day13_1 extends InstructionSolution<Pair<Integer, Integer>, AtomicLong> {
	public static void main(String[] args) {
		new Day13_1().solve();
	}

	@Override
	protected AtomicLong initializeValue() {
		return new AtomicLong();
	}

	@Override
	protected Pair<Integer, Integer> transformInstruction(String instruction) {
		var instructions = instruction.split(": ");
		return Pair.of(Integer.parseInt(instructions[0]), Integer.parseInt(instructions[1]));
	}

	@Override
	protected boolean performInstruction(Pair<Integer, Integer> integerIntegerPair, AtomicLong atomicLong) {
		if (integerIntegerPair.getLeft() % (integerIntegerPair.getRight() * 2 - 2) == 0) {
			atomicLong.addAndGet((long) integerIntegerPair.getLeft() * integerIntegerPair.getRight());
		}

		return false;
	}

	@Override
	protected String getSolution(AtomicLong atomicLong) {
		return atomicLong.toString();
	}
}
