package solution.y2021;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import utils.soution.Solution;

public class Day18_2 extends Solution {
	public static void main(String[] args) {
		new Day18_2().solve();
	}

	@Override
	protected String doSolve() {
		var maxMagnitude = Long.MIN_VALUE;
		for (var number1 : input) {
			for (var number2 : input) {
				if (number1.equals(number2)) {
					continue;
				}

				maxMagnitude = Math.max(maxMagnitude, getMagnitude(reduce("[" + number1 + "," + number2 + "]")));
			}
		}
		return String.valueOf(maxMagnitude);
	}

	private final Pattern VALUE_PAIR = Pattern.compile("\\[\\d+,\\d+]");

	protected long getMagnitude(String snailfishNumber) {
		while (!StringUtils.isNumeric(snailfishNumber)) {
			var matcher = VALUE_PAIR.matcher(snailfishNumber);
			matcher.find();
			var valuePair = matcher.group();
			var values = valuePair.substring(1, valuePair.length() - 1).split(",");
			snailfishNumber = snailfishNumber.replace(valuePair,
					3 * Long.parseLong(values[0]) + 2 * Long.parseLong(values[1]) + "");
		}
		return Long.parseLong(snailfishNumber);
	}

	protected String reduce(String snailfishNumber) {
		String nextIteration = snailfishNumber;
		do {
			snailfishNumber = nextIteration;
			nextIteration = explode(nextIteration);
			nextIteration = split(nextIteration);
		} while (!nextIteration.equals(snailfishNumber));
		return nextIteration;
	}

	private String explode(String snailfishNumber) {
		var index = getIndexWithDepth5(snailfishNumber);
		while (index != -1) {
			var explodingPair = snailfishNumber	.substring(index + 1, getNextClosingBracket(snailfishNumber, index))
												.split(",");
			snailfishNumber = snailfishNumber.substring(0, index) + "0"
					+ snailfishNumber.substring(index + explodingPair[0].length() + explodingPair[1].length() + 3);
			snailfishNumber = addToTheRight(snailfishNumber, index, Integer.parseInt(explodingPair[1]));
			snailfishNumber = addToTheLeft(snailfishNumber, index, Integer.parseInt(explodingPair[0]));
			index = getIndexWithDepth5(snailfishNumber);
		}
		return snailfishNumber;
	}

	private String split(String snailfishNumber) {
		for (var i = 1; i < snailfishNumber.length(); i++) {
			if (StringUtils.isNumeric(snailfishNumber.substring(i - 1, i + 1))) {
				var amount = Integer.parseInt(snailfishNumber.substring(i - 1, i + 1));
				snailfishNumber = snailfishNumber.substring(0, i - 1) + "[" + amount / 2 + "," + (amount + 1) / 2 + "]"
						+ snailfishNumber.substring(i + 1);
				return snailfishNumber;
			}
		}
		return snailfishNumber;
	}

	private int getIndexWithDepth5(String snailfishNumber) {
		var depth = 0;
		for (var i = 0; i < snailfishNumber.length(); i++) {
			if (snailfishNumber.charAt(i) == '[') {
				depth++;
				if (depth == 5) {
					return i;
				}
			} else if (snailfishNumber.charAt(i) == ']') {
				depth--;
			}
		}
		return -1;
	}

	private int getNextClosingBracket(String snailfishNumber, int index) {
		for (var i = index; i < snailfishNumber.length(); i++) {
			if (snailfishNumber.charAt(i) == ']') {
				return i;
			}
		}
		throw new IllegalStateException();
	}

	private String addToTheLeft(String snailfishNumber, int index, int amount) {
		for (var maxIndex = index - 1; maxIndex > 0; maxIndex--) {
			if (StringUtils.isNumeric(snailfishNumber.charAt(maxIndex) + "")) {
				var minIndex = maxIndex;
				for (var j = maxIndex - 1; j > 0; j--) {
					if (StringUtils.isNumeric(snailfishNumber.charAt(j) + "")) {
						minIndex = j;
					} else {
						break;
					}
				}
				snailfishNumber = snailfishNumber.substring(0, minIndex)
						+ (Integer.parseInt(snailfishNumber.substring(minIndex, maxIndex + 1)) + amount)
						+ snailfishNumber.substring(maxIndex + 1);
				break;
			}
		}
		return snailfishNumber;
	}

	private String addToTheRight(String snailfishNumber, int index, int amount) {
		for (var minIndex = index + 1; minIndex < snailfishNumber.length(); minIndex++) {
			if (StringUtils.isNumeric(snailfishNumber.charAt(minIndex) + "")) {
				var maxIndex = minIndex;
				for (var j = minIndex + 1; j < snailfishNumber.length(); j++) {
					if (StringUtils.isNumeric(snailfishNumber.charAt(j) + "")) {
						maxIndex = j;
					} else {
						break;
					}
				}
				snailfishNumber = snailfishNumber.substring(0, minIndex)
						+ (Integer.parseInt(snailfishNumber.substring(minIndex, maxIndex + 1)) + amount)
						+ snailfishNumber.substring(maxIndex + 1);
				break;
			}
		}
		return snailfishNumber;
	}
}
