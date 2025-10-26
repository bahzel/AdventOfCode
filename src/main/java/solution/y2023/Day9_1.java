package solution.y2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import utils.soution.InstructionSolution;

public class Day9_1 extends InstructionSolution<List<Long>, AtomicLong> {
	public static void main(String[] args) {
		new Day9_1().solve();
	}

	@Override
	protected AtomicLong initializeValue() {
		return new AtomicLong();
	}

	@Override
	protected List<Long> transformInstruction(String instruction) {
		return new ArrayList<>(Arrays.stream(instruction.split(" ")).map(Long::parseLong).toList());
	}

	@Override
	protected boolean performInstruction(List<Long> longs, AtomicLong atomicLong) {
		var tree = new ArrayList<>(List.of(longs));

		while (tree.getLast().stream().anyMatch(x -> x != 0L)) {
			var nextList = new ArrayList<Long>();
			for (var i = 1; i < tree.getLast().size(); i++) {
				nextList.add(tree.getLast().get(i) - tree.getLast().get(i - 1));
			}
			tree.add(nextList);
		}

		tree.getLast().add(0L);
		for (var i = tree.size() - 2; i >= 0; i--) {
			tree.get(i).add(tree.get(i + 1).getLast() + tree.get(i).getLast());
		}

		atomicLong.addAndGet(tree.getFirst().getLast());
		return false;
	}

	@Override
	protected String getSolution(AtomicLong atomicLong) {
		return atomicLong.toString();
	}
}