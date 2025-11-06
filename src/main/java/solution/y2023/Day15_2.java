package solution.y2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.lang3.tuple.Pair;

import utils.soution.InstructionSolution;

public class Day15_2
		extends InstructionSolution<Consumer<List<List<Pair<String, Integer>>>>, List<List<Pair<String, Integer>>>> {
	public static void main(String[] args) {
		new Day15_2().solve();
	}

	@Override
	protected List<List<Pair<String, Integer>>> initializeValue() {
		var boxes = new ArrayList<List<Pair<String, Integer>>>();
		for (var i = 0; i < 256; i++) {
			boxes.add(new ArrayList<>());
		}
		return boxes;
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		return Arrays.asList(instructions.getFirst().split(","));
	}

	@Override
	protected Consumer<List<List<Pair<String, Integer>>>> transformInstruction(String instruction) {
		if (instruction.contains("-")) {
			var key = instruction.substring(0, instruction.length() - 1);
			return boxes -> boxes.get(computeHash(key)).removeIf(entry -> entry.getLeft().equals(key));
		} else {
			var instructions = instruction.split("=");
			var lens = Pair.of(instructions[0], Integer.parseInt(instructions[1]));
			return boxes -> {
				var box = boxes.get(computeHash(instructions[0]));
				for (var i = 0; i < box.size(); i++) {
					if (box.get(i).getLeft().equals(instructions[0])) {
						box.remove(i);
						box.add(i, lens);
						return;
					}
				}
				box.add(lens);
			};
		}
	}

	private int computeHash(String text) {
		var value = 0;
		for (var i = 0; i < text.length(); i++) {
			value += text.charAt(i);
			value *= 17;
			value %= 256;
		}
		return value;
	}

	@Override
	protected boolean performInstruction(Consumer<List<List<Pair<String, Integer>>>> instruction,
			List<List<Pair<String, Integer>>> boxes) {
		instruction.accept(boxes);
		return false;
	}

	@Override
	protected String getSolution(List<List<Pair<String, Integer>>> boxes) {
		var solution = 0;
		for (var i = 0; i < boxes.size(); i++) {
			for (var j = 0; j < boxes.get(i).size(); j++) {
				solution += (i + 1) * (j + 1) * boxes.get(i).get(j).getRight();
			}
		}
		return solution + "";
	}
}