package solution.y2015;

import utils.InstructionQueue;
import utils.Solution;

import java.util.List;

public class Day1_2 extends Solution {
	private int floor = 0;
	private int index = 0;

	public static void main(String[] args) {
		new Day1_2().solve();
	}

	@Override
	protected String solve(List<String> input) {
		var instructionQueue = new InstructionQueue(input.getFirst());
		while (instructionQueue.peek() != null) {
			performInstruction(instructionQueue.poll());
			index++;

			if (floor < 0) {
				return index + "";
			}
		}

		return "ERROR";
	}

	protected void performInstruction(Character instruction) {
		switch (instruction) {
		case '(':
			floor++;
			break;
		case ')':
			floor--;
			break;
		default:
			throw new IllegalArgumentException("Invalid instruction: " + instruction);
		}
	}
}
