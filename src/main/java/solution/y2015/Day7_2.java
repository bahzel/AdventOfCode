package solution.y2015;

import org.apache.commons.lang3.math.NumberUtils;
import utils.soution.Solution;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Day7_2 extends Solution {
	public static void main(String[] args) {
		new Day7_2().solve();
	}

	@Override
	protected String doSolve() {
		Map<String, Integer> circuit = new HashMap<>();

		Queue<Gate> instructions = new LinkedList<>();
		for (var instruction : input) {
			instructions.add(mapInstruction(instruction, circuit));
		}

		while (!instructions.isEmpty()) {
			Gate instruction = instructions.remove();
			if (instruction.isReady()) {
				circuit.put(instruction.getDestination(), instruction.compute());
			} else {
				instructions.add(instruction);
			}
		}

		return circuit.get("a") + "";
	}

	private Gate mapInstruction(String instruction, Map<String, Integer> circuit) {
		var instructions = instruction.split(" -> ");
		var destination = new DynamicWire(instructions[1], circuit);

		var sources = instructions[0].split(" ");
		if (sources.length == 1) {
			if ("b".equals(destination.getIdentifier())) {
				return new AssignmentGate(mapWire("16076", circuit), destination);
			} else {
				return new AssignmentGate(mapWire(sources[0], circuit), destination);
			}
		} else if (sources.length == 2) {
			return new NotGate(mapWire(sources[1], circuit), destination);
		} else if (sources.length == 3) {
			return switch (sources[1]) {
				case "AND" -> new AndGate(mapWire(sources[0], circuit), mapWire(sources[2], circuit), destination);
				case "OR" -> new OrGate(mapWire(sources[0], circuit), mapWire(sources[2], circuit), destination);
				case "RSHIFT" ->
						new ShiftRightGate(mapWire(sources[0], circuit), mapWire(sources[2], circuit), destination);
				case "LSHIFT" ->
						new ShiftLeftGate(mapWire(sources[0], circuit), mapWire(sources[2], circuit), destination);
				default -> throw new IllegalArgumentException("Unknown instruction: " + instruction);
			};
		} else {
			throw new IllegalArgumentException("Invalid instruction: " + instruction);
		}
	}

	private Wire mapWire(String identifier, Map<String, Integer> circuit) {
		if (NumberUtils.isCreatable(identifier)) {
			return new StaticWire(Integer.parseInt(identifier));
		} else {
			return new DynamicWire(identifier, circuit);
		}
	}
}
