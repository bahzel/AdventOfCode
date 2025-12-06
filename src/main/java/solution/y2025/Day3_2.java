package solution.y2025;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Day3_2 extends Day3_1 {
	public static void main(String[] args) {
		new Day3_2().solve();
	}

	@Override
	protected boolean performInstruction(List<Integer> integers, AtomicLong atomicLong) {
		atomicLong.addAndGet(getHighestNumber(integers, 12));

		return false;
	}

	private long getHighestNumber(List<Integer> integers, int digits) {
		var firstNumber = integers	.subList(0, integers.size() - digits + 1)
									.stream()
									.mapToInt(Integer::intValue)
									.max()
									.orElseThrow();
		if (digits == 1) {
			return firstNumber;
		}

		var secondNumber = getHighestNumber(integers.subList(integers.indexOf(firstNumber) + 1, integers.size()),
				digits - 1);
		return Long.parseLong(firstNumber + "" + secondNumber);
	}
}
