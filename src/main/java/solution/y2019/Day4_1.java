package solution.y2019;

import utils.soution.Solution;

public class Day4_1 extends Solution {
	public static void main(String[] args) {
		new Day4_1().solve();
	}

	@Override
	protected String doSolve() {
		var instructions = input.getFirst().split("-");
		var minimumPassword = Integer.parseInt(instructions[0]);
		var maximumPassword = Integer.parseInt(instructions[1]);

		var solution = 0;
		for (int i = minimumPassword; i <= maximumPassword; i++) {
			if (checkPassword(i + "")) {
				solution++;
			}
		}

		return solution + "";
	}

	private boolean checkPassword(String password) {
		return hasDouble(password) && isIncreasing(password);
	}

	private boolean hasDouble(String password) {
		return password.charAt(0) == password.charAt(1) || password.charAt(1) == password.charAt(2)
				|| password.charAt(2) == password.charAt(3) || password.charAt(3) == password.charAt(4)
				|| password.charAt(4) == password.charAt(5);
	}

	private boolean isIncreasing(String password) {
		return password.charAt(0) <= password.charAt(1) && password.charAt(1) <= password.charAt(2)
				&& password.charAt(2) <= password.charAt(3) && password.charAt(3) <= password.charAt(4)
				&& password.charAt(4) <= password.charAt(5);
	}
}
