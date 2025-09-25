package solution.y2018;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.tuple.Pair;
import utils.StringTransformer;
import utils.soution.InstructionSolution;

public class Day2_1 extends InstructionSolution<String, Pair<AtomicInteger, AtomicInteger>> {
	public static void main(String[] args) {
		new Day2_1().solve();
	}

	@Override
	protected Pair<AtomicInteger, AtomicInteger> initializeValue() {
		return Pair.of(new AtomicInteger(), new AtomicInteger());
	}

	@Override
	protected String transformInstruction(String instruction) {
		return StringTransformer.sort(instruction);
	}

	@Override
	protected boolean performInstruction(String s, Pair<AtomicInteger, AtomicInteger> atomicIntegerAtomicIntegerPair) {
		if (hasPair(s)) {
			atomicIntegerAtomicIntegerPair.getLeft().incrementAndGet();
		}
		if (hasTriple(s)) {
			atomicIntegerAtomicIntegerPair.getRight().incrementAndGet();
		}

		return false;
	}

	private boolean hasPair(String candidate) {
		for (int i = 0; i < candidate.length() - 1; i++) {
			if (candidate.charAt(i) == candidate.charAt(i + 1) && (i == candidate.length() - 2
					|| candidate.charAt(i) != candidate.charAt(i + 2))) {
				return true;
			}
			i = candidate.lastIndexOf(candidate.charAt(i));
		}
		return false;
	}

	private boolean hasTriple(String candidate) {
		for (int i = 0; i < candidate.length() - 2; i++) {
			if (candidate.charAt(i) == candidate.charAt(i + 1) && candidate.charAt(i) == candidate.charAt(i + 2) && (
					i == candidate.length() - 3 || candidate.charAt(i) != candidate.charAt(i + 3))) {
				return true;
			}
			i = candidate.lastIndexOf(candidate.charAt(i));
		}
		return false;
	}

	@Override
	protected String getSolution(Pair<AtomicInteger, AtomicInteger> atomicIntegerAtomicIntegerPair) {
		return atomicIntegerAtomicIntegerPair.getLeft().get() * atomicIntegerAtomicIntegerPair.getRight().get() + "";
	}
}
