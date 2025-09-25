package solution.y2018;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import utils.GanttDiagram;
import utils.soution.MapSolution;

public class Day7_2 extends MapSolution<Map<String, InstructionWithTime>> {
	public static void main(String[] args) {
		new Day7_2().solve();
	}

	@Override
	protected Map<String, InstructionWithTime> initializeMapping() {
		return new HashMap<>();
	}

	@Override
	protected void transformInstruction(String instruction, Map<String, InstructionWithTime> stringInstructionMap) {
		var instructions = instruction.split(" ");
		var parent = stringInstructionMap.computeIfAbsent(instructions[1], id -> new InstructionWithTime(id.charAt(0)));
		parent.addChild(
				stringInstructionMap.computeIfAbsent(instructions[7], id -> new InstructionWithTime(id.charAt(0))));
	}

	@Override
	protected String computeSolution(Map<String, InstructionWithTime> stringInstructionMap) {
		return stringInstructionMap.get("C").computeFinishTime() + "";
	}
}

@Getter
class InstructionWithTime extends GanttDiagram<Integer> {
	public InstructionWithTime(char name) {
		super(name - 'A' + 61);
	}

	public int computeFinishTime() {
		var startingTime = getParents()	.stream()
										.mapToInt(parent -> ((InstructionWithTime) parent).computeFinishTime())
										.max()
										.orElse(0);
		return startingTime + getValue();
	}
}
