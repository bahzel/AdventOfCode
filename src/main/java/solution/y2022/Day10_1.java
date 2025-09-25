package solution.y2022;

import java.util.List;
import java.util.function.Consumer;

import utils.soution.InstructionSolution;

public class Day10_1 extends InstructionSolution<Consumer<int[]>, int[]> {
	private final List<Integer> COUNT_CYCLES = List.of(20, 60, 100, 140, 180, 220);

	public static void main(String[] args) {
		new Day10_1().solve();
	}

	@Override
	protected int[] initializeValue() {
		return new int[] { 0, 1, 0 };
	}

	@Override
	protected Consumer<int[]> transformInstruction(String instruction) {
		var instructions = instruction.split(" ");
		return switch (instructions[0]) {
		case "noop" -> register -> {
			register[0]++;
			if (COUNT_CYCLES.contains(register[0])) {
				register[2] += register[1] * register[0];
			}
		};
		case "addx" -> register -> {
			register[0] += 2;
			if (COUNT_CYCLES.contains(register[0])) {
				register[2] += register[1] * register[0];
			} else if (COUNT_CYCLES.contains(register[0] - 1)) {
				register[2] += register[1] * (register[0] - 1);
			}
			register[1] += Integer.parseInt(instructions[1]);
		};
		default -> throw new IllegalStateException("Unexpected value: " + instructions[0]);
		};
	}

	@Override
	protected boolean performInstruction(Consumer<int[]> consumer, int[] ints) {
		consumer.accept(ints);
		return false;
	}

	@Override
	protected String getSolution(int[] ints) {
		return ints[2] + "";
	}
}
