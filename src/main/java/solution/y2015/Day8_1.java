package solution.y2015;

import org.apache.commons.lang3.tuple.Pair;
import utils.InstructionSolution;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Day8_1 extends InstructionSolution<Pair<String, String>, AtomicLong> {
	private static final List<Character> HEX_CHARACTERS = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', 'a', 'b', 'c', 'd', 'e', 'f');

	public static void main(String[] args) {
		new Day8_1().solve();
	}

	@Override
	protected AtomicLong initializeValue() {
		return new AtomicLong();
	}

	@Override
	protected Pair<String, String> transformInstruction(String instruction) {
		var transformedString = new StringBuilder();

		for (int i = 0; i < instruction.length(); i++) {
			var ch = instruction.charAt(i);
			if ('\"' == ch) {
				if (i == 0 || i == instruction.length() - 1) {
					continue;
				}
			} else if ('\\' == ch) {
				var nextChar = instruction.charAt(i + 1);
				if ('\\' == nextChar || '\"' == nextChar) {
					transformedString.append(nextChar);
					i++;
					continue;
				} else if ('x' == nextChar) {
					if (i + 3 < instruction.length() && HEX_CHARACTERS.contains(instruction.charAt(i + 2))
							&& HEX_CHARACTERS.contains(instruction.charAt(i + 3))) {
						transformedString.append(nextChar); //placeholder
						i = i + 3;
						continue;
					}
				}
			}
			transformedString.append(ch);
		}

		return Pair.of(instruction, transformedString.toString());
	}

	@Override
	protected boolean performInstruction(Pair<String, String> instruction, AtomicLong value) {
		value.addAndGet(instruction.getLeft().length() - instruction.getRight().length());
		return false;
	}

	@Override
	protected String getSolution(AtomicLong value) {
		return value.toString();
	}
}
