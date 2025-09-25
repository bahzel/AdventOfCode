package solution.y2016;

import lombok.Getter;
import lombok.Setter;
import utils.MapSolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day10_1 extends MapSolution<Map<String, Robot>> {
	public static void main(String[] args) {
		new Day10_1().solve();
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
			robot.setLower(stringRobotMap.computeIfAbsent(instructions[6], Robot::new));
			robot.setHigher(stringRobotMap.computeIfAbsent(instructions[11], Robot::new));
		}
	}

	@Override
	protected String computeSolution(Map<String, Robot> stringRobotMap) {
		while (true) {
			for (var robot : stringRobotMap.values()) {
				if (robot.give()) {
					return robot.getName();
				}
			}
		}
	}
}

class Robot {
	@Getter
	private final List<Integer> chips = new ArrayList<>();
	@Setter
	private Robot lower;
	@Setter
	private Robot higher;
	@Getter
	private final String name;

	public Robot(String name) {
		this.name = name;
	}

	public void addChip(int value) {
		chips.add(value);
	}

	public boolean give() {
		var finish = false;

		if (chips.size() == 2) {
			if (chips.get(0) < chips.get(1)) {
				lower.addChip(chips.get(0));
				higher.addChip(chips.get(1));
				finish = chips.get(0) == 17 && chips.get(1) == 61;
			} else {
				lower.addChip(chips.get(1));
				higher.addChip(chips.get(0));
				finish = chips.get(1) == 17 && chips.get(0) == 61;
			}
			chips.clear();
		}

		return finish;
	}
}
