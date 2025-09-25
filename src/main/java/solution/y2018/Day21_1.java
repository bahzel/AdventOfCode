package solution.y2018;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import utils.soution.MapSolution;

public class Day21_1 extends MapSolution<List<Consumer<int[]>>> {
	public static void main(String[] args) {
		new Day21_1().solve();
	}

	@Override
	protected List<Consumer<int[]>> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		return instructions.subList(1, instructions.size());
	}

	@Override
	protected void transformInstruction(String instruction, List<Consumer<int[]>> biConsumers) {
		var instructions = instruction.split(" ");
		var a = Integer.parseInt(instructions[1]);
		var b = Integer.parseInt(instructions[2]);
		var c = Integer.parseInt(instructions[3]);
		switch (instructions[0]) {
		case "addr":
			biConsumers.add(register -> register[c] = register[a] + register[b]);
			break;
		case "addi":
			biConsumers.add(register -> register[c] = register[a] + b);
			break;
		case "mulr":
			biConsumers.add(register -> register[c] = register[a] * register[b]);
			break;
		case "muli":
			biConsumers.add(register -> register[c] = register[a] * b);
			break;
		case "bani":
			biConsumers.add(register -> register[c] = register[a] & b);
			break;
		case "bori":
			biConsumers.add(register -> register[c] = register[a] | b);
			break;
		case "setr":
			biConsumers.add(register -> register[c] = register[a]);
			break;
		case "seti":
			biConsumers.add(register -> register[c] = a);
			break;
		case "gtir":
			biConsumers.add(register -> register[c] = a > register[b] ? 1 : 0);
			break;
		case "gtrr":
			biConsumers.add(register -> register[c] = register[a] > register[b] ? 1 : 0);
			break;
		case "eqri":
			biConsumers.add(register -> register[c] = register[a] == b ? 1 : 0);
			break;
		case "eqrr":
			biConsumers.add(register -> register[c] = register[a] == register[b] ? 1 : 0);
			break;
		default:
			throw new RuntimeException("Unknown instruction: " + instruction);
		}
	}

	@Override
	protected String computeSolution(List<Consumer<int[]>> biConsumers) {
		var register = new int[6];

		for (; register[5] < biConsumers.size(); register[5]++) {
			biConsumers.get(register[5]).accept(register);
			if (register[5] == 28) {
				return register[4] + "";
			}
		}

		throw new IllegalStateException();
	}
}
