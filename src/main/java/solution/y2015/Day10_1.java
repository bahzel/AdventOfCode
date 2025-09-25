package solution.y2015;

import utils.InstructionSolution;
import utils.Solution;

import java.util.Arrays;
import java.util.List;

public class Day10_1 extends Solution {
	public static void main(String[] args) {
		new Day10_1().solve();
	}

	@Override
	protected String doSolve() {
		String value = input.getFirst();

		for (int i = 0; i < 40; i++) {
			value = new LookAndSay(value).solve();
		}

		return value.length() + "";
	}
}

class LookAndSay extends InstructionSolution<String, StringBuilder> {
	public LookAndSay(String input) {
		super(List.of(input));
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		return Arrays.asList(instructions.getFirst().split("(?<=(.))(?!\\1)"));
	}

	@Override
	protected StringBuilder initializeValue() {
		return new StringBuilder();
	}

	@Override
	protected String transformInstruction(String instruction) {
		return instruction;
	}

	@Override
	protected void performInstruction(String s, StringBuilder stringBuilder) {
		stringBuilder.append(s.length());
		stringBuilder.append(s.charAt(0));
	}

	@Override
	protected String getSolution(StringBuilder stringBuilder) {
		return stringBuilder.toString();
	}
}
