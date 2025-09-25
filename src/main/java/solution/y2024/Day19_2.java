package solution.y2024;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import utils.soution.InstructionWithInputSolution;

public class Day19_2 extends InstructionWithInputSolution<String, List<String>, AtomicLong> {
	private final Map<String, Long> cache = new HashMap<>();

	public static void main(String[] args) {
		new Day19_2().solve();
	}

	@Override
	protected AtomicLong initializeValue() {
		return new AtomicLong();
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
	protected void performInstruction(String instruction, List<String> towels, AtomicLong solution) {
		solution.addAndGet(countPossibleCombinations(instruction, towels));
	}

	private long countPossibleCombinations(String instruction, List<String> towels) {
		if (instruction.isEmpty()) {
			return 1;
		}

		var cachedValue = cache.get(instruction);
		if (cachedValue != null) {
			return cachedValue;
		}

		var combinationCount = 0L;
		for (var towel : towels) {
			if (instruction.startsWith(towel)) {
				combinationCount += countPossibleCombinations(instruction.substring(towel.length()), towels);
			}
		}

		cache.put(instruction, combinationCount);
		return combinationCount;
	}

	@Override
	protected String getSolution(AtomicLong solution) {
		return solution.toString();
	}
}
