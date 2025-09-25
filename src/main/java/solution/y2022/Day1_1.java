package solution.y2022;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import utils.soution.Solution;

public class Day1_1 extends Solution {
	public static void main(String[] args) {
		new Day1_1().solve();
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

		return elfs.stream().mapToLong(elf -> elf.stream().mapToLong(fruit -> fruit).sum()).max().orElseThrow() + "";
	}
}
