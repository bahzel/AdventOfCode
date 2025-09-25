package solution.y2022;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import utils.soution.Solution;

public class Day1_2 extends Solution {
	public static void main(String[] args) {
		new Day1_2().solve();
	}

	@Override
	protected String doSolve() {
		var elfs = new ArrayList<List<Long>>();
		elfs.add(new ArrayList<>());
		for (var fruit : input) {
			if (StringUtils.isEmpty(fruit)) {
				elfs.add(new ArrayList<>());
			} else {
				elfs.getLast().add(Long.valueOf(fruit));
			}
		}

		var summedElfs = elfs	.stream()
								.map(elf -> elf.stream().mapToLong(fruit -> fruit).sum())
								.sorted(Comparator.reverseOrder())
								.toList();
		return summedElfs.getFirst() + summedElfs.get(1) + summedElfs.get(2) + "";
	}
}
