package solution.y2016;

import utils.MapSolution;

import java.util.HashMap;
import java.util.Map;

public class Day10_2 extends MapSolution<Map<String, Robot>> {
	public static void main(String[] args) {
		new Day10_2().solve();
	}

	@Override
	protected Map<String, Robot> initializeMapping() {
		return new HashMap<>();
	}

	@Override
	protected void transformInstruction(String instruction, Map<String, Robot> stringRobotMap) {
		var instructions = instruction.split(" ");
		if (instruction.startsWith("value")) {
			var robot = stringRobotMap.computeIfAbsent(instructions[5], Robot::new);
			robot.addChip(Integer.parseInt(instructions[1]));
		} else {
			var robot = stringRobotMap.computeIfAbsent(instructions[1], Robot::new);
			if ("output".equals(instructions[5])) {
				robot.setLower(stringRobotMap.computeIfAbsent("output" + instructions[6], Robot::new));
			} else {
				robot.setLower(stringRobotMap.computeIfAbsent(instructions[6], Robot::new));
			}
			if ("output".equals(instructions[10])) {
				robot.setHigher(stringRobotMap.computeIfAbsent("output" + instructions[11], Robot::new));
			} else {
				robot.setHigher(stringRobotMap.computeIfAbsent(instructions[11], Robot::new));
			}
		}
	}

	@Override
	protected String computeSolution(Map<String, Robot> stringRobotMap) {
		while (true) {
			for (var robot : stringRobotMap.values()) {
				robot.give();
			}

			var output0 = stringRobotMap.get("output0").getChips();
			var output1 = stringRobotMap.get("output1").getChips();
			var output2 = stringRobotMap.get("output2").getChips();
			if (!output0.isEmpty() && !output1.isEmpty() && !output2.isEmpty()) {
				return ((long) output0.getFirst() * output1.getFirst() * output2.getFirst()) + "";
			}
		}
	}
}
