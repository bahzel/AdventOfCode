package solution.y2023;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.tuple.Pair;

import utils.soution.InstructionSolution;

public class Day4_1 extends InstructionSolution<Pair<List<Integer>, Set<Integer>>, AtomicInteger> {
	public static void main(String[] args) {
		new Day4_1().solve();
	}

	@Override
	protected AtomicInteger initializeValue() {
		return new AtomicInteger(0);
	}

	@Override
	protected Pair<List<Integer>, Set<Integer>> transformInstruction(String instruction) {
		var instructions = instruction.replace("  ", " ").split(": ")[1].split(" \\| ");
		var winningNumbers = Arrays.stream(instructions[0].split(" ")).map(Integer::parseInt).toList();
		var numbers = new HashSet<>(Arrays.stream(instructions[1].split(" ")).map(Integer::parseInt).toList());

		return Pair.of(winningNumbers, numbers);
	}

	@Override
	protected boolean performInstruction(Pair<List<Integer>, Set<Integer>> listSetPair, AtomicInteger atomicInteger) {
		var winningCount = listSetPair.getRight().size();
		listSetPair.getLeft().forEach(listSetPair.getRight()::remove);
		winningCount -= listSetPair.getRight().size();

		atomicInteger.addAndGet((int) Math.pow(2, winningCount - 1));
		return false;
	}

	@Override
	protected String getSolution(AtomicInteger atomicInteger) {
		return atomicInteger.toString();
	}
}