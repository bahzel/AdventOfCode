package solution.y2016;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import utils.soution.Solution;

public class Day14_1 extends Solution {
	private final Map<Long, String> CACHE = new HashMap<>();
	private String salt;

	public static void main(String[] args) {
		new Day14_1().solve();
	}

	@Override
	protected String doSolve() {
		salt = input.getFirst();
		int counter = 0;

		for (var i = 0L; i < Long.MAX_VALUE; i++) {
			var hash = CACHE.computeIfAbsent(i, this::computeHash);
			var tripleChar = getTriple(hash);
			if (tripleChar == null) {
				continue;
			}
			var quintuple = StringUtils.repeat(tripleChar, 5);

			for (var j = i + 1; j <= i + 1000; j++) {
				if (CACHE.computeIfAbsent(j, this::computeHash).contains(quintuple)) {
					counter++;
					break;
				}
			}

			if (counter == 64) {
				return i + "";
			}
		}

		return "";
	}

	private String computeHash(long index) {
		return DigestUtils.md5Hex(salt + index);
	}

	private final Pattern PATTERN_TRIPLE = Pattern.compile("(.)\\1{2}");

	private Character getTriple(String hash) {
		var matcher = PATTERN_TRIPLE.matcher(hash);
		if (matcher.find()) {
			return matcher.group(1).charAt(0);
		} else {
			return null;
		}
	}
}
