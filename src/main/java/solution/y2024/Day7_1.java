package solution.y2024;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.tuple.Pair;
import utils.soution.InstructionSolution;

public class Day7_1 extends InstructionSolution<Pair<List<Long>, Long>, AtomicLong> {
	public static void main(String[] args) {
		new Day7_1().solve();
	}

	@Override
	protected AtomicLong initializeValue() {
		return new AtomicLong();
	}

	@Override
	protected Pair<List<Long>, Long> transformInstruction(String instruction) {
		var instructions = instruction.split(": ");
		return Pair.of(Arrays.stream(instructions[1].split(" ")).mapToLong(Long::parseLong).boxed().toList(),
				Long.parseLong(instructions[0]));
	}

	@Override
	protected boolean performInstruction(Pair<List<Long>, Long> equation, AtomicLong solution) {
		Queue<Pair<List<Long>, Long>> queue = new LinkedList<>();
		queue.add(Pair.of(equation.getLeft().subList(1, equation.getLeft().size()), equation.getLeft().getFirst()));

		while (!queue.isEmpty()) {
			var subSolution = queue.poll();
			if (subSolution.getLeft().isEmpty()) {
				if (Objects.equals(subSolution.getRight(), equation.getRight())) {
					solution.addAndGet(equation.getRight());
					return false;
				}
				continue;
			}

			queue.add(Pair.of(subSolution.getLeft().subList(1, subSolution.getLeft().size()),
					subSolution.getRight() + subSolution.getLeft().getFirst()));
			queue.add(Pair.of(subSolution.getLeft().subList(1, subSolution.getLeft().size()),
					subSolution.getRight() * subSolution.getLeft().getFirst()));
		}

		return false;
	}

	@Override
	protected String getSolution(AtomicLong solution) {
		return solution.toString();
	}
}
