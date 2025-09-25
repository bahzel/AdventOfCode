package solution.y2019;

import java.util.ArrayList;
import java.util.List;

public class IntCodeInterpreter {
	private IntCodeInterpreter() {
	}

	public static List<Integer> performComputation(int[] register) {
		return performComputation(register, 0);
	}

	public static List<Integer> performComputation(int[] register, int input) {
		var output = new ArrayList<Integer>();

		for (int i = 0; i < register.length; ) {
			var opCode = register[i] % 100;
			var modeFirstParameter = (register[i] / 100) % 10;
			var modeSecondParameter = (register[i] / 1000) % 10;
			var modeThirdParameter = (register[i] / 10000) % 10;

			switch (opCode) {
			case 1:
				register[register[i + 3]] =
						getValue(register, i + 1, modeFirstParameter) + getValue(register, i + 2, modeSecondParameter);
				i = i + 4;
				break;
			case 2:
				register[register[i + 3]] =
						getValue(register, i + 1, modeFirstParameter) * getValue(register, i + 2, modeSecondParameter);
				i = i + 4;
				break;
			case 3:
				register[register[i + 1]] = input;
				i = i + 2;
				break;
			case 4:
				output.add(getValue(register, i + 1, modeFirstParameter));
				i = i + 2;
				break;
			case 5:
				if (getValue(register, i + 1, modeFirstParameter) != 0) {
					i = getValue(register, i + 2, modeSecondParameter);
				} else {
					i = i + 3;
				}
				break;
			case 6:
				if (getValue(register, i + 1, modeFirstParameter) == 0) {
					i = getValue(register, i + 2, modeSecondParameter);
				} else {
					i = i + 3;
				}
				break;
			case 7:
				register[register[i + 3]] =
						getValue(register, i + 1, modeFirstParameter) < getValue(register, i + 2, modeSecondParameter)
								? 1
								: 0;
				i = i + 4;
				break;
			case 8:
				register[register[i + 3]] =
						getValue(register, i + 1, modeFirstParameter) == getValue(register, i + 2, modeSecondParameter)
								? 1
								: 0;
				i = i + 4;
				break;
			case 99:
				return output;
			default:
				throw new IllegalStateException("Unexpected value: " + register[i]);
			}
		}

		return output;
	}

	private static int getValue(int[] register, int index, int mode) {
		return mode == 1 ? register[index] : register[register[index]];
	}
}
