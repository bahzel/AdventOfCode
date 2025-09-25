package solution.y2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import utils.soution.Solution;

public class Day17_1 extends Solution {
	public static void main(String[] args) {
		new Day17_1().solve();
	}

	@Override
	protected String doSolve() {
		var register = initializeRegister();
		var program = initializeProgram();
		var output = new ArrayList<Integer>();

		for (var i = 0; i < program.size(); i = i + 2) {
			switch (program.get(i)) {
			case 0:
				register[0] = (int) (register[0] / Math.pow(2, getComboOperand(register, program.get(i + 1))));
				break;
			case 1:
				register[1] = register[1] ^ program.get(i + 1);
				break;
			case 2:
				register[1] = getComboOperand(register, program.get(i + 1)) % 8;
				break;
			case 3:
				if (register[0] != 0) {
					i = program.get(i + 1) - 2;
				}
				break;
			case 4:
				register[1] = register[1] ^ register[2];
				break;
			case 5:
				var outputValue = getComboOperand(register, program.get(i + 1)) % 8;
				output.add(outputValue);
				break;
			case 6:
				register[1] = (int) (register[0] / Math.pow(2, getComboOperand(register, program.get(i + 1))));
				break;
			case 7:
				register[2] = (int) (register[0] / Math.pow(2, getComboOperand(register, program.get(i + 1))));
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + program.get(i));
			}
		}

		return StringUtils.join(output, ",");
	}

	private int[] initializeRegister() {
		int[] register = new int[3];
		register[0] = Integer.parseInt(input.getFirst().split(": ")[1]);
		register[1] = Integer.parseInt(input.get(1).split(": ")[1]);
		register[2] = Integer.parseInt(input.get(2).split(": ")[1]);
		return register;
	}

	private List<Integer> initializeProgram() {
		return Arrays.stream(input.get(4).split(": ")[1].split(",")).map(Integer::parseInt).toList();
	}

	private int getComboOperand(int[] register, int operand) {
		return switch (operand) {
		case 0 -> 0;
		case 1 -> 1;
		case 2 -> 2;
		case 3 -> 3;
		case 4 -> register[0];
		case 5 -> register[1];
		case 6 -> register[2];
		default -> throw new IllegalArgumentException("Invalid operand: " + operand);
		};
	}
}
