package solution.y2015;

import utils.InstructionSolution;

import java.util.concurrent.atomic.AtomicLong;

public class Day2_1 extends InstructionSolution<Present, AtomicLong> {
	public static void main(String[] args) {
		new Day2_1().solve();
	}

	@Override
	protected AtomicLong initializeValue() {
		return new AtomicLong();
	}

	@Override
	protected Present transformInstruction(String instruction) {
		return new Present(instruction);
	}

	@Override
	protected boolean performInstruction(Present present, AtomicLong value) {
		value.addAndGet(present.getPaperSize());
		return false;
	}

	@Override
	protected String getSolution(AtomicLong value) {
		return value.toString();
	}
}

class Present {
	private final long length;
	private final long width;
	private final long height;

	public Present(String size) {
		var sizes = size.split("x");
		length = Long.parseLong(sizes[0]);
		width = Long.parseLong(sizes[1]);
		height = Long.parseLong(sizes[2]);
	}

	public long getPaperSize() {
		long x1 = length * width;
		long x2 = width * height;
		long x3 = height * length;
		return 2 * x1 + 2 * x2 + 2 * x3 + Math.min(Math.min(x1, x2), x3);
	}
}
