package solution.y2022;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.tuple.Pair;

import utils.soution.InstructionSolution;

public class Day4_1 extends InstructionSolution<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>, AtomicInteger> {
	public static void main(String[] args) {
		new Day4_1().solve();
	}

	@Override
	protected AtomicInteger initializeValue() {
		return new AtomicInteger();
	}

	@Override
	protected Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> transformInstruction(String instruction) {
		var instructions = instruction.replace(",", "-").split("-");
		return Pair.of(Pair.of(Integer.parseInt(instructions[0]), Integer.parseInt(instructions[1])),
				Pair.of(Integer.parseInt(instructions[2]), Integer.parseInt(instructions[3])));
	}

	@Override
	protected boolean performInstruction(Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> pairPairPair,
			AtomicInteger atomicInteger) {
		if (pairPairPair.getLeft().getLeft() >= pairPairPair.getRight().getLeft()
				&& pairPairPair.getLeft().getRight() <= pairPairPair.getRight().getRight()
				|| pairPairPair.getRight().getLeft() >= pairPairPair.getLeft().getLeft()
						&& pairPairPair.getRight().getRight() <= pairPairPair.getLeft().getRight()) {
			atomicInteger.incrementAndGet();
		}

		return false;
	}

	@Override
	protected String getSolution(AtomicInteger atomicInteger) {
		return atomicInteger.toString();
	}
}
