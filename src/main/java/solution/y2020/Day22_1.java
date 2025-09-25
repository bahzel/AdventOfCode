package solution.y2020;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import utils.soution.Solution;

public class Day22_1 extends Solution {
	public static void main(String[] args) {
		new Day22_1().solve();
	}

	@Override
	protected String doSolve() {
		Queue<Long> deck1 = new LinkedList<>(
				input.subList(1, input.indexOf("")).stream().map(Long::parseLong).toList());
		Queue<Long> deck2 = new LinkedList<>(
				input.subList(input.indexOf("") + 2, input.size()).stream().map(Long::parseLong).toList());

		while (!deck1.isEmpty() && !deck2.isEmpty()) {
			long card1 = deck1.remove();
			long card2 = deck2.remove();

			if (card1 > card2) {
				deck1.add(card1);
				deck1.add(card2);
			} else {
				deck2.add(card2);
				deck2.add(card1);
			}
		}

		List<Long> winner;
		if (deck1.isEmpty()) {
			winner = new ArrayList<>(deck2);
		} else {
			winner = new ArrayList<>(deck1);
		}
		var solution = 0L;
		for (var i = 0; i < winner.size(); i++) {
			solution += (winner.size() - i) * winner.get(i);
		}
		return solution + "";
	}
}
