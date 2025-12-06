package solution.y2025;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.tuple.Pair;

import utils.soution.InstructionWithInputSolution;

public class Day5_1 extends InstructionWithInputSolution<Long, List<Pair<Long, Long>>, AtomicInteger> {
	public static void main(String[] args) {
		new Day5_1().solve();
	}

	@Override
	protected AtomicInteger initializeValue() {
		return new AtomicInteger(0);
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		return instructions.subList(instructions.indexOf("") + 1, instructions.size());
	}

	@Override
	protected Long transformInstruction(String instruction) {
		return Long.parseLong(instruction);
	}

	@Override
	protected List<String> getInput(List<String> instructions) {
		return instructions.subList(0, instructions.indexOf(""));
	}

	@Override
	protected List<Pair<Long, Long>> transformInput(List<String> input) {
		return input.stream().map(line -> {
			var values = line.split("-");
			return Pair.of(Long.parseLong(values[0]), Long.parseLong(values[1]));
		}).toList();
	}

	@Override
	protected void performInstruction(Long aLong, List<Pair<Long, Long>> pairs, AtomicInteger atomicInteger) {
		for (var pair : pairs) {
			if (aLong >= pair.getLeft() && aLong <= pair.getRight()) {
				atomicInteger.incrementAndGet();
				return;
			}
		}
	}

	@Override
	protected String getSolution(AtomicInteger atomicInteger) {
		return atomicInteger.toString();
	}
}
