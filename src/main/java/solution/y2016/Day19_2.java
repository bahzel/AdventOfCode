package solution.y2016;

import java.util.LinkedList;

import utils.CyclingListIterator;
import utils.soution.Solution;

public class Day19_2 extends Solution {
	public static void main(String[] args) {
		new Day19_2().solve();
	}

	@Override
	protected String doSolve() {
		var elfCount = Integer.parseInt(input.getFirst());
		var queue = new LinkedList<Integer>();

		for (int i = 1; i <= elfCount; i++) {
			queue.add(i);
		}

		var iterator = new CyclingListIterator<>(queue);
		while (queue.size() > 1) {
			iterator.next();
			iterator.remove();
			iterator.next();
			iterator.remove();
			iterator.next();
		}
		return iterator.next() + "";
	}
}
