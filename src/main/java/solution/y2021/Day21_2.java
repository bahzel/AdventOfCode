package solution.y2021;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import utils.soution.Solution;

public class Day21_2 extends Solution {
	private static final long WINNING_SCORE = 21;
	private static final List<Pair<Integer, Integer>> DICE_THROWS = List.of(Pair.of(9, 1), Pair.of(8, 3), Pair.of(7, 6),
			Pair.of(6, 7), Pair.of(5, 6), Pair.of(4, 3), Pair.of(3, 1));

	public static void main(String[] args) {
		new Day21_2().solve();
	}

	@Override
	protected String doSolve() {
		var position1 = Integer.parseInt(input.getFirst().split(" ")[4]) - 1;
		var position2 = Integer.parseInt(input.get(1).split(" ")[4]) - 1;
		var winners = getWinners(position1, position2, 0, 0);
		return Math.max(winners.getLeft(), winners.getRight()) + "";
	}

	private Pair<Long, Long> getWinners(int position1, int position2, int score1, int score2) {
		var wins1 = 0L;
		var wins2 = 0L;

		for (var diceThrow1 : DICE_THROWS) {
			var nextPosition1 = (position1 + diceThrow1.getLeft()) % 10;
			var nextScore1 = score1 + nextPosition1 + 1;
			if (nextScore1 >= WINNING_SCORE) {
				wins1 += diceThrow1.getRight();
				continue;
			}

			for (var diceThrow2 : DICE_THROWS) {
				var nextPosition2 = (position2 + diceThrow2.getLeft()) % 10;
				var nextScore2 = score2 + nextPosition2 + 1;
				if (nextScore2 >= WINNING_SCORE) {
					wins2 += (long) diceThrow1.getRight() * diceThrow2.getRight();
					continue;
				}

				var nextWinner = getWinners(nextPosition1, nextPosition2, nextScore1, nextScore2);
				wins1 += diceThrow1.getRight() * diceThrow2.getRight() * nextWinner.getLeft();
				wins2 += diceThrow1.getRight() * diceThrow2.getRight() * nextWinner.getRight();
			}
		}

		return Pair.of(wins1, wins2);
	}
}
