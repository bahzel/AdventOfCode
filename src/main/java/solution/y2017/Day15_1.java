package solution.y2017;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import utils.soution.Solution;

public class Day15_1 extends Solution {
	public static void main(String[] args) {
		new Day15_1().solve();
	}

	@Override
	protected String doSolve() {
		var count = 0;

		var generatorA = new Generator(16807, Integer.parseInt(input.getFirst().split(" ")[4]));
		var generatorB = new Generator(48271, Integer.parseInt(input.get(1).split(" ")[4]));

		for (var i = 0L; i < 40000000; i++) {
			generatorA.computeNext();
			generatorB.computeNext();
			if (generatorA.compare(generatorB)) {
				count++;
			}
		}

		return count + "";
	}
}

@AllArgsConstructor
class Generator {
	protected final long factor;
	protected long value;

	public void computeNext() {
		value = (value * factor) % 2147483647;
	}

	public boolean compare(Generator other) {
		var binaryThis = StringUtils.leftPad(Long.toBinaryString(value), 16, '0');
		var binaryOther = StringUtils.leftPad(Long.toBinaryString(other.value), 16, '0');

		return binaryThis.substring(binaryThis.length() - 16).equals(binaryOther.substring(binaryOther.length() - 16));
	}
}
