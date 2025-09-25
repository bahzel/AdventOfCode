package solution.y2021;

import utils.soution.Solution;

public class Day21_1 extends Solution {
	private static final long WINNING_SCORE = 1000L;

	public static void main(String[] args) {
		new Day21_1().solve();
	}

	@Override
	protected String doSolve() {
		var position1 = Long.parseLong(input.getFirst().split(" ")[4]) - 1;
		var position2 = Long.parseLong(input.get(1).split(" ")[4]) - 1;
		var score1 = 0L;
		var score2 = 0L;
		var rollCount = 0L;
		while (true) {
			rollCount++;
			var diceValue = getDiceValue(rollCount);
			rollCount++;
			diceValue += getDiceValue(rollCount);
			rollCount++;
			diceValue += getDiceValue(rollCount);
			position1 += diceValue;
			position1 %= 10;
			score1 += position1 + 1;
			if (score1 >= WINNING_SCORE) {
				return rollCount * score2 + "";
			}

			rollCount++;
			diceValue = getDiceValue(rollCount);
			rollCount++;
			diceValue += getDiceValue(rollCount);
			rollCount++;
			diceValue += getDiceValue(rollCount);
			position2 += diceValue;
			position2 %= 10;
			score2 += position2 + 1;
			if (score2 >= WINNING_SCORE) {
				return rollCount * score1 + "";
			}
		}
	}

	private long getDiceValue(long rollCount) {
		var value = rollCount % 100;
		if (value == 0) {
			return 100;
		}
		return value;
	}
}
