package solution.y2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import utils.StringTransformer;
import utils.soution.MapSolution;

public class Day8_1 extends MapSolution<List<Pair<List<String>, List<String>>>> {
	public static void main(String[] args) {
		new Day8_1().solve();
	}

	@Override
	protected List<Pair<List<String>, List<String>>> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<Pair<List<String>, List<String>>> pairs) {
		var instructions = instruction.split(" \\| ");
		pairs.add(Pair.of(Arrays.asList(instructions[0].split(" ")),
				Arrays.stream(instructions[1].split(" ")).map(StringTransformer::sort).toList()));
	}

	@Override
	protected String computeSolution(List<Pair<List<String>, List<String>>> pairs) {
		return pairs.stream()
					.map(Pair::getRight)
					.flatMap(Collection::stream)
					.filter(digit -> digit.length() == 2 || digit.length() == 3 || digit.length() == 4
							|| digit.length() == 7)
					.count()
				+ "";
	}
}
