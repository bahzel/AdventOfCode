package solution.y2015;

import org.apache.commons.lang3.tuple.MutablePair;
import utils.InstructionSolution;
import utils.StringTransformer;

import java.util.List;

public class Day1_2 extends InstructionSolution<String, MutablePair<Long, Long>> {
	public static void main(String[] args) {
		new Day1_2().solve();
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		return StringTransformer.fromString(instructions.getFirst());
	}

	@Override
	protected MutablePair<Long, Long> initializeValue() {
		return MutablePair.of(0L, 0L);
	}

	@Override
	protected String transformInstruction(String instruction) {
		return instruction;
	}

	@Override
	protected void performInstruction(String instruction, MutablePair<Long, Long> value) {
		if (value.getLeft() < 0) {
			return;
		}

		switch (instruction) {
		case "(":
			value.setLeft(value.getLeft() + 1);
			break;
		case ")":
			value.setLeft(value.getLeft() - 1);
			break;
		default:
			throw new IllegalArgumentException("Invalid instruction: " + instruction);
		}

		value.setRight(value.getRight() + 1);
	}

	@Override
	protected String getSolution(MutablePair<Long, Long> value) {
		return value.getRight() + "";
	}
}
