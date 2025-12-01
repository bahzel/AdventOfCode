package solution.y2025;

import java.util.concurrent.atomic.AtomicInteger;

public class Day1_2 extends Day1_1 {
	public static void main(String[] args) {
		new Day1_2().solve();
	}

	@Override
	protected void performInstruction(Integer integer, AtomicInteger value, AtomicInteger solution) {
		value.addAndGet(integer);
		while (value.get() < 0) {
			value.addAndGet(100);
			solution.incrementAndGet();
		}
		while (value.get() > 99) {
			value.addAndGet(-100);
			solution.incrementAndGet();
		}
	}
}
