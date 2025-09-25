package solution.y2019;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.awaitility.Awaitility.await;

public class IntCodeInterpreter {
	private IntCodeInterpreter() {
	}

	public static void performComputation(List<Long> register) {
		performComputation(register, new ConcurrentLinkedQueue<>(), new ConcurrentLinkedQueue<>());
	}

	public static void performComputation(List<Long> register, long input, ConcurrentLinkedQueue<Long> output) {
		performComputation(register, new ConcurrentLinkedQueue<>(List.of(input)), output);
	}

	public static void performComputation(List<Long> register, ConcurrentLinkedQueue<Long> input,
			ConcurrentLinkedQueue<Long> output) {
		var offset = 0L;

		for (var i = 0; ; ) {
			var opCode = (int) (register.get(i) % 100);
			var modeFirstParameter = (int) (get(register, i) / 100) % 10;
			var modeSecondParameter = (int) (get(register, i) / 1000) % 10;
			var modeThirdParameter = (int) (get(register, i) / 10000) % 10;

			switch (opCode) {
			case 1:
				set(register, get(register, i + 3), modeThirdParameter, offset,
						getValue(register, i + 1, modeFirstParameter, offset) + getValue(register, i + 2,
								modeSecondParameter, offset));
				i = i + 4;
				break;
			case 2:
				set(register, get(register, i + 3), modeThirdParameter, offset,
						getValue(register, i + 1, modeFirstParameter, offset) * getValue(register, i + 2,
								modeSecondParameter, offset));
				i = i + 4;
				break;
			case 3:
				if (input.isEmpty()) {
					await().until(() -> !input.isEmpty());
				}

				set(register, get(register, i + 1), modeFirstParameter, offset, input.poll());
				i = i + 2;
				break;
			case 4:
				output.add(getValue(register, i + 1, modeFirstParameter, offset));
				i = i + 2;
				break;
			case 5:
				if (getValue(register, i + 1, modeFirstParameter, offset) != 0) {
					i = (int) getValue(register, i + 2, modeSecondParameter, offset);
				} else {
					i = i + 3;
				}
				break;
			case 6:
				if (getValue(register, i + 1, modeFirstParameter, offset) == 0) {
					i = (int) getValue(register, i + 2, modeSecondParameter, offset);
				} else {
					i = i + 3;
				}
				break;
			case 7:
				set(register, get(register, i + 3), modeThirdParameter, offset,
						getValue(register, i + 1, modeFirstParameter, offset) < getValue(register, i + 2,
								modeSecondParameter, offset) ? 1L : 0L);
				i = i + 4;
				break;
			case 8:
				set(register, get(register, i + 3), modeThirdParameter, offset,
						getValue(register, i + 1, modeFirstParameter, offset) == getValue(register, i + 2,
								modeSecondParameter, offset) ? 1L : 0L);
				i = i + 4;
				break;
			case 9:
				offset += getValue(register, i + 1, modeFirstParameter, offset);
				i = i + 2;
				break;
			case 99:
				return;
			default:
				throw new IllegalStateException("Unexpected value: " + get(register, i));
			}
		}
	}

	private static long getValue(List<Long> register, int index, int mode, long offset) {
		return switch (mode) {
			case 0 -> get(register, get(register, index));
			case 1 -> get(register, index);
			case 2 -> get(register, get(register, index) + offset);
			default -> throw new IllegalStateException("Unexpected value: " + get(register, index));
		};
	}

	private static long get(List<Long> register, long index) {
		if (register.size() > index) {
			return register.get((int) index);
		} else {
			return 0;
		}
	}

	private static void set(List<Long> register, long index, int mode, long offset, long value) {
		var actualIndex = index + (mode == 2 ? offset : 0);

		while (register.size() <= actualIndex) {
			register.add(0L);
		}
		register.set((int) actualIndex, value);
	}
}
