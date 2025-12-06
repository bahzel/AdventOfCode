package solution.y2025;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import utils.soution.InstructionSolution;

public class Day5_2 extends InstructionSolution<Pair<Long, Long>, List<Pair<Long, Long>>> {
	public static void main(String[] args) {
		new Day5_2().solve();
	}

	@Override
	protected List<Pair<Long, Long>> initializeValue() {
		return new ArrayList<>();
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		return instructions.subList(0, instructions.indexOf(""));
	}

	@Override
	protected Pair<Long, Long> transformInstruction(String instruction) {
		var values = instruction.split("-");
		return Pair.of(Long.parseLong(values[0]), Long.parseLong(values[1]));
	}

	@Override
	protected boolean performInstruction(Pair<Long, Long> longLongPair, List<Pair<Long, Long>> pairs) {
		var addPairs = new ArrayList<>(Collections.singletonList(longLongPair));

		for (var i = 0; i < addPairs.size(); i++) {
			var addPair = addPairs.get(i);
			for (var pair : pairs) {
				if (addPair.getLeft() < pair.getLeft() && addPair.getRight() > pair.getRight()) {
					addPairs.remove(i);
					addPairs.add(Pair.of(addPair.getLeft(), pair.getLeft() - 1));
					addPairs.add(Pair.of(pair.getRight() + 1, addPair.getRight()));
					i--;
					break;
				} else if (addPair.getLeft() >= pair.getLeft() && addPair.getLeft() <= pair.getRight()
						&& addPair.getRight() >= pair.getLeft() && addPair.getRight() <= pair.getRight()) {
					addPairs.remove(i);
					i--;
					break;
				} else if (addPair.getLeft() >= pair.getLeft() && addPair.getLeft() <= pair.getRight()) {
					addPairs.remove(i);
					addPairs.add(Pair.of(pair.getRight() + 1, addPair.getRight()));
					i--;
					break;
				} else if (addPair.getRight() >= pair.getLeft() && addPair.getRight() <= pair.getRight()) {
					addPairs.remove(i);
					addPairs.add(Pair.of(addPair.getLeft(), pair.getLeft() - 1));
					i--;
					break;
				}
			}
		}
		pairs.addAll(addPairs);

		return false;
	}

	@Override
	protected String getSolution(List<Pair<Long, Long>> pairs) {
		return pairs.stream().mapToLong(pair -> pair.getRight() - pair.getLeft() + 1).sum() + "";
	}
}
