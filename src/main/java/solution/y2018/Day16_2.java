package solution.y2018;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.BiConsumer;

import org.apache.commons.lang3.tuple.Pair;

import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.soution.Solution;

public class Day16_2 extends Solution {
	public static void main(String[] args) {
		new Day16_2().solve();
	}

	@Override
	protected String doSolve() {
		var ruleMap = createRuleMap();
		var program = getProgram();
		var register = new int[4];

		for (var instruction : program) {
			ruleMap.get(instruction.getLeft()).accept(instruction.getRight(), register);
		}

		return register[0] + "";
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

	private Map<Integer, BiConsumer<int[], int[]>> createRuleMap() {
		var ruleMap = new HashMap<Integer, BiConsumer<int[], int[]>>();
		Queue<BiConsumer<int[], int[]>> ruleQueue = new LinkedList<>(createRuleset());
		var testMap = createTestMap();

		while (!ruleQueue.isEmpty()) {
			var rule = ruleQueue.poll();
			var matchingIndixes = testMap	.entrySet()
											.stream()
											.filter(entry -> countRuleMatches(rule,
													entry.getValue()) == entry.getValue().size())
											.mapToInt(Map.Entry::getKey)
											.boxed()
											.toList();
			if (matchingIndixes.size() == 1) {
				ruleMap.put(matchingIndixes.getFirst(), rule);
				testMap.remove(matchingIndixes.getFirst());
			} else {
				ruleQueue.add(rule);
			}
		}

		return ruleMap;
	}

	private Map<Integer, List<Testset>> createTestMap() {
		var testMap = new HashMap<Integer, List<Testset>>();

		for (int i = 0; !input.get(i).isEmpty(); i = i + 4) {
			var registerBefore = Arrays	.stream(input.get(i).replace("]", "").split("\\[")[1].split(", "))
										.mapToInt(Integer::parseInt)
										.toArray();
			var instructions = parseInstruction(input.get(i + 1));
			var registerAfter = Arrays	.stream(input.get(i + 2).replace("]", "").split("\\[")[1].split(", "))
										.mapToInt(Integer::parseInt)
										.toArray();

			testMap	.computeIfAbsent(instructions.getLeft(), key -> new ArrayList<>())
					.add(new Testset(instructions.getRight(), registerBefore, registerAfter));
		}

		return testMap;
	}

	private long countRuleMatches(BiConsumer<int[], int[]> rule, List<Testset> tests) {
		return tests.stream().filter(test -> checkRule(rule, test)).count();
	}

	private boolean checkRule(BiConsumer<int[], int[]> rule, Testset testset) {
		var registerBefore = testset.getRegisterBefore();
		rule.accept(testset.getInstruction(), registerBefore);
		return Arrays.equals(registerBefore, testset.getRegisterAfter());
	}

	private List<Pair<Integer, int[]>> getProgram() {
		var program = new ArrayList<Pair<Integer, int[]>>();

		for (int i = getIndexOfProgram(); i < input.size(); i++) {
			program.add(parseInstruction(input.get(i)));

		}

		return program;
	}

	private int getIndexOfProgram() {
		for (int i = 0; i < input.size(); i++) {
			if (input.get(i).isEmpty() && input.get(i + 1).isEmpty() && input.get(i + 2).isEmpty()) {
				return i + 3;
			}
		}
		throw new IllegalStateException();
	}

	private Pair<Integer, int[]> parseInstruction(String instruction) {
		var instructions = Arrays.stream(instruction.split(" ")).mapToInt(Integer::parseInt).toArray();
		return Pair.of(instructions[0], Arrays.copyOfRange(instructions, 1, 4));
	}
}

@AllArgsConstructor
@Getter
class Testset {
	private final int[] instruction;
	private final int[] registerBefore;
	private final int[] registerAfter;

	public int[] getRegisterBefore() {
		return Arrays.copyOf(registerBefore, registerBefore.length);
	}
}
