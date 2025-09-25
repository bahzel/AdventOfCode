package solution.y2018;

import java.util.ArrayList;
import java.util.List;

import utils.StringTransformer;
import utils.soution.MapSolution;

public class Day5_1 extends MapSolution<List<String>> {
	public static void main(String[] args) {
		new Day5_1().solve();
	}

	public Day5_1() {
		super();
	}

	public Day5_1(List<String> input) {
		super(input);
	}

	@Override
	protected List<String> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		return StringTransformer.splitString(instructions.getFirst());
	}

	@Override
	protected void transformInstruction(String instruction, List<String> strings) {
		strings.add(instruction);
	}

	@Override
	protected String computeSolution(List<String> strings) {
		var iterator = strings.listIterator();
		while (iterator.hasNext()) {
			var currentChar = iterator.next();
			if (!iterator.hasNext()) {
				break;
			}

			var nextChar = iterator.next();

			if (Character.isUpperCase(currentChar.charAt(0)) && currentChar.toLowerCase().equals(nextChar) || (
					Character.isLowerCase(currentChar.charAt(0)) && currentChar.toUpperCase().equals(nextChar))) {
				iterator.previous();
				iterator.remove();
				iterator.previous();
				iterator.remove();
			}

			if (iterator.hasPrevious()) {
				iterator.previous();
			}
		}

		return strings.size() + "";
	}
}
