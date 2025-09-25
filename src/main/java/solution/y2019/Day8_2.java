package solution.y2019;

import java.util.ArrayList;
import java.util.List;

import utils.StringTransformer;
import utils.soution.MapSolution;

public class Day8_2 extends MapSolution<List<List<String>>> {
	public static void main(String[] args) {
		new Day8_2().solve();
	}

	@Override
	protected List<List<String>> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		return StringTransformer.splitString(instructions.getFirst(), 25 * 6);
	}

	@Override
	protected void transformInstruction(String instruction, List<List<String>> strings) {
		strings.add(StringTransformer.splitString(instruction, 25));
	}

	@Override
	protected String computeSolution(List<List<String>> strings) {
		var solution = new StringBuilder();

		for (var y = 0; y < strings.getFirst().size(); y++) {
			for (var x = 0; x < strings.getFirst().getFirst().length(); x++) {
				for (List<String> string : strings) {
					switch (string.get(y).charAt(x)) {
					case '2':
						continue;
					case '0':
						solution.append(' ');
						break;
					case '1':
						solution.append('#');
						break;
					default:
						throw new IllegalStateException("Unexpected value: " + string.get(y).charAt(x));
					}
					break;
				}
			}
			solution.append('\n');
		}

		return solution.toString();
	}
}
