package solution.y2023;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

import lombok.AllArgsConstructor;
import utils.soution.InstructionSolution;

public class Day19_1 extends InstructionSolution<Part, AtomicInteger> {
	private final Map<String, Workflow> WORKFLOWS = new HashMap<>();

	public static void main(String[] args) {
		new Day19_1().solve();
	}

	@Override
	protected AtomicInteger initializeValue() {
		for (var instruction : input.subList(0, input.indexOf(""))) {
			var instructions = instruction.replace("}", "").split("\\{");
			var rules = instructions[1].split(",");
			WORKFLOWS.put(instructions[0], (workflows, part) -> {
				for (var rule : rules) {
					if (!rule.contains(":")) {
						return workflows.get(rule).apply(workflows, part);
					} else {
						if (rule.contains("<")) {
							var ruleParts = rule.replace("<", ":").split(":");
							if (part.get(ruleParts[0]) < Integer.parseInt(ruleParts[1])) {
								return workflows.get(ruleParts[2]).apply(workflows, part);
							}
						} else {
							var ruleParts = rule.replace(">", ":").split(":");
							if (part.get(ruleParts[0]) > Integer.parseInt(ruleParts[1])) {
								return workflows.get(ruleParts[2]).apply(workflows, part);
							}
						}
					}
				}
				throw new IllegalStateException();
			});
		}
		WORKFLOWS.put("A", (workflows, part) -> true);
		WORKFLOWS.put("R", (workflows, part) -> false);

		return new AtomicInteger();
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		return instructions.subList(instructions.indexOf("") + 1, instructions.size());
	}

	@Override
	protected Part transformInstruction(String instruction) {
		var instructions = instruction	.replace(",m", "")
										.replace(",a", "")
										.replace(",s", "")
										.replace("}", "")
										.split("=");
		return new Part(Integer.parseInt(instructions[1]), Integer.parseInt(instructions[2]),
				Integer.parseInt(instructions[3]), Integer.parseInt(instructions[4]));
	}

	@Override
	protected boolean performInstruction(Part part, AtomicInteger atomicInteger) {
		if (WORKFLOWS.get("in").apply(WORKFLOWS, part)) {
			atomicInteger.addAndGet(part.getRating());
		}

		return false;
	}

	@Override
	protected String getSolution(AtomicInteger atomicInteger) {
		return atomicInteger.toString();
	}
}

@AllArgsConstructor
class Part {
	private final int x;
	private final int m;
	private final int a;
	private final int s;

	public int get(String key) {
		return switch (key) {
		case "x" -> x;
		case "m" -> m;
		case "a" -> a;
		case "s" -> s;
		default -> throw new IllegalArgumentException("Invalid key: " + key);
		};
	}

	public int getRating() {
		return x + m + a + s;
	}
}

interface Workflow extends BiFunction<Map<String, Workflow>, Part, Boolean> {
}