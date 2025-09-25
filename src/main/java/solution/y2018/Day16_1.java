package solution.y2018;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

import utils.soution.Solution;

public class Day16_1 extends Solution {
	public static void main(String[] args) {
		new Day16_1().solve();
	}

	@Override
	protected String doSolve() {
		var ruleSet = createRuleset();
		var solution = 0;

		for (int i = 0; !input.get(i).isEmpty(); i = i + 4) {
			var registerBefore = Arrays	.stream(input.get(i).replace("]", "").split("\\[")[1].split(", "))
										.mapToInt(Integer::parseInt)
										.toArray();
			var instruction = Arrays.stream(Arrays.copyOfRange(input.get(i + 1).split(" "), 1, 4))
									.mapToInt(Integer::parseInt)
									.toArray();
			var registerAfter = Arrays	.stream(input.get(i + 2).replace("]", "").split("\\[")[1].split(", "))
										.mapToInt(Integer::parseInt)
										.toArray();
			if (countRuleMatches(ruleSet, instruction, registerBefore, registerAfter) >= 3) {
				solution++;
			}
		}

		return solution + "";
	}

	private List<BiConsumer<int[], int[]>> createRuleset() {
		var ruleset = new ArrayList<BiConsumer<int[], int[]>>();

		ruleset.add((instruction,
				register) -> register[instruction[2]] = register[instruction[0]] + register[instruction[1]]);
		ruleset.add((instruction, register) -> register[instruction[2]] = register[instruction[0]] + instruction[1]);

		ruleset.add((instruction,
				register) -> register[instruction[2]] = register[instruction[0]] * register[instruction[1]]);
		ruleset.add((instruction, register) -> register[instruction[2]] = register[instruction[0]] * instruction[1]);

		ruleset.add((instruction,
				register) -> register[instruction[2]] = register[instruction[0]] & register[instruction[1]]);
		ruleset.add((instruction, register) -> register[instruction[2]] = register[instruction[0]] & instruction[1]);

		ruleset.add((instruction,
				register) -> register[instruction[2]] = register[instruction[0]] | register[instruction[1]]);
		ruleset.add((instruction, register) -> register[instruction[2]] = register[instruction[0]] | instruction[1]);

		ruleset.add((instruction, register) -> register[instruction[2]] = register[instruction[0]]);
		ruleset.add((instruction, register) -> register[instruction[2]] = instruction[0]);

		ruleset.add((instruction,
				register) -> register[instruction[2]] = instruction[0] > register[instruction[1]] ? 1 : 0);
		ruleset.add((instruction,
				register) -> register[instruction[2]] = register[instruction[0]] > instruction[1] ? 1 : 0);
		ruleset.add((instruction,
				register) -> register[instruction[2]] = register[instruction[0]] > register[instruction[1]] ? 1 : 0);

		ruleset.add((instruction,
				register) -> register[instruction[2]] = instruction[0] == register[instruction[1]] ? 1 : 0);
		ruleset.add((instruction,
				register) -> register[instruction[2]] = register[instruction[0]] == instruction[1] ? 1 : 0);
		ruleset.add((instruction,
				register) -> register[instruction[2]] = register[instruction[0]] == register[instruction[1]] ? 1 : 0);

		return ruleset;
	}

	private long countRuleMatches(List<BiConsumer<int[], int[]>> rules, int[] instruction, int[] registerBefore,
			int[] registerAfter) {
		return rules.stream().filter(rule -> checkRule(rule, instruction, registerBefore, registerAfter)).count();
	}

	private boolean checkRule(BiConsumer<int[], int[]> rule, int[] instruction, int[] registerBefore,
			int[] registerAfter) {
		var beforeCopy = Arrays.copyOf(registerBefore, registerBefore.length);
		rule.accept(instruction, beforeCopy);
		return Arrays.equals(beforeCopy, registerAfter);
	}
}
