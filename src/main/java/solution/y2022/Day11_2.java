package solution.y2022;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections4.ListUtils;

import utils.soution.Solution;

public class Day11_2 extends Solution {
	public static void main(String[] args) {
		new Day11_2().solve();
	}

	@Override
	protected String doSolve() {
		List<Monkey> monkeys = new ArrayList<>();
		ListUtils.partition(input, 7).forEach(instructions -> monkeys.add(new Monkey(instructions, monkeys)));
		for (var i = 0; i < 10000; i++) {
			monkeys.forEach(Monkey::perform);
		}
		monkeys.sort(Comparator.comparingLong(Monkey::getCount));
		return monkeys.getLast().getCount() * monkeys.get(monkeys.size() - 2).getCount() + "";
	}
}
