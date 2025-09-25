package solution.y2017;

import java.util.LinkedList;

import utils.soution.Solution;

public class Day17_1 extends Solution {
	public static void main(String[] args) {
		new Day17_1().solve();
	}

	@Override
	protected String doSolve() {
		var buffer = new LinkedList<Integer>();
		buffer.add(0);

		int step = Integer.parseInt(input.getFirst());
		int currentIndex = 0;
		for (int i = 1; i <= 2017; i++) {
			currentIndex += step;
			currentIndex %= buffer.size();
			buffer.add(currentIndex + 1, i);
			currentIndex++;
		}

		return buffer.get(currentIndex + 1).toString();
	}
}
