package solution.y2025;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.tuple.Pair;

import utils.soution.InstructionSolution;

public class Day2_1 extends InstructionSolution<Pair<Long, Long>, AtomicLong> {
	public static void main(String[] args) {
		new Day2_1().solve();
	}

	@Override
	protected AtomicLong initializeValue() {
		return new AtomicLong(0);
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		return Arrays.asList(instructions.getFirst().split(","));
	}

	@Override
	protected Pair<Long, Long> transformInstruction(String instruction) {
		var instructions = instruction.split("-");
		return Pair.of(Long.parseLong(instructions[0]), Long.parseLong(instructions[1]));
	}

	@Override
	protected boolean performInstruction(Pair<Long, Long> longLongPair, AtomicLong atomicInteger) {
		var from = longLongPair.getLeft();
		var to = longLongPair.getRight();

		var toCheck = from / pow10((from.toString().length() + 1) / 2);
		while (true) {
			var candidate = toCheck * (pow10(Long.toString(toCheck).length()) + 1);
			if (candidate > to) {
				break;
			} else if (candidate >= from) {
				atomicInteger.addAndGet(candidate);
			}
			toCheck++;
		}

		return false;
	}

	private long pow10(int amount) {
		var result = 1;
		for (var i = 0; i < amount; i++) {
			result *= 10;
		}
		return result;
	}

	@Override
	protected String getSolution(AtomicLong atomicInteger) {
		return atomicInteger.toString();
	}
}
