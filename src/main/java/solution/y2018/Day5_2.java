package solution.y2018;

import java.util.List;

import utils.soution.Solution;

public class Day5_2 extends Solution {
	public static void main(String[] args) {
		new Day5_2().solve();
	}

	@Override
	protected String doSolve() {
		var shortestPolymer = Integer.MAX_VALUE;
		
		for (char i = 'a'; i <= 'z'; i++) {
			var polymerSize = new Day5_1(
					List.of(input.getFirst().replace(i + "", "").replace((i + "").toUpperCase(), ""))).solve();
			shortestPolymer = Math.min(shortestPolymer, Integer.parseInt(polymerSize));
		}

		return shortestPolymer + "";
	}
}
