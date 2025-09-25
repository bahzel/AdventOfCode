package solution.y2019;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.awaitility.Awaitility.await;

public class IntCodeInterpreter {
	private IntCodeInterpreter() {
	}

	public static void performComputation(int[] register) {
		performComputation(register, new ConcurrentLinkedQueue<>(), new ConcurrentLinkedQueue<>());
	}

	public static void performComputation(int[] register, int input, ConcurrentLinkedQueue<Integer> output) {
		performComputation(register, new ConcurrentLinkedQueue<>(List.of(input)), output);
	}

	public static void performComputation(int[] register, ConcurrentLinkedQueue<Integer> input,
			ConcurrentLinkedQueue<Integer> output) {
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
				if (input.isEmpty()) {
					//System.out.println("Wait for input");
					await().until(() -> !input.isEmpty());
					//System.out.println("Got input");
				}

				//System.out.println("Poll input");
				register[register[i + 1]] = input.poll();
				i = i + 2;
				break;
			case 4:
				//System.out.println("Write output");
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
				return;
			default:
				throw new IllegalStateException("Unexpected value: " + register[i]);
			}
		}
	}

	private static int getValue(int[] register, int index, int mode) {
		return mode == 1 ? register[index] : register[register[index]];
	}
}
