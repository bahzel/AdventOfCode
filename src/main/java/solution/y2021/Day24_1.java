package solution.y2021;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.commons.lang3.math.NumberUtils;

import utils.soution.MapSolution;

public class Day24_1 extends MapSolution<List<List<Consumer<long[]>>>> {
	public static void main(String[] args) {
		new Day24_1().solve();
	}

	@Override
	protected List<List<Consumer<long[]>>> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<List<Consumer<long[]>>> lists) {
		var instructions = instruction.split(" ");
		var index = getIndexFromRegisterName(instructions[1]);
		switch (instructions[0]) {
		case "inp":
			lists.add(new ArrayList<>());
			lists.getLast().add((register) -> {
			});
			break;
		case "add":
			if (NumberUtils.isCreatable(instructions[2])) {
				lists.getLast().add((register) -> register[index] += Integer.parseInt(instructions[2]));
			} else {
				lists	.getLast()
						.add((register) -> register[index] += register[getIndexFromRegisterName(instructions[2])]);
			}
			break;
		case "mul":
			if (NumberUtils.isCreatable(instructions[2])) {
				lists.getLast().add((register) -> register[index] *= Integer.parseInt(instructions[2]));
			} else {
				lists	.getLast()
						.add((register) -> register[index] *= register[getIndexFromRegisterName(instructions[2])]);
			}
			break;
		case "div":
			if (NumberUtils.isCreatable(instructions[2])) {
				lists.getLast().add((register) -> register[index] /= Integer.parseInt(instructions[2]));
			} else {
				lists	.getLast()
						.add((register) -> register[index] /= register[getIndexFromRegisterName(instructions[2])]);
			}
			break;
		case "mod":
			if (NumberUtils.isCreatable(instructions[2])) {
				lists.getLast().add((register) -> register[index] %= Integer.parseInt(instructions[2]));
			} else {
				lists	.getLast()
						.add((register) -> register[index] %= register[getIndexFromRegisterName(instructions[2])]);
			}
			break;
		case "eql":
			if (NumberUtils.isCreatable(instructions[2])) {
				lists	.getLast()
						.add((register) -> register[index] = register[index] == Integer.parseInt(instructions[2]) ? 1
								: 0);
			} else {
				lists	.getLast()
						.add((register) -> register[index] = register[index] == register[getIndexFromRegisterName(
								instructions[2])] ? 1 : 0);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown instruction: " + instruction);
		}
	}

	private int getIndexFromRegisterName(String registerName) {
		return switch (registerName) {
		case "w" -> 0;
		case "x" -> 1;
		case "y" -> 2;
		case "z" -> 3;
		default -> throw new IllegalArgumentException("Invalid register: " + registerName);
		};
	}

	@Override
	protected String computeSolution(List<List<Consumer<long[]>>> lists) {
		var lastInputs = new HashMap<Long, Long>();
		lastInputs.put(0L, 0L);
		for (var list : lists) {
			var nextInputs = new HashMap<Long, Long>();
			for (var lastInput : lastInputs.entrySet()) {
				for (var i = 9; i > 0; i--) {
					var register = new long[4];
					register[0] = i;
					register[3] = lastInput.getKey();
					for (var instruction : list) {
						instruction.accept(register);
					}
					var nextValue = lastInput.getValue() * 10 + i;
					var currentValue = nextInputs.get(register[3]);
					if (currentValue == null || nextValue > currentValue) {
						nextInputs.put(register[3], nextValue);
					}
				}
			}
			lastInputs = nextInputs;
		}

		return lastInputs	.entrySet()
							.stream()
							.filter(entry -> entry.getKey() == 0)
							.mapToLong(Map.Entry::getValue)
							.max()
							.orElseThrow()
				+ "";
	}
}
