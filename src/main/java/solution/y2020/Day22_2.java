package solution.y2020;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import utils.soution.Solution;

public class Day22_2 extends Solution {
	public static void main(String[] args) {
		new Day22_2().solve();
	}

	@Override
	protected String doSolve() {
		var deck1 = new LinkedList<>(input.subList(1, input.indexOf("")).stream().map(Long::parseLong).toList());
		var deck2 = new LinkedList<>(
				input.subList(input.indexOf("") + 2, input.size()).stream().map(Long::parseLong).toList());

		play(deck1, deck2);

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

	private void play(LinkedList<Long> deck1, LinkedList<Long> deck2) {
		var cache = new HashSet<String>();
		while (!deck1.isEmpty() && !deck2.isEmpty()) {
			if (!cache.add(deck1.stream().map(Object::toString).collect(Collectors.joining("_"))
					+ deck2.stream().map(Object::toString).collect(Collectors.joining()))) {
				deck2.clear();
				return;
			}

			long card1 = deck1.remove();
			long card2 = deck2.remove();

			if (deck1.size() >= card1 && deck2.size() >= card2) {
				var copy1 = new LinkedList<>(deck1.subList(0, (int) card1));
				var copy2 = new LinkedList<>(deck2.subList(0, (int) card2));
				play(copy1, copy2);
				if (copy1.isEmpty()) {
					deck2.add(card2);
					deck2.add(card1);
				} else {
					deck1.add(card1);
					deck1.add(card2);
				}
			} else {
				if (card1 > card2) {
					deck1.add(card1);
					deck1.add(card2);
				} else {
					deck2.add(card2);
					deck2.add(card1);
				}
			}
		}
	}
}
