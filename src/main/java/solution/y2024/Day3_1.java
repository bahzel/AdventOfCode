package solution.y2024;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

import org.apache.commons.lang3.tuple.Pair;

import utils.soution.InstructionSolution;

public class Day3_1 extends InstructionSolution<Pair<Long, Long>, AtomicLong> {
	private final Pattern pattern = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)");

	public static void main(String[] args) {
		new Day3_1().solve();
	}

	@Override
	protected AtomicLong initializeValue() {
		return new AtomicLong();
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		var instructionList = new ArrayList<String>();

		for (var instruction : instructions) {
			var matcher = pattern.matcher(instruction);

			while (matcher.find()) {
				instructionList.add(matcher.group(0));
			}
		}

		return instructionList;
	}

	@Override
	protected Pair<Long, Long> transformInstruction(String instruction) {
		var instructions = instruction.replace("mul(", "").replace(")", "").split(",");
		return Pair.of(Long.parseLong(instructions[0]), Long.parseLong(instructions[1]));
	}

	@Override
	protected boolean performInstruction(Pair<Long, Long> longLongPair, AtomicLong atomicLong) {
		atomicLong.addAndGet(longLongPair.getLeft() * longLongPair.getRight());

		return false;
	}

	@Override
	protected String getSolution(AtomicLong atomicLong) {
		return atomicLong.toString();
	}
}
