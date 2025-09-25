package solution.y2019;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import lombok.Getter;

@Getter
public class IntCodeInterpreter {
	private List<Long> register;
	private BlockingQueue<Long> input = new LinkedBlockingQueue<>();
	private BlockingQueue<Long> output = new LinkedBlockingQueue<>();
	private final BlockingQueue<Boolean> status = new LinkedBlockingQueue<>();

	public IntCodeInterpreter withRegister(List<Long> register) {
		this.register = register;
		return this;
	}

	public IntCodeInterpreter withInput(long input) {
		this.input = new LinkedBlockingQueue<>(List.of(input));
		return this;
	}

	public IntCodeInterpreter withInput(BlockingQueue<Long> input) {
		this.input = input;
		return this;
	}

	public IntCodeInterpreter withOutput(BlockingQueue<Long> output) {
		this.output = output;
		return this;
	}

	public void performComputation() {
		var offset = 0L;

		for (var i = 0; ; ) {
			var opCode = (int) (register.get(i) % 100);
			var modeFirstParameter = (int) (get(i) / 100) % 10;
			var modeSecondParameter = (int) (get(i) / 1000) % 10;
			var modeThirdParameter = (int) (get(i) / 10000) % 10;

			switch (opCode) {
			case 1:
				set(get(i + 3), modeThirdParameter, offset,
						getValue(i + 1, modeFirstParameter, offset) + getValue(i + 2, modeSecondParameter, offset));
				i = i + 4;
				break;
			case 2:
				set(get(i + 3), modeThirdParameter, offset,
						getValue(i + 1, modeFirstParameter, offset) * getValue(i + 2, modeSecondParameter, offset));
				i = i + 4;
				break;
			case 3:
				if (input.isEmpty()) {
					status.add(true);
				}
				try {
					set(get(i + 1), modeFirstParameter, offset, input.take());
				} catch (InterruptedException e) {
					return;
				}
				i = i + 2;
				break;
			case 4:
				try {
					output.put(getValue(i + 1, modeFirstParameter, offset));
				} catch (InterruptedException e) {
					return;
				}
				i = i + 2;
				break;
			case 5:
				if (getValue(i + 1, modeFirstParameter, offset) != 0) {
					i = (int) getValue(i + 2, modeSecondParameter, offset);
				} else {
					i = i + 3;
				}
				break;
			case 6:
				if (getValue(i + 1, modeFirstParameter, offset) == 0) {
					i = (int) getValue(i + 2, modeSecondParameter, offset);
				} else {
					i = i + 3;
				}
				break;
			case 7:
				set(get(i + 3), modeThirdParameter, offset,
						getValue(i + 1, modeFirstParameter, offset) < getValue(i + 2, modeSecondParameter, offset)
								? 1L
								: 0L);
				i = i + 4;
				break;
			case 8:
				set(get(i + 3), modeThirdParameter, offset,
						getValue(i + 1, modeFirstParameter, offset) == getValue(i + 2, modeSecondParameter, offset)
								? 1L
								: 0L);
				i = i + 4;
				break;
			case 9:
				offset += getValue(i + 1, modeFirstParameter, offset);
				i = i + 2;
				break;
			case 99:
				status.add(false);
				return;
			default:
				throw new IllegalStateException("Unexpected value: " + get(i));
			}
		}
	}

	private long getValue(int index, int mode, long offset) {
		return switch (mode) {
			case 0 -> get(get(index));
			case 1 -> get(index);
			case 2 -> get(get(index) + offset);
			default -> throw new IllegalStateException("Unexpected value: " + get(index));
		};
	}

	private long get(long index) {
		if (register.size() > index) {
			return register.get((int) index);
		} else {
			return 0;
		}
	}

	private void set(long index, int mode, long offset, long value) {
		var actualIndex = index + (mode == 2 ? offset : 0);

		while (register.size() <= actualIndex) {
			register.add(0L);
		}
		register.set((int) actualIndex, value);
	}
}
