package solution.y2025;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

public class Day2_2 extends Day2_1 {
	public static void main(String[] args) {
		new Day2_2().solve();
	}

	@Override
	protected boolean performInstruction(Pair<Long, Long> longLongPair, AtomicLong atomicInteger) {
		var from = longLongPair.getLeft();
		var to = longLongPair.getRight();

		for (var i = from; i <= to; i++) {
			if (checkNumber(i)) {
				atomicInteger.addAndGet(i);
			}
		}

		return false;
	}

	private boolean checkNumber(long number) {
		var numberString = String.valueOf(number);
		var length = numberString.length();

		for (var digits = 1; digits <= length / 2; digits++) {
			if (length % digits != 0) {
				continue;
			}

			if (StringUtils.repeat(numberString.substring(0, digits), length / digits).equals(numberString)) {
				return true;
			}
		}
		return false;
	}
}
