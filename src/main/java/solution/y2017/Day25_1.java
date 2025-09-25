package solution.y2017;

import java.util.HashMap;

import utils.soution.Solution;

public class Day25_1 extends Solution {
	public static void main(String[] args) {
		new Day25_1().solve();
	}

	@Override
	protected String doSolve() {
		var currentState = State.A;
		var cursor = 0L;
		var tape = new HashMap<Long, Boolean>();

		for (int i = 0; i < 12302209; i++) {
			var currentValue = tape.computeIfAbsent(cursor, index -> false);

			switch (currentState) {
			case A:
				if (currentValue) {
					tape.put(cursor, false);
					cursor--;
					currentState = State.D;
				} else {
					tape.put(cursor, true);
					cursor++;
					currentState = State.B;
				}
				break;
			case B:
				if (currentValue) {
					tape.put(cursor, false);
					currentState = State.F;
				} else {
					tape.put(cursor, true);
					currentState = State.C;
				}
				cursor++;
				break;
			case C:
				if (currentValue) {
					currentState = State.A;
				} else {
					tape.put(cursor, true);
				}
				cursor--;
				break;
			case D:
				if (currentValue) {
					cursor++;
					currentState = State.A;
				} else {
					cursor--;
					currentState = State.E;
				}
				break;
			case E:
				if (currentValue) {
					tape.put(cursor, false);
					cursor++;
					currentState = State.B;
				} else {
					tape.put(cursor, true);
					cursor--;
					currentState = State.A;
				}
				break;
			case F:
				if (currentValue) {
					tape.put(cursor, false);
					currentState = State.E;
				} else {
					currentState = State.C;
				}
				cursor++;
				break;
			}
		}

		return tape.values().stream().filter(value -> value).count() + "";
	}
}

enum State {
	A, B, C, D, E, F
}
