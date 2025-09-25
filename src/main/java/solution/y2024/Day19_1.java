package solution.y2024;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

import utils.soution.InstructionWithInputSolution;

public class Day19_1 extends InstructionWithInputSolution<String, List<String>, AtomicInteger> {
	public static void main(String[] args) {
		new Day19_1().solve();
	}

	@Override
	protected AtomicInteger initializeValue() {
		return new AtomicInteger();
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		return instructions.subList(2, instructions.size());
	}

	@Override
	protected String transformInstruction(String instruction) {
		return instruction;
	}

	@Override
	protected List<String> getInput(List<String> instructions) {
		return instructions.subList(0, 1);
	}

	@Override
	protected List<String> transformInput(List<String> input) {
		return Arrays.asList(input.getFirst().split(", "));
	}

	@Override
	protected void performInstruction(String instruction, List<String> towels, AtomicInteger solution) {
		var cache = new HashSet<Integer>();

		Queue<Integer> queue = new LinkedList<>();
		queue.add(0);
		while (!queue.isEmpty()) {
			var currentIndex = queue.poll();
			if (!cache.add(currentIndex)) {
				continue;
			}

			if (currentIndex == instruction.length()) {
				solution.incrementAndGet();
				return;
			}

			var rest = instruction.substring(currentIndex);
			for (var towel : towels) {
				if (rest.startsWith(towel)) {
					queue.add(currentIndex + towel.length());
				}
			}
		}
	}

	@Override
	protected String getSolution(AtomicInteger atomicInteger) {
		return atomicInteger.toString();
	}
}
