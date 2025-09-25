package solution.y2016;

import utils.Solution;

public class Day9_1 extends Solution {
	public static void main(String[] args) {
		new Day9_1().solve();
	}

	@Override
	protected String doSolve() {
		var compressedString = input.getFirst();
		var solution = new StringBuilder();
		var state = ParseState.MAP;

		var characters = 0;
		var repetitions = 0;
		StringBuilder repetitionBuffer = null;

		for (int i = 0; i < compressedString.length(); i++) {
			if (state == ParseState.MAP && compressedString.charAt(i) == '(') {
				state = ParseState.MARKER_CHARACTERS;
				characters = 0;
			} else if (state == ParseState.MAP) {
				solution.append(compressedString.charAt(i));
			} else if (state == ParseState.MARKER_CHARACTERS && compressedString.charAt(i) == 'x') {
				state = ParseState.MARKER_REPETITIONS;
				repetitions = 0;
			} else if (state == ParseState.MARKER_CHARACTERS) {
				characters *= 10;
				characters += Integer.parseInt(compressedString.charAt(i) + "");
			} else if (state == ParseState.MARKER_REPETITIONS && compressedString.charAt(i) == ')') {
				state = ParseState.REPEAT;
				repetitionBuffer = new StringBuilder();
			} else if (state == ParseState.MARKER_REPETITIONS) {
				repetitions *= 10;
				repetitions += Integer.parseInt(compressedString.charAt(i) + "");
			} else if (state == ParseState.REPEAT) {
				characters--;
				repetitionBuffer.append(compressedString.charAt(i));
				if (characters == 0) {
					solution.append(repetitionBuffer.toString().repeat(repetitions));
					state = ParseState.MAP;
				}
			} else {
				throw new IllegalStateException();
			}
		}
		return solution.length() + "";
	}
}

enum ParseState {
	MAP, MARKER_CHARACTERS, MARKER_REPETITIONS, REPEAT
}
