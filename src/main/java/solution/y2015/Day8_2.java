package solution.y2015;

import org.apache.commons.lang3.tuple.Pair;
import utils.soution.InstructionSolution;

import java.util.concurrent.atomic.AtomicLong;

public class Day8_2 extends InstructionSolution<Pair<String, String>, AtomicLong> {
	public static void main(String[] args) {
		new Day8_2().solve();
	}

	@Override
	protected AtomicLong initializeValue() {
		return new AtomicLong();
	}

	@Override
	protected Pair<String, String> transformInstruction(String instruction) {
		var transformedString = new StringBuilder("\"");

		for (int i = 0; i < instruction.length(); i++) {
			var ch = instruction.charAt(i);
			if ('\"' == ch || '\\' == ch) {
				transformedString.append('\\');
			}
			transformedString.append(ch);
		}
		transformedString.append('\"');

		return Pair.of(instruction, transformedString.toString());
	}

	@Override
	protected boolean performInstruction(Pair<String, String> instruction, AtomicLong value) {
		value.addAndGet(instruction.getRight().length() - instruction.getLeft().length());
		return false;
	}

	@Override
	protected String getSolution(AtomicLong value) {
		return value.toString();
	}
}
