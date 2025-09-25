package solution.y2018;

import java.util.HashMap;
import java.util.LinkedList;

import utils.CyclingListIterator;
import utils.soution.Solution;

public class Day9_2 extends Solution {
	public static void main(String[] args) {
		new Day9_2().solve();
	}

	@Override
	protected String doSolve() {
		var instructions = input.getFirst().split(" ");
		var players = Integer.parseInt(instructions[0]);
		var score = new HashMap<Integer, Long>();
		var marbleCount = Integer.parseInt(instructions[6]) * 100;
		var marbles = new LinkedList<Integer>();
		marbles.add(0);
		var iterator = new CyclingListIterator<>(marbles);

		for (int i = 1; i <= marbleCount; i++) {
			if (i % 23 != 0) {
				iterator.next();
				iterator.add(i);
			} else {
				score.put(i % players, score.getOrDefault(i % players, 0L) + i);
				var value = 0L;
				for (int j = 0; j <= 7; j++) {
					value = iterator.previous();
				}
				iterator.remove();
				iterator.next();
				score.put(i % players, score.get(i % players) + value);
			}
		}

		return score.values().stream().mapToLong(Long::longValue).max().orElseThrow() + "";
	}
}
