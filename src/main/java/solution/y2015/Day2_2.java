package solution.y2015;

import utils.soution.InstructionSolution;

import java.util.concurrent.atomic.AtomicLong;

public class Day2_2 extends InstructionSolution<PresentWithBow, AtomicLong> {
	public static void main(String[] args) {
		new Day2_2().solve();
	}

	@Override
	protected AtomicLong initializeValue() {
		return new AtomicLong();
	}

	@Override
	protected PresentWithBow transformInstruction(String instruction) {
		return new PresentWithBow(instruction);
	}

	@Override
	protected boolean performInstruction(PresentWithBow present, AtomicLong value) {
		value.addAndGet(present.getBowSize());
		return false;
	}

	@Override
	protected String getSolution(AtomicLong value) {
		return value.toString();
	}
}

class PresentWithBow {
	private final long length;
	private final long width;
	private final long height;

	public PresentWithBow(String size) {
		var sizes = size.split("x");
		length = Long.parseLong(sizes[0]);
		width = Long.parseLong(sizes[1]);
		height = Long.parseLong(sizes[2]);
	}

	public long getBowSize() {
		return Math.min(Math.min(2 * length + 2 * width, 2 * width + 2 * height), 2 * length + 2 * height)
				+ length * width * height;
	}
}
