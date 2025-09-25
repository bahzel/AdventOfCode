package solution.y2024;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;

import utils.Tree;
import utils.soution.Solution;

public class Day24_1 extends Solution {
	public static void main(String[] args) {
		new Day24_1().solve();
	}

	@Override
	protected String doSolve() {
		var gateMap = getGates();
		var gates = gateMap.values();
		while (gates.stream().anyMatch(gate -> gate.getValue().getRight() == null)) {
			gates	.stream()
					.filter(gate -> gate.getValue().getRight() == null)
					.filter(gate -> gate.getChildren().getFirst().getValue().getRight() != null
							&& gate.getChildren().get(1).getValue().getRight() != null)
					.forEach(gate -> gate	.getValue()
											.setRight(gate	.getValue()
															.getLeft()
															.apply(gate.getChildren().getFirst().getValue().getRight(),
																	gate.getChildren().get(1).getValue().getRight())));
		}

		var solution = new StringBuilder();
		for (int i = 0;; i++) {
			var gate = gateMap.get("z" + StringUtils.leftPad(i + "", 2, "0"));
			if (gate == null) {
				break;
			} else {
				solution.insert(0, gate.getValue().getRight() ? "1" : "0");
			}
		}

		return Long.parseLong(solution.toString(), 2) + "";
	}

	private Map<String, Tree<MutablePair<BiFunction<Boolean, Boolean, Boolean>, Boolean>>> getGates() {
		var indexOfSeparator = input.indexOf("");

		var gates = new HashMap<String, Tree<MutablePair<BiFunction<Boolean, Boolean, Boolean>, Boolean>>>();
		for (int i = indexOfSeparator + 1; i < input.size(); i++) {
			var instructions = input.get(i).split(" ");
			var gate = gates.computeIfAbsent(instructions[4], key -> new Tree<>(null));
			var child1 = gates.computeIfAbsent(instructions[0], key -> new Tree<>(null));
			var child2 = gates.computeIfAbsent(instructions[2], key -> new Tree<>(null));
			gate.addChild(child1);
			gate.addChild(child2);

			switch (instructions[1]) {
			case "AND":
				gate.setValue(MutablePair.of((input1, input2) -> input1 && input2, null));
				break;
			case "OR":
				gate.setValue(MutablePair.of((input1, input2) -> input1 || input2, null));
				break;
			case "XOR":
				gate.setValue(MutablePair.of((input1, input2) -> input1 ^ input2, null));
				break;
			}
		}

		for (int i = 0; i < indexOfSeparator; i++) {
			var instructions = input.get(i).split(": ");
			gates.get(instructions[0]).setValue(MutablePair.of(null, "1".equals(instructions[1])));
		}

		return gates;
	}
}
