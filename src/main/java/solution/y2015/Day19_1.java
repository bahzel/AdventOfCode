package solution.y2015;

import org.apache.commons.lang3.tuple.Pair;
import utils.soution.InstructionWithInputSolution;

import java.util.HashSet;
import java.util.Set;

public class Day19_1 extends InstructionWithInputSolution<Pair<String, String>, String, Set<String>> {
	public static void main(String[] args) {
		new Day19_1().solve();
	}

	@Override
	protected Set<String> initializeValue() {
		return new HashSet<>();
	}

	@Override
	protected Pair<String, String> transformInstruction(String instruction) {
		var instructions = instruction.split(" => ");
		return Pair.of(instructions[0], instructions[1]);
	}

	@Override
	protected String transformInput(String input) {
		return input;
	}

	@Override
	protected void performInstruction(Pair<String, String> stringStringPair, String s, Set<String> strings) {
		int index = s.indexOf(stringStringPair.getLeft());
		while (index >= 0) {
			strings.add(s.substring(0, index) + stringStringPair.getRight() + s.substring(
					index + stringStringPair.getLeft().length()));
			index = s.indexOf(stringStringPair.getLeft(), index + 1);
		}
	}

	@Override
	protected String getSolution(Set<String> strings) {
		return strings.size() + "";
	}
}
