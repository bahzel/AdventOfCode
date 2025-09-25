package solution.y2017;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import utils.Tree;
import utils.soution.MapSolution;

public class Day7_1 extends MapSolution<Map<String, Tree<Pair<String, Integer>>>> {
	public static void main(String[] args) {
		new Day7_1().solve();
	}

	@Override
	protected Map<String, Tree<Pair<String, Integer>>> initializeMapping() {
		return new HashMap<>();
	}

	@Override
	protected void transformInstruction(String instruction, Map<String, Tree<Pair<String, Integer>>> stringTreeMap) {
		var instructions = instruction.split(" -> ");
		var parentInstruction = instructions[0].replace(")", "").split((" \\("));

		var parent = stringTreeMap.computeIfAbsent(parentInstruction[0], name -> new Tree<>());
		parent.setValue(Pair.of(parentInstruction[0], Integer.parseInt(parentInstruction[1])));

		if (instructions.length > 1) {
			var childInstructions = instructions[1].split(", ");
			for (int i = 0; i < childInstructions.length; i++) {
				var child = stringTreeMap.computeIfAbsent(childInstructions[i], name -> new Tree<>());
				parent.addChild(child);
			}
		}
	}

	@Override
	protected String computeSolution(Map<String, Tree<Pair<String, Integer>>> stringTreeMap) {
		var parent = stringTreeMap.values().iterator().next();
		while (parent.getParent() != null) {
			parent = parent.getParent();
		}
		return parent.getValue().getLeft();
	}
}
