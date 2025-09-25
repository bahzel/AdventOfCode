package solution.y2016;

import java.util.LinkedList;
import java.util.Queue;

import utils.soution.Solution;

public class Day19_1 extends Solution {
	public static void main(String[] args) {
		new Day19_1().solve();
	}

	@Override
	protected String doSolve() {
		var elfCount = Integer.parseInt(input.getFirst());
		Queue<Integer> queue = new LinkedList<>();

		for (int i = 1; i <= elfCount; i++) {
			queue.add(i);
		}

		while (!queue.isEmpty()) {
			int elf = queue.poll();

			if (queue.isEmpty()) {
				return elf + "";
			}

			queue.poll();
			queue.add(elf);
		}
		throw new IllegalStateException();
	}
}
