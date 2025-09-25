package solution.y2024;

import java.util.Arrays;
import java.util.List;

import utils.soution.Solution;

public class Day17_2 extends Solution {
	public static void main(String[] args) {
		new Day17_2().solve();
	}

	@Override
	protected String doSolve() {
		var goal = Arrays.stream(input.getLast().split(": ")[1].split(",")).map(Integer::parseInt).toList();

		var currentA = 0L;
		for (var i = goal.size() - 1; i >= 0; i--) {
			for (var j = currentA * 8; ; j++) {
				if (solve(j, goal.subList(i, goal.size()))) {
					currentA = j;
					break;
				}
			}
		}

		return currentA + "";
	}

	private boolean solve(long a, List<Integer> goal) {
		var i = 0;
		for (; a != 0; i++) {
			var b = (a % 8) ^ 3;
			var c = (long) (a / Math.pow(2, b));
			b = b ^ 5;
			a = a / 8;
			b = b ^ c;
			if (b % 8 != goal.get(i)) {
				return false;
			}
		}
		return i == goal.size();
	}
}
