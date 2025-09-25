package solution.y2015;

import org.apache.commons.lang3.StringUtils;
import utils.InstructionSolution;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class Day5_1 extends InstructionSolution<String, AtomicInteger> {
	private final Pattern PATTERN_TWICE_IN_A_ROW = Pattern.compile("(.)\\1");
	private final List<String> BLACKLIST = Arrays.asList("ab", "cd", "pq", "xy");

	public static void main(String[] args) {
		new Day5_1().solve();
	}

	@Override
	protected AtomicInteger initializeValue() {
		return new AtomicInteger();
	}

	@Override
	protected String transformInstruction(String instruction) {
		return instruction;
	}

	@Override
	protected boolean performInstruction(String instruction, AtomicInteger value) {
		if (countVowels(instruction) < 3) {
			return false;
		}

		if (!PATTERN_TWICE_IN_A_ROW.matcher(instruction).find()) {
			return false;
		}

		if (!hasBlacklistItem(instruction)) {
			value.incrementAndGet();
		}
		return false;
	}

	private int countVowels(String instruction) {
		int vowels = 0;
		vowels += StringUtils.countMatches(instruction, "a");
		vowels += StringUtils.countMatches(instruction, "e");
		vowels += StringUtils.countMatches(instruction, "i");
		vowels += StringUtils.countMatches(instruction, "o");
		vowels += StringUtils.countMatches(instruction, "u");
		return vowels;
	}

	private boolean hasBlacklistItem(String instruction) {
		for (var blacklistItem : BLACKLIST) {
			if (instruction.contains(blacklistItem)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected String getSolution(AtomicInteger value) {
		return value.toString();
	}
}
