package solution.y2015;

import utils.soution.Solution;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day11_2 extends Solution {
	private final List<Character> BLACKLIST = List.of('i', 'o', 'l');

	public static void main(String[] args) {
		new Day11_2().solve();
	}

	public Day11_2() {
		super(List.of("hxbxxyzz"));
	}

	@Override
	protected String doSolve() {
		String passwordCandidate = input.getFirst();
		do {
			passwordCandidate = increment(passwordCandidate);
		} while (!isValid(passwordCandidate));

		return passwordCandidate;
	}

	private String increment(String input) {
		return incrementAtIndex(input, input.length() - 1);
	}

	private String incrementAtIndex(String input, int index) {
		if (index < 0) {
			return "a" + input;
		}

		var newValue = input.substring(0, index) + (char) (input.charAt(index) + 1) + input.substring(index + 1);
		if (newValue.charAt(index) == '{') {
			return incrementAtIndex(newValue.replace('{', 'a'), index - 1);
		} else {
			return newValue;
		}
	}

	private boolean isValid(String password) {
		return hasStraight(password) && !containsBlacklistItem(password) && countPairs(password) > 1;
	}

	private boolean hasStraight(String password) {
		var chars = password.toCharArray();
		for (int i = 0; i < chars.length - 2; i++) {
			if (chars[i] + 1 == chars[i + 1] && chars[i] + 2 == chars[i + 2]) {
				return true;
			}
		}
		return false;
	}

	private boolean containsBlacklistItem(String password) {
		for (char ch : password.toCharArray()) {
			if (BLACKLIST.contains(ch)) {
				return true;
			}
		}
		return false;
	}

	private int countPairs(String password) {
		Set<Character> pairs = new HashSet<>();

		var chars = password.toCharArray();
		for (int i = 0; i < chars.length - 1; i++) {
			if (chars[i] == chars[i + 1]) {
				pairs.add(chars[i]);
				i++;
			}
		}

		return pairs.size();
	}
}
