package solution.y2020;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.function.TriConsumer;

import utils.soution.MapSolution;

public class Day8_2 extends MapSolution<List<TriConsumer<AtomicInteger, AtomicInteger, Boolean>>> {
	public static void main(String[] args) {
		new Day8_2().solve();
	}

	@Override
	protected List<TriConsumer<AtomicInteger, AtomicInteger, Boolean>> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction,
			List<TriConsumer<AtomicInteger, AtomicInteger, Boolean>> biConsumers) {
		var instructions = instruction.split(" ");
		var value = Integer.parseInt(instructions[1]);
		switch (instructions[0]) {
		case "acc":
			biConsumers.add((accumulator, index, reverse) -> accumulator.addAndGet(value));
			break;
		case "jmp":
			biConsumers.add((accumulator, index, reverse) -> {
				if (!reverse) {
					index.addAndGet(value - 1);
				}
			});
			break;
		case "nop":
			biConsumers.add((accumulator, index, reverse) -> {
				if (reverse) {
					index.addAndGet(value - 1);
				}
			});
			break;
		}
	}

	@Override
	protected String computeSolution(List<TriConsumer<AtomicInteger, AtomicInteger, Boolean>> biConsumers) {
		for (int i = 0; i < biConsumers.size(); i++) {
			var value = execute(biConsumers, i);
			if (value != null) {
				return value + "";
			}
		}
		throw new RuntimeException("No solution found");
	}

	private Integer execute(List<TriConsumer<AtomicInteger, AtomicInteger, Boolean>> biConsumers, int revertIndex) {
		var cache = new HashSet<Integer>();

		var accumulator = new AtomicInteger();
		for (var index = new AtomicInteger(); index.get() < biConsumers.size(); index.incrementAndGet()) {
			if (!cache.add(index.get())) {
				return null;
			}
			biConsumers.get(index.get()).accept(accumulator, index, index.get() == revertIndex);
		}
		return accumulator.intValue();
	}
}
