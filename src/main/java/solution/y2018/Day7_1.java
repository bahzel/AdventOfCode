package solution.y2018;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import utils.GanttDiagram;
import utils.soution.MapSolution;

public class Day7_1 extends MapSolution<Map<String, Instruction>> {
	public static void main(String[] args) {
		new Day7_1().solve();
	}

	@Override
	protected Map<String, Instruction> initializeMapping() {
		return new HashMap<>();
	}

	@Override
	protected void transformInstruction(String instruction, Map<String, Instruction> stringInstructionMap) {
		var instructions = instruction.split(" ");
		var parent = stringInstructionMap.computeIfAbsent(instructions[1], Instruction::new);
		parent.addChild(stringInstructionMap.computeIfAbsent(instructions[7], Instruction::new));
	}

	@Override
	protected String computeSolution(Map<String, Instruction> stringInstructionMap) {
		var solution = new StringBuilder();

		while (true) {
			var nextInstruction = stringInstructionMap	.values()
														.stream()
														.filter(Instruction::isReady)
														.min(Comparator.comparing(Instruction::getName))
														.orElse(null);
			if (nextInstruction == null) {
				return solution.toString();
			} else {
				solution.append(nextInstruction.getName());
				nextInstruction.execute();
			}
		}
	}
}

@Getter
class Instruction extends GanttDiagram<Boolean> {
	private final String name;

	public Instruction(String name) {
		super(false);
		this.name = name;
	}

	public boolean isReady() {
		return !getValue() && getParents().stream().allMatch(GanttDiagram::getValue);
	}

	public void execute() {
		setValue(true);
	}
}
