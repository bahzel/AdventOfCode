package solution.y2019;

import java.util.HashMap;
import java.util.Map;

import utils.Tree;
import utils.soution.MapSolution;

public class Day6_2 extends MapSolution<Map<String, Tree<String>>> {
	public static void main(String[] args) {
		new Day6_2().solve();
	}

	@Override
	protected Map<String, Tree<String>> initializeMapping() {
		return new HashMap<>();
	}

	@Override
	protected void transformInstruction(String instruction, Map<String, Tree<String>> stringTreeMap) {
		var instructions = instruction.split("\\)");
		stringTreeMap.computeIfAbsent(instructions[0], Tree::new)
					 .addChild(stringTreeMap.computeIfAbsent(instructions[1], Tree::new));
	}

	@Override
	protected String computeSolution(Map<String, Tree<String>> stringTreeMap) {
		var santa = stringTreeMap.get("SAN");
		var currentPlanet = santa;

		while (!currentPlanet.contains("YOU")) {
			currentPlanet = currentPlanet.getParent();
		}

		return currentPlanet.computeDepth() * -2 + santa.computeDepth() + stringTreeMap.get("YOU").computeDepth() - 2
				+ "";
	}
}
