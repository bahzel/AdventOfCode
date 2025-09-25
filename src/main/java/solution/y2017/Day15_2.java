package solution.y2017;

import utils.soution.Solution;

public class Day15_2 extends Solution {
	public static void main(String[] args) {
		new Day15_2().solve();
	}

	@Override
	protected String doSolve() {
		var count = 0;

		var generatorA = new PickyGenerator(16807, Integer.parseInt(input.getFirst().split(" ")[4]), 4);
		var generatorB = new PickyGenerator(48271, Integer.parseInt(input.get(1).split(" ")[4]), 8);

		for (var i = 0L; i < 5000000; i++) {
			generatorA.computeNext();
			generatorB.computeNext();
			if (generatorA.compare(generatorB)) {
				count++;
			}
		}

		return count + "";
	}
}

class PickyGenerator extends Generator {
	private final long multipleOf;

	public PickyGenerator(long factor, long value, long multipleOf) {
		super(factor, value);
		this.multipleOf = multipleOf;
	}

	public void computeNext() {
		do {
			value = (value * factor) % 2147483647;
		} while ((value % multipleOf) != 0);
	}
}
