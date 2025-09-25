package solution.y2020;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import utils.soution.MapSolution;

public class Day9_2 extends MapSolution<List<Long>> {
	public static void main(String[] args) {
		new Day9_2().solve();
	}

	@Override
	protected List<Long> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<Long> longs) {
		longs.add(Long.parseLong(instruction));
	}

	@Override
	protected String computeSolution(List<Long> longs) {
		var invalidNumber = getInvalidNumber(longs);
		for (var i = 0; i < longs.size(); i++) {
			var sum = 0;
			for (var j = i; sum < invalidNumber; j++) {
				sum += longs.get(j);
				if (sum == invalidNumber) {
					var sequence = longs.subList(i, j + 1);
					sequence.sort(Comparator.naturalOrder());
					return sequence.getFirst() + sequence.getLast() + "";
				}
			}
		}
		throw new IllegalStateException();
	}

	private long getInvalidNumber(List<Long> longs) {
		for (var i = 25; i < longs.size(); ++i) {
			if (!isValid(longs, i)) {
				return longs.get(i);
			}
		}
		throw new IllegalStateException();
	}

	private boolean isValid(List<Long> longs, int index) {
		for (int i = index - 25; i < index; i++) {
			for (int j = i + 1; j < index; j++) {
				if (longs.get(i) + longs.get(j) == longs.get(index)) {
					return true;
				}
			}
		}
		return false;
	}
}
