package solution.y2022;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.lang3.tuple.Pair;

import utils.soution.InstructionSolution;

public class Day10_2 extends InstructionSolution<Consumer<Pair<int[], StringBuilder>>, Pair<int[], StringBuilder>> {
	public static void main(String[] args) {
		new Day10_2().solve();
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		var transformedInstructions = new ArrayList<String>();
		for (var instruction : instructions) {
			var splitInstruction = instruction.split(" ");
			switch (splitInstruction[0]) {
			case "noop":
				transformedInstructions.add("0");
				break;
			case "addx":
				transformedInstructions.add("0");
				transformedInstructions.add(splitInstruction[1]);
				break;
			default:
				throw new IllegalArgumentException("Unknown instruction: " + splitInstruction[0]);
			}
		}
		return transformedInstructions;
	}

	@Override
	protected Pair<int[], StringBuilder> initializeValue() {
		return Pair.of(new int[] { 0, 1 }, new StringBuilder());
	}

	@Override
	protected Consumer<Pair<int[], StringBuilder>> transformInstruction(String instruction) {
		var amount = Integer.parseInt(instruction);
		return pair -> {
			if (pair.getLeft()[0] >= pair.getLeft()[1] - 1 && pair.getLeft()[0] <= pair.getLeft()[1] + 1) {
				pair.getRight().append("#");
			} else {
				pair.getRight().append(" ");
			}
			pair.getLeft()[0]++;
			pair.getLeft()[1] += amount;
			if (pair.getLeft()[0] == 40) {
				pair.getLeft()[0] = 0;
				pair.getRight().append("\n");
			}
		};
	}

	@Override
	protected boolean performInstruction(Consumer<Pair<int[], StringBuilder>> consumer,
			Pair<int[], StringBuilder> ints) {
		consumer.accept(ints);
		return false;
	}

	@Override
	protected String getSolution(Pair<int[], StringBuilder> ints) {
		return ints.getRight().toString();
	}
}
