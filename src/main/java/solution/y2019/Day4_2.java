package solution.y2019;

import utils.soution.Solution;

public class Day4_2 extends Solution {
	public static void main(String[] args) {
		new Day4_2().solve();
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
		for (int i = 0; i < password.length() - 1; i++) {
			if (password.charAt(i) == password.charAt(i + 1) && (i == password.length() - 2
					|| password.charAt(i) != password.charAt(i + 2)) && (i == 0
					|| password.charAt(i) != password.charAt(i - 1))) {
				return true;
			}
		}

		return false;
	}

	private boolean isIncreasing(String password) {
		return password.charAt(0) <= password.charAt(1) && password.charAt(1) <= password.charAt(2)
				&& password.charAt(2) <= password.charAt(3) && password.charAt(3) <= password.charAt(4)
				&& password.charAt(4) <= password.charAt(5);
	}
}
