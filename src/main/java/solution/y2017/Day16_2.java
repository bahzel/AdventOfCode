package solution.y2017;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

import utils.soution.InstructionSolution;

public class Day16_2 extends InstructionSolution<Consumer<StringBuilder>, StringBuilder> {
	public static void main(String[] args) {
		new Day16_2().solve();
	}

	@Override
	protected StringBuilder initializeValue() {
		return new StringBuilder("abcdefghijklmnop");
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		return Arrays.asList(instructions.getFirst().split(","));
	}

	@Override
	protected Consumer<StringBuilder> transformInstruction(String instruction) {
		return switch (instruction.charAt(0)) {
			case 's' -> programs -> {
				var amount = Integer.parseInt(instruction.substring(1));
				var toSpin = programs.substring(programs.length() - amount);
				programs.delete(programs.length() - amount, programs.length());
				programs.insert(0, toSpin);
			};
			case 'x' -> programs -> {
				var indices = instruction.substring(1).split("/");
				var lowerIndex = Math.min(Integer.parseInt(indices[0]), Integer.parseInt(indices[1]));
				var higherIndex = Math.max(Integer.parseInt(indices[0]), Integer.parseInt(indices[1]));
				var lowerValue = programs.charAt(lowerIndex);
				var higherValue = programs.charAt(higherIndex);
				programs.deleteCharAt(higherIndex);
				programs.deleteCharAt(lowerIndex);
				programs.insert(lowerIndex, higherValue);
				programs.insert(higherIndex, lowerValue);
			};
			case 'p' -> programs -> {
				var indices = instruction.substring(1).split("/");
				var lowerIndex = Math.min(programs.indexOf(indices[0]), programs.indexOf(indices[1]));
				var higherIndex = Math.max(programs.indexOf(indices[0]), programs.indexOf(indices[1]));
				var lowerValue = programs.charAt(lowerIndex);
				var higherValue = programs.charAt(higherIndex);
				programs.deleteCharAt(higherIndex);
				programs.deleteCharAt(lowerIndex);
				programs.insert(lowerIndex, higherValue);
				programs.insert(higherIndex, lowerValue);
			};
			default -> throw new IllegalArgumentException("Invalid instruction: " + instruction);
		};
	}

	private final int REPETITIONS = 1000000000;

	@Override
	public String doSolve() {
		var cache = new HashSet<String>();
		var firstDuplicate = 0;

		for (int i = 0; i < REPETITIONS; i++) {
			for (var instruction : getInstructions()) {
				if (performInstruction(instruction, getValue())) {
					break;
				}
			}

			if (!cache.add(getValue().toString())) {
				if (firstDuplicate == 0) {
					firstDuplicate = i;
					cache.clear();
				} else {
					var cycleLength = i - firstDuplicate - 1;
					i += cycleLength * ((REPETITIONS - i) / cycleLength);
					cache.clear();
				}
			}
		}
		return getSolution(getValue());
	}

	@Override
	protected boolean performInstruction(Consumer<StringBuilder> consumer, StringBuilder chars) {
		consumer.accept(chars);
		return false;
	}

	@Override
	protected String getSolution(StringBuilder chars) {
		return chars.toString();
	}
}
