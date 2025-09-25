package solution.y2015;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.apache.commons.lang3.math.NumberUtils;

import utils.soution.Solution;

public class Day7_1 extends Solution {
	public static void main(String[] args) {
		new Day7_1().solve();
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
			return new AssignmentGate(mapWire(sources[0], circuit), destination);
		} else if (sources.length == 2) {
			return new NotGate(mapWire(sources[1], circuit), destination);
		} else if (sources.length == 3) {
			return switch (sources[1]) {
			case "AND" -> new AndGate(mapWire(sources[0], circuit), mapWire(sources[2], circuit), destination);
			case "OR" -> new OrGate(mapWire(sources[0], circuit), mapWire(sources[2], circuit), destination);
			case "RSHIFT" ->
				new ShiftRightGate(mapWire(sources[0], circuit), mapWire(sources[2], circuit), destination);
			case "LSHIFT" -> new ShiftLeftGate(mapWire(sources[0], circuit), mapWire(sources[2], circuit), destination);
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

abstract class Wire {
	public abstract boolean isReady();

	public abstract int getSignal();

	public abstract String getIdentifier();
}

class StaticWire extends Wire {
	private final int signal;

	public StaticWire(int signal) {
		this.signal = signal;
	}

	@Override
	public boolean isReady() {
		return true;
	}

	@Override
	public int getSignal() {
		return signal;
	}

	@Override
	public String getIdentifier() {
		throw new IllegalStateException();
	}
}

class DynamicWire extends Wire {
	private final String identifier;
	private final Map<String, Integer> circuit;

	public DynamicWire(String identifier, Map<String, Integer> circuit) {
		this.identifier = identifier;
		this.circuit = circuit;
	}

	@Override
	public boolean isReady() {
		return circuit.containsKey(identifier);
	}

	@Override
	public int getSignal() {
		return circuit.get(identifier);
	}

	@Override
	public String getIdentifier() {
		return identifier;
	}
}

abstract class Gate {
	protected final Wire source1;
	protected final Wire source2;
	private final DynamicWire destination;

	public Gate(Wire source1, DynamicWire destination) {
		this(source1, null, destination);
	}

	public Gate(Wire source1, Wire source2, DynamicWire destination) {
		this.source1 = source1;
		this.source2 = source2;
		this.destination = destination;
	}

	public boolean isReady() {
		return source1.isReady() && (source2 == null || source2.isReady());
	}

	public abstract int compute();

	public String getDestination() {
		return destination.getIdentifier();
	}
}

class AssignmentGate extends Gate {
	public AssignmentGate(Wire source1, DynamicWire destination) {
		super(source1, destination);
	}

	@Override
	public int compute() {
		return source1.getSignal();
	}
}

class NotGate extends Gate {
	public NotGate(Wire source1, DynamicWire destination) {
		super(source1, destination);
	}

	@Override
	public int compute() {
		return ~source1.getSignal();
	}
}

class AndGate extends Gate {
	public AndGate(Wire source1, Wire source2, DynamicWire destination) {
		super(source1, source2, destination);
	}

	@Override
	public int compute() {
		return source1.getSignal() & source2.getSignal();
	}
}

class OrGate extends Gate {
	public OrGate(Wire source1, Wire source2, DynamicWire destination) {
		super(source1, source2, destination);
	}

	@Override
	public int compute() {
		return source1.getSignal() | source2.getSignal();
	}
}

class ShiftLeftGate extends Gate {
	public ShiftLeftGate(Wire source1, Wire source2, DynamicWire destination) {
		super(source1, source2, destination);
	}

	@Override
	public int compute() {
		return source1.getSignal() << source2.getSignal();
	}
}

class ShiftRightGate extends Gate {
	public ShiftRightGate(Wire source1, Wire source2, DynamicWire destination) {
		super(source1, source2, destination);
	}

	@Override
	public int compute() {
		return source1.getSignal() >> source2.getSignal();
	}
}
