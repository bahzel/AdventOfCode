package solution.y2024;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import utils.soution.MapSolution;

public class Day1_2 extends MapSolution<Pair<List<Long>, List<Long>>> {
	public static void main(String[] args) {
		new Day1_2().solve();
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
		var similarity = 0L;

		for (var entry : leftList) {
			similarity += entry * Collections.frequency(rightList, entry);
		}

		return similarity + "";
	}
}
