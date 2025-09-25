package solution.y2021;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.ListUtils;

import utils.soution.Solution;

public class Day4_1 extends Solution {
	public static void main(String[] args) {
		new Day4_1().solve();
	}

	@Override
	protected String doSolve() {
		var drawnNumbers = Arrays.stream(input.getFirst().split(",")).map(Integer::parseInt).toList();
		var cards = ListUtils	.partition(input.subList(1, input.size()), 6)
								.stream()
								.map(numbers -> new Bingo(numbers.subList(1, numbers.size())))
								.toList();
		for (var drawnNumber : drawnNumbers) {
			cards.forEach(card -> card.drawNumber(drawnNumber));
			var winner = cards.stream().filter(Bingo::isWinner).findAny();
			if (winner.isPresent()) {
				return winner.get().getScore() + "";
			}
		}
		throw new RuntimeException("No winner found");
	}
}

class Bingo {
	private final int[][] fields;
	private int lastNumber;

	public Bingo(List<String> data) {
		fields = new int[5][5];
		for (int i = 0; i < 5; i++) {
			var numbers = data.get(i).trim().replace("  ", " ").split(" ");
			for (int j = 0; j < 5; j++) {
				fields[i][j] = Integer.parseInt(numbers[j]);
			}
		}
	}

	public void drawNumber(int number) {
		lastNumber = number;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (fields[i][j] == number) {
					fields[i][j] = -1;
					return;
				}
			}
		}
	}

	public boolean isWinner() {
		for (var i = 0; i < 5; i++) {
			if (isRowWinner(i) || isColumnWinner(i)) {
				return true;
			}
		}
		return false;
	}

	private boolean isRowWinner(int row) {
		return fields[row][0] == -1 && fields[row][1] == -1 && fields[row][2] == -1 && fields[row][3] == -1
				&& fields[row][4] == -1;
	}

	private boolean isColumnWinner(int column) {
		return fields[0][column] == -1 && fields[1][column] == -1 && fields[2][column] == -1 && fields[3][column] == -1
				&& fields[4][column] == -1;
	}

	public long getScore() {
		var score = 0L;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (fields[i][j] > -1) {
					score += fields[i][j];
				}
			}
		}
		return score * lastNumber;
	}
}
