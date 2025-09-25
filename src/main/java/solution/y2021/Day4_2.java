package solution.y2021;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.collections4.ListUtils;

import utils.soution.Solution;

public class Day4_2 extends Solution {
	public static void main(String[] args) {
		new Day4_2().solve();
	}

	@Override
	protected String doSolve() {
		var drawnNumbers = Arrays.stream(input.getFirst().split(",")).map(Integer::parseInt).toList();
		var cards = new ArrayList<>(ListUtils	.partition(input.subList(1, input.size()), 6)
												.stream()
												.map(numbers -> new Bingo(numbers.subList(1, numbers.size())))
												.toList());
		for (var drawnNumber : drawnNumbers) {
			cards.forEach(card -> card.drawNumber(drawnNumber));
			if (cards.size() == 1) {
				if (cards.getFirst().isWinner()) {
					return cards.getFirst().getScore() + "";
				}
			} else {
				cards.removeIf(Bingo::isWinner);
			}
		}
		throw new RuntimeException("No winner found");
	}
}
