package solution.y2019;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import utils.StringTransformer;
import utils.soution.MapSolution;

public class Day8_1 extends MapSolution<List<String>> {
	public static void main(String[] args) {
		new Day8_1().solve();
	}

	@Override
	protected List<String> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		return StringTransformer.splitString(instructions.getFirst(), 25 * 6);
	}

	@Override
	protected void transformInstruction(String instruction, List<String> strings) {
		strings.add(instruction);
	}

	@Override
	protected String computeSolution(List<String> strings) {
		var mostZeros = strings	.stream()
								.min(Comparator.comparingInt(string -> StringUtils.countMatches(string, "0")))
								.orElseThrow();

		return StringUtils.countMatches(mostZeros, "1") * StringUtils.countMatches(mostZeros, "2") + "";
	}
}
