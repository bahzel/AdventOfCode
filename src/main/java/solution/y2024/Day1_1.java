package solution.y2024;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import utils.soution.MapSolution;

public class Day1_1 extends MapSolution<Pair<List<Long>, List<Long>>> {
	public static void main(String[] args) {
		new Day1_1().solve();
	}

	@Override
	protected Pair<List<Long>, List<Long>> initializeMapping() {
		return Pair.of(new ArrayList<>(), new ArrayList<>());
	}

	@Override
	protected void transformInstruction(String instruction, Pair<List<Long>, List<Long>> listListPair) {
		var instructions = instruction.split(" {3}");
		listListPair.getLeft().add(Long.parseLong(instructions[0]));
		listListPair.getRight().add(Long.parseLong(instructions[1]));
	}

	@Override
	protected String computeSolution(Pair<List<Long>, List<Long>> listListPair) {
		var leftList = listListPair.getLeft();
		var rightList = listListPair.getRight();
		leftList.sort(Long::compareTo);
		rightList.sort(Long::compareTo);
		var differences = 0L;

		for (int i = 0; i < leftList.size(); i++) {
			differences += Math.abs(leftList.get(i) - rightList.get(i));
		}

		return differences + "";
	}
}
