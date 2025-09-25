package solution.y2021;

import java.util.ArrayList;

import utils.soution.Solution;

public class Day3_2 extends Solution {
	public static void main(String[] args) {
		new Day3_2().solve();
	}

	@Override
	protected String doSolve() {
		var oxygen = new ArrayList<>(input);
		var co2 = new ArrayList<>(input);
		for (var i = 0; i < input.getFirst().length(); i++) {
			var index = i;
			if (oxygen.stream().filter(value -> value.charAt(index) == '1').count() >= (oxygen.size() + 1) / 2) {
				oxygen.removeIf(value -> value.charAt(index) == '0');
			} else {
				oxygen.removeIf(value -> value.charAt(index) == '1');
			}
			if (co2.size() == 1) {
				continue;
			}
			if (co2.stream().filter(value -> value.charAt(index) == '1').count() >= (co2.size() + 1) / 2) {
				co2.removeIf(value -> value.charAt(index) == '1');
			} else {
				co2.removeIf(value -> value.charAt(index) == '0');
			}
		}
		return Long.parseLong(oxygen.getFirst(), 2) * Long.parseLong(co2.getFirst(), 2) + "";
	}
}
