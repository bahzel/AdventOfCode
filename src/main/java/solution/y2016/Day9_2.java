package solution.y2016;

import utils.Solution;

public class Day9_2 extends Solution {
	public static void main(String[] args) {
		new Day9_2().solve();
	}

	@Override
	protected String doSolve() {
		return parse(input.getFirst()) + "";
	}

	private long parse(String compressedString) {
		var solution = 0L;
		var state = ParseState.MAP;

		var characters = 0;
		var repetitions = 0;

		for (int i = 0; i < compressedString.length(); i++) {
			if (state == ParseState.MAP && compressedString.charAt(i) == '(') {
				state = ParseState.MARKER_CHARACTERS;
				characters = 0;
			} else if (state == ParseState.MAP) {
				solution++;
			} else if (state == ParseState.MARKER_CHARACTERS && compressedString.charAt(i) == 'x') {
				state = ParseState.MARKER_REPETITIONS;
				repetitions = 0;
			} else if (state == ParseState.MARKER_CHARACTERS) {
				characters *= 10;
				characters += Integer.parseInt(compressedString.charAt(i) + "");
			} else if (state == ParseState.MARKER_REPETITIONS && compressedString.charAt(i) == ')') {
				solution += parse(compressedString.substring(i + 1, i + 1 + characters)) * repetitions;
				i += characters;
				state = ParseState.MAP;
			} else if (state == ParseState.MARKER_REPETITIONS) {
				repetitions *= 10;
				repetitions += Integer.parseInt(compressedString.charAt(i) + "");
			} else {
				throw new IllegalStateException();
			}
		}
		return solution;
	}
}
