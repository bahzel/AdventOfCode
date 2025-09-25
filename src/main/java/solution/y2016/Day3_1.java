package solution.y2016;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.tuple.Triple;

import utils.soution.InstructionSolution;

public class Day3_1 extends InstructionSolution<Triple<Integer, Integer, Integer>, AtomicInteger> {
	public static void main(String[] args) {
		new Day3_1().solve();
	}

	@Override
	protected AtomicInteger initializeValue() {
		return new AtomicInteger();
	}

	@Override
	protected Triple<Integer, Integer, Integer> transformInstruction(String instruction) {
		var lengths = instruction.replaceAll("\\s+", " ").substring(1).split(" ");
		return Triple.of(Integer.parseInt(lengths[0]), Integer.parseInt(lengths[1]), Integer.parseInt(lengths[2]));
	}

	@Override
	protected boolean performInstruction(Triple<Integer, Integer, Integer> triangle, AtomicInteger atomicInteger) {
		if (triangle.getLeft() + triangle.getMiddle() > triangle.getRight()
				&& triangle.getLeft() + triangle.getRight() > triangle.getMiddle()
				&& triangle.getMiddle() + triangle.getRight() > triangle.getLeft()) {
			atomicInteger.incrementAndGet();
		}

		return false;
	}

	@Override
	protected String getSolution(AtomicInteger atomicInteger) {
		return atomicInteger.toString();
	}
}
