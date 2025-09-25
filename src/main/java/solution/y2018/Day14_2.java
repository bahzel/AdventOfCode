package solution.y2018;

import java.util.ArrayList;
import java.util.List;

import utils.soution.Solution;

import static org.apache.commons.lang3.stream.LangCollectors.joining;

public class Day14_2 extends Solution {
	public static void main(String[] args) {
		new Day14_2().solve();
	}

	@Override
	protected String doSolve() {
		var goal = input.getFirst();
		List<Integer> scores = new ArrayList<>();
		scores.add(3);
		scores.add(7);
		var elf1 = 0;
		var elf2 = 1;

		while (scores.size() < goal.length() + 1 || !scores	.subList(scores.size() - goal.length() - 1, scores.size())
															.stream()
															.collect(joining())
															.contains(goal)) {
			var elf1Next = scores.get(elf1);
			var elf2Next = scores.get(elf2);
			var nextScore = elf1Next + elf2Next;
			if (nextScore > 9) {
				scores.add(nextScore / 10);
				scores.add(nextScore % 10);
			} else {
				scores.add(nextScore);
			}
			elf1 = (elf1 + elf1Next + 1) % scores.size();
			elf2 = (elf2 + elf2Next + 1) % scores.size();
		}

		return scores.size() - goal.length()
				+ scores.subList(scores.size() - goal.length() - 2, scores.size())
						.stream()
						.collect(joining())
						.indexOf(goal)
				- 2 + "";
	}
}
