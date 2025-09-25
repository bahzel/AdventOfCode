package solution.y2018;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utils.Tree;
import utils.soution.MapSolution;

public class Day8_2 extends MapSolution<List<Integer>> {
	public static void main(String[] args) {
		new Day8_2().solve();
	}

	@Override
	protected List<Integer> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		return Arrays.asList(instructions.getFirst().split(" "));
	}

	@Override
	protected void transformInstruction(String instruction, List<Integer> integers) {
		integers.add(Integer.parseInt(instruction));
	}

	@Override
	protected String computeSolution(List<Integer> integers) {
		var parent = new Tree<List<Integer>>(new ArrayList<>());
		addChild(integers, 0, parent);
		return "" + getScore(parent.getChildren().getFirst());
	}

	private int addChild(List<Integer> integers, int index, Tree<List<Integer>> parent) {
		var child = new Tree<List<Integer>>(new ArrayList<>());
		parent.addChild(child);
		var childCount = integers.get(index);
		var metadataCount = integers.get(index + 1);
		index = index + 2;

		for (int i = 0; i < childCount; i++) {
			index = addChild(integers, index, child);
		}

		for (int i = 0; i < metadataCount; i++) {
			child.getValue().add(integers.get(index + i));
		}

		return index + metadataCount;
	}

	private long getScore(Tree<List<Integer>> parent) {
		if (parent.getChildren().isEmpty()) {
			return parent.getValue().stream().mapToLong(Integer::longValue).sum();
		}

		var sum = 0L;
		for (var child : parent.getValue()) {
			if (child > 0 && child <= parent.getChildren().size()) {
				sum += getScore(parent.getChildren().get(child - 1));
			}
		}

		return sum;
	}
}
