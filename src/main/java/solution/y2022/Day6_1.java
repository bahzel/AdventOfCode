package solution.y2022;

import java.util.HashSet;

import utils.soution.Solution;

public class Day6_1 extends Solution {
	private static final int MARKER_LENGTH = 4;

	public static void main(String[] args) {
		new Day6_1().solve();
	}

	@Override
	protected String doSolve() {
		var chiffre = input.getFirst();
		for (var i = 0; i < chiffre.length(); i++) {
			var set = new HashSet<Character>();
			for (var j = 0; j < MARKER_LENGTH; j++) {
				if (!set.add(chiffre.charAt(i + j))) {
					break;
				} else if (j == MARKER_LENGTH - 1) {
					return i + MARKER_LENGTH + "";
				}
			}
		}
		throw new IllegalStateException();
	}
}
