package solution.y2022;

import java.util.Arrays;
import java.util.stream.Collectors;

import utils.StringTransformer;
import utils.soution.InstructionSolution;

public class Day25_1 extends InstructionSolution<int[], int[]> {
	public static void main(String[] args) {
		new Day25_1().solve();
	}

	@Override
	protected int[] initializeValue() {
		return new int[50];
	}

	@Override
	protected int[] transformInstruction(String instruction) {
		var number = new int[instruction.length()];
		var instructions = StringTransformer.splitString(instruction);
		for (var i = 0; i < instructions.size(); i++) {
			switch (instructions.get(i)) {
			case "2" -> number[i] = 2;
			case "1" -> number[i] = 1;
			case "0" -> number[i] = 0;
			case "-" -> number[i] = -1;
			case "=" -> number[i] = -2;
			default -> throw new IllegalArgumentException("Unknown instruction: " + instructions.get(i));
			}
		}
		return number;
	}

	@Override
	protected boolean performInstruction(int[] instruction, int[] value) {
		for (var i = 0; i < instruction.length; i++) {
			value[value.length - 1 - i] += instruction[instruction.length - 1 - i];
		}

		var changed = true;
		while (changed) {
			changed = false;
			for (var i = value.length - 1; i >= 0; i--) {
				if (value[i] > 2) {
					value[i] -= 5;
					value[i - 1]++;
					changed = true;
				} else if (value[i] < -2) {
					value[i] += 5;
					value[i - 1]--;
					changed = true;
				}
			}
		}

		return false;
	}

	@Override
	protected String getSolution(int[] integers) {
		var solution = Arrays.stream(integers).mapToObj(digit -> switch (digit) {
		case 2 -> "2";
		case 1 -> "1";
		case 0 -> "0";
		case -1 -> "-";
		case -2 -> "=";
		default -> throw new IllegalArgumentException("Unknown integer digit: " + digit);
		}).collect(Collectors.joining());
		var i = 0;
		for (; i < solution.length(); i++) {
			if (solution.charAt(i) != '0') {
				break;
			}
		}
		return solution.substring(i);
	}
}