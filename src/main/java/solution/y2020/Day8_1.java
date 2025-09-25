package solution.y2020;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

import utils.soution.MapSolution;

public class Day8_1 extends MapSolution<List<BiConsumer<AtomicInteger, AtomicInteger>>> {
	public static void main(String[] args) {
		new Day8_1().solve();
	}

	@Override
	protected List<BiConsumer<AtomicInteger, AtomicInteger>> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction,
			List<BiConsumer<AtomicInteger, AtomicInteger>> biConsumers) {
		var instructions = instruction.split(" ");
		var value = Integer.parseInt(instructions[1]);
		switch (instructions[0]) {
		case "acc":
			biConsumers.add((accumulator, index) -> accumulator.addAndGet(value));
			break;
		case "jmp":
			biConsumers.add((accumulator, index) -> index.addAndGet(value - 1));
			break;
		case "nop":
			biConsumers.add((accumulator, index) -> {
			});
			break;
		}
	}

	@Override
	protected String computeSolution(List<BiConsumer<AtomicInteger, AtomicInteger>> biConsumers) {
		var cache = new HashSet<Integer>();

		var accumulator = new AtomicInteger();
		for (var index = new AtomicInteger(); index.get() < biConsumers.size(); index.incrementAndGet()) {
			if (!cache.add(index.get())) {
				return accumulator.toString();
			}
			biConsumers.get(index.get()).accept(accumulator, index);
		}
		throw new IllegalStateException();
	}
}
