package solution.y2015;

import utils.InstructionSolution;

import java.util.List;

public class Day1_1 extends InstructionSolution {
	private int floor = 0;

	public static void main(String[] args) {
		new Day1_1().solve();
	}

	@Override
	protected String solve(List<String> input) {
		iterate(input.getFirst());
		return floor + "";
	}

	@Override
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
