package solution.y2022;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.commons.lang3.tuple.Triple;

import utils.soution.InstructionSolution;

public class Day5_1 extends InstructionSolution<Triple<Integer, Integer, Integer>, List<Stack<String>>> {
	public static void main(String[] args) {
		new Day5_1().solve();
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		return instructions.subList(instructions.indexOf("") + 1, instructions.size());
	}

	@Override
	protected List<Stack<String>> initializeValue() {
		var value = new ArrayList<Stack<String>>();
		for (var i = 0; i < (input.getFirst().length() + 1) / 4; i++) {
			value.add(new Stack<>());
		}

		for (var line = input.indexOf("") - 2; line >= 0; line--) {
			for (var stackIndex = 1; stackIndex < input.get(line).length(); stackIndex += 4) {
				if (input.get(line).charAt(stackIndex) != ' ') {
					value.get(stackIndex / 4).push(input.get(line).charAt(stackIndex) + "");
				}
			}
		}

		return value;
	}

	@Override
	protected Triple<Integer, Integer, Integer> transformInstruction(String instruction) {
		var instructions = instruction.split(" ");
		return Triple.of(Integer.parseInt(instructions[1]), Integer.parseInt(instructions[3]) - 1,
				Integer.parseInt(instructions[5]) - 1);
	}

	@Override
	protected boolean performInstruction(Triple<Integer, Integer, Integer> integerIntegerIntegerTriple,
			List<Stack<String>> stacks) {
		for (var i = 0; i < integerIntegerIntegerTriple.getLeft(); i++) {
			stacks	.get(integerIntegerIntegerTriple.getRight())
					.push(stacks.get(integerIntegerIntegerTriple.getMiddle()).pop());
		}
		return false;
	}

	@Override
	protected String getSolution(List<Stack<String>> stacks) {
		StringBuilder solution = new StringBuilder();
		for (var stack : stacks) {
			solution.append(stack.pop());
		}
		return solution.toString();
	}
}
