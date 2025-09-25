package solution.y2017;

import java.util.ArrayList;
import java.util.List;

import utils.soution.MapSolution;

public class Day5_2 extends MapSolution<List<Long>> {
	public static void main(String[] args) {
		new Day5_2().solve();
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
		var index = 0;
		var step = 0L;

		while (index >= 0 && index < longs.size()) {
			step++;
			var offset = longs.get(index);
			if (offset >= 3) {
				longs.set(index, offset - 1);
			} else {
				longs.set(index, offset + 1);
			}
			index += offset;
		}

		return step + "";
	}
}
