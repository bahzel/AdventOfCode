package solution.y2020;

import java.util.HashMap;
import java.util.Map;

import utils.soution.MapSolution;

public class Day7_2 extends MapSolution<Map<String, Bag>> {
	public static void main(String[] args) {
		new Day7_2().solve();
	}

	@Override
	protected Map<String, Bag> initializeMapping() {
		return new HashMap<>();
	}

	@Override
	protected void transformInstruction(String instruction, Map<String, Bag> bags) {
		var instructions = instruction.split(", ");
		var definition = instructions[0].split(" ");
		var bag = bags.computeIfAbsent(definition[0] + definition[1], Bag::new);

		if (!"no".equals(definition[4])) {
			bag.addChild(bags.computeIfAbsent(definition[5] + definition[6], Bag::new),
					Integer.parseInt(definition[4]));
		}

		for (int i = 1; i < instructions.length; ++i) {
			var child = instructions[i].split(" ");
			bag.addChild(bags.computeIfAbsent(child[1] + child[2], Bag::new), Integer.parseInt(child[0]));
		}
	}

	@Override
	protected String computeSolution(Map<String, Bag> bags) {
		return bags.get("shinygold").countChildren() + "";
	}
}
