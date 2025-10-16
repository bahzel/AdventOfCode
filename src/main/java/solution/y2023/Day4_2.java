package solution.y2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import utils.soution.MapSolution;

public class Day4_2 extends MapSolution<List<Pair<Pair<List<Integer>, Set<Integer>>, Integer>>> {
	public static void main(String[] args) {
		new Day4_2().solve();
	}

	@Override
	protected List<Pair<Pair<List<Integer>, Set<Integer>>, Integer>> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction,
			List<Pair<Pair<List<Integer>, Set<Integer>>, Integer>> pairs) {
		var instructions = instruction.replace("  ", " ").split(": ")[1].split(" \\| ");
		var winningNumbers = Arrays.stream(instructions[0].split(" ")).map(Integer::parseInt).toList();
		var numbers = new HashSet<>(Arrays.stream(instructions[1].split(" ")).map(Integer::parseInt).toList());

		pairs.add(MutablePair.of(Pair.of(winningNumbers, numbers), 1));
	}

	@Override
	protected String computeSolution(List<Pair<Pair<List<Integer>, Set<Integer>>, Integer>> pairs) {
		for (var i = 0; i < pairs.size(); i++) {
			var winningCount = pairs.get(i).getLeft().getRight().size();
			pairs.get(i).getLeft().getLeft().forEach(pairs.get(i).getLeft().getRight()::remove);
			winningCount -= pairs.get(i).getLeft().getRight().size();

			for (var j = 1; j <= winningCount && i + j < pairs.size(); j++) {
				pairs.get(i + j).setValue(pairs.get(i + j).getValue() + pairs.get(i).getValue());
			}
		}

		return pairs.stream().mapToInt(Pair::getRight).sum() + "";
	}
}