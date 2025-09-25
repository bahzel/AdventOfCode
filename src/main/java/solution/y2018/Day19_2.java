package solution.y2018;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import utils.soution.MapSolution;

public class Day19_2 extends MapSolution<List<Consumer<int[]>>> {
	public static void main(String[] args) {
		new Day19_2().solve();
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
		case "setr":
			biConsumers.add(register -> register[c] = register[a]);
			break;
		case "seti":
			biConsumers.add(register -> register[c] = a);
			break;
		case "gtrr":
			biConsumers.add(register -> register[c] = register[a] > register[b] ? 1 : 0);
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
		register[0] = 1;

		for (; register[2] < biConsumers.size(); register[2]++) {
			if (register[2] == 2 && register[5] != 0) {
				if (register[1] % register[5] == 0) {
					register[0] += register[5];
				}
				register[4] = 0;
				register[3] = register[1];
				register[2] = 12;
			}

			biConsumers.get(register[2]).accept(register);
		}

		return register[0] + "";
	}
}
