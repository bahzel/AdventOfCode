package solution.y2019;

import org.apache.commons.lang3.StringUtils;
import utils.StringTransformer;
import utils.soution.Solution;

public class Day16_2 extends Solution {
	private int inputLength;

	public static void main(String[] args) {
		new Day16_2().solve();
	}

	@Override
	protected String doSolve() {
		var currentRound = createInput();
		for (int i = 0; i < 100; i++) {
			currentRound = computeNextRound(currentRound);
		}
		return getScore(currentRound);
	}

	private int[] createInput() {
		int startIndex = Integer.parseInt(input.getFirst().substring(0, 7));
		var inputString = StringUtils.repeat(input.getFirst(), 10000).substring(startIndex);
		inputLength = inputString.length();
		return StringTransformer.splitString(inputString).stream().mapToInt(Integer::parseInt).toArray();
	}

	private int[] computeNextRound(int[] currentRound) {
		var nextRound = new int[inputLength];
		for (int i = 0; i < inputLength; i++) {
			var currentValue = 0;
			for (int j = i; j < inputLength; j++) {
				currentValue += currentRound[j];
			}
			nextRound[i] = Math.abs(currentValue) % 10;
		}
		return nextRound;
	}

	private String getScore(int[] currentRound) {
		var solution = new StringBuilder();
		for (int i = 0; i < 8; i++) {
			solution.append(currentRound[i]);
		}
		return solution.toString();
	}
}
