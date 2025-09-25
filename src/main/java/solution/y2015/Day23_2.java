package solution.y2015;

import org.apache.commons.lang3.function.TriConsumer;
import utils.soution.MapSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Day23_2 extends MapSolution<List<TriConsumer<AtomicLong, AtomicLong, AtomicInteger>>> {
	public static void main(String[] args) {
		new Day23_2().solve();
	}

	@Override
	protected List<TriConsumer<AtomicLong, AtomicLong, AtomicInteger>> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction,
			List<TriConsumer<AtomicLong, AtomicLong, AtomicInteger>> triConsumers) {
		var instructions = instruction.replace(",", "").split(" ");
		switch (instructions[0]) {
		case "hlf":
			if ("a".equals(instructions[1])) {
				triConsumers.add((registerA, registerB, instructionIndex) -> registerA.set(registerA.get() / 2));
			} else if ("b".equals(instructions[1])) {
				triConsumers.add((registerA, registerB, instructionIndex) -> registerB.set(registerB.get() / 2));
			} else {
				throw new IllegalArgumentException("Invalid instruction: " + instruction);
			}
			return;
		case "tpl":
			if ("a".equals(instructions[1])) {
				triConsumers.add((registerA, registerB, instructionIndex) -> registerA.set(registerA.get() * 3));
			} else if ("b".equals(instructions[1])) {
				triConsumers.add((registerA, registerB, instructionIndex) -> registerB.set(registerB.get() * 3));
			} else {
				throw new IllegalArgumentException("Invalid instruction: " + instruction);
			}
			return;
		case "inc":
			if ("a".equals(instructions[1])) {
				triConsumers.add((registerA, registerB, instructionIndex) -> registerA.incrementAndGet());
			} else if ("b".equals(instructions[1])) {
				triConsumers.add((registerA, registerB, instructionIndex) -> registerB.incrementAndGet());
			} else {
				throw new IllegalArgumentException("Invalid instruction: " + instruction);
			}
			return;
		case "jmp":
			int offset = Integer.parseInt(instructions[1]);
			triConsumers.add((registerA, registerB, instructionIndex) -> instructionIndex.set(
					instructionIndex.get() + offset - 1));
			return;
		case "jie":
			int offsetEven = Integer.parseInt(instructions[2]);
			if ("a".equals(instructions[1])) {
				triConsumers.add((registerA, registerB, instructionIndex) -> {
					if (registerA.get() % 2 == 0) {
						instructionIndex.set(instructionIndex.get() + offsetEven - 1);
					}
				});
			} else if ("b".equals(instructions[1])) {
				triConsumers.add((registerA, registerB, instructionIndex) -> {
					if (registerB.get() % 2 == 0) {
						instructionIndex.set(instructionIndex.get() + offsetEven - 1);
					}
				});
			} else {
				throw new IllegalArgumentException("Invalid instruction: " + instruction);
			}
			return;
		case "jio":
			int offsetOne = Integer.parseInt(instructions[2]);
			if ("a".equals(instructions[1])) {
				triConsumers.add((registerA, registerB, instructionIndex) -> {
					if (registerA.get() == 1) {
						instructionIndex.set(instructionIndex.get() + offsetOne - 1);
					}
				});
			} else if ("b".equals(instructions[1])) {
				triConsumers.add((registerA, registerB, instructionIndex) -> {
					if (registerB.get() == 1) {
						instructionIndex.set(instructionIndex.get() + offsetOne - 1);
					}
				});
			} else {
				throw new IllegalArgumentException("Invalid instruction: " + instruction);
			}
			return;
		default:
			throw new IllegalArgumentException("Invalid instruction: " + instruction);
		}
	}

	@Override
	protected String computeSolution(List<TriConsumer<AtomicLong, AtomicLong, AtomicInteger>> triConsumers) {
		var registerA = new AtomicLong(1);
		var registerB = new AtomicLong();
		for (var i = new AtomicInteger(); i.get() < triConsumers.size(); i.incrementAndGet()) {
			triConsumers.get(i.get()).accept(registerA, registerB, i);
		}
		return registerB.toString();
	}
}
