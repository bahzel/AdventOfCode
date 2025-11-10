package solution.y2023;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import lombok.AllArgsConstructor;
import utils.soution.MapSolution;

public class Day19_2 extends MapSolution<Map<String, WorkflowRange>> {
	public static void main(String[] args) {
		new Day19_2().solve();
	}

	@Override
	protected Map<String, WorkflowRange> initializeMapping() {
		var mapping = new HashMap<String, WorkflowRange>();
		mapping.put("A", (workflows, part) -> List.of(part));
		mapping.put("R", (workflows, part) -> Collections.emptyList());
		return mapping;
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		return instructions.subList(0, instructions.indexOf(""));
	}

	@Override
	protected void transformInstruction(String instruction, Map<String, WorkflowRange> workflowMap) {
		var instructions = instruction.replace("}", "").split("\\{");
		var rules = instructions[1].split(",");
		workflowMap.put(instructions[0], (workflows, part) -> {
			var acceptedParts = new ArrayList<PartRange>();
			for (var rule : rules) {
				if (!rule.contains(":")) {
					acceptedParts.addAll(workflows.get(rule).apply(workflows, part));
					return acceptedParts;
				} else {
					if (rule.contains("<")) {
						var ruleParts = rule.replace("<", ":").split(":");
						var value = Integer.parseInt(ruleParts[1]);

						if (part.getMin(ruleParts[0]) < value) {
							if (part.getMax(ruleParts[0]) < value) {
								acceptedParts.addAll(workflows.get(ruleParts[2]).apply(workflows, part));
								return acceptedParts;
							} else {
								var newPart = new PartRange(part);
								newPart.setMax(ruleParts[0], value - 1);
								acceptedParts.addAll(workflows.get(ruleParts[2]).apply(workflows, newPart));
								part.setMin(ruleParts[0], value);
							}
						}
					} else {
						var ruleParts = rule.replace(">", ":").split(":");
						var value = Integer.parseInt(ruleParts[1]);

						if (part.getMax(ruleParts[0]) > value) {
							if (part.getMin(ruleParts[0]) > value) {
								acceptedParts.addAll(workflows.get(ruleParts[2]).apply(workflows, part));
								return acceptedParts;
							} else {
								var newPart = new PartRange(part);
								newPart.setMin(ruleParts[0], value + 1);
								acceptedParts.addAll(workflows.get(ruleParts[2]).apply(workflows, newPart));
								part.setMax(ruleParts[0], value);
							}
						}
					}
				}
			}
			throw new IllegalStateException();
		});
	}

	@Override
	protected String computeSolution(Map<String, WorkflowRange> workflows) {
		return workflows.get("in")
						.apply(workflows, new PartRange(1, 4000, 1, 4000, 1, 4000, 1, 4000))
						.stream()
						.mapToLong(PartRange::getCount)
						.sum()
				+ "";
	}
}

@AllArgsConstructor
class PartRange {
	private int xMin;
	private int xMax;
	private int mMin;
	private int mMax;
	private int aMin;
	private int aMax;
	private int sMin;
	private int sMax;

	public PartRange(PartRange other) {
		this.xMin = other.xMin;
		this.xMax = other.xMax;
		this.mMin = other.mMin;
		this.mMax = other.mMax;
		this.aMin = other.aMin;
		this.aMax = other.aMax;
		this.sMin = other.sMin;
		this.sMax = other.sMax;
	}

	public int getMin(String key) {
		return switch (key) {
		case "x" -> xMin;
		case "m" -> mMin;
		case "a" -> aMin;
		case "s" -> sMin;
		default -> throw new IllegalArgumentException("Invalid key: " + key);
		};
	}

	public int getMax(String key) {
		return switch (key) {
		case "x" -> xMax;
		case "m" -> mMax;
		case "a" -> aMax;
		case "s" -> sMax;
		default -> throw new IllegalArgumentException("Invalid key: " + key);
		};
	}

	public void setMin(String key, int value) {
		switch (key) {
		case "x" -> xMin = value;
		case "m" -> mMin = value;
		case "a" -> aMin = value;
		case "s" -> sMin = value;
		default -> throw new IllegalArgumentException("Invalid key: " + key);
		}
	}

	public void setMax(String key, int value) {
		switch (key) {
		case "x" -> xMax = value;
		case "m" -> mMax = value;
		case "a" -> aMax = value;
		case "s" -> sMax = value;
		default -> throw new IllegalArgumentException("Invalid key: " + key);
		}
	}

	public long getCount() {
		return (xMax - xMin + 1L) * (mMax - mMin + 1L) * (aMax - aMin + 1L) * (sMax - sMin + 1L);
	}
}

interface WorkflowRange extends BiFunction<Map<String, WorkflowRange>, PartRange, List<PartRange>> {
}