package solution.y2022;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import utils.soution.MapSolution;

public class Day21_1 extends MapSolution<Map<String, ComputingMonkey>> {
	public static void main(String[] args) {
		new Day21_1().solve();
	}

	@Override
	protected Map<String, ComputingMonkey> initializeMapping() {
		return new HashMap<>();
	}

	@Override
	protected void transformInstruction(String instruction, Map<String, ComputingMonkey> stringComputingMonkeyMap) {
		var instructions = instruction.replace(":", "").split(" ");
		if (instructions.length == 2) {
			stringComputingMonkeyMap.put(instructions[0], new NumberMonkey(Long.parseLong(instructions[1])));
		} else {
			switch (instructions[2]) {
			case "+" -> stringComputingMonkeyMap.put(instructions[0],
					new SumMonkey(instructions[1], instructions[3], stringComputingMonkeyMap));
			case "-" -> stringComputingMonkeyMap.put(instructions[0],
					new DifferenceMonkey(instructions[1], instructions[3], stringComputingMonkeyMap));
			case "*" -> stringComputingMonkeyMap.put(instructions[0],
					new ProductMonkey(instructions[1], instructions[3], stringComputingMonkeyMap));
			case "/" -> stringComputingMonkeyMap.put(instructions[0],
					new DivisionMonkey(instructions[1], instructions[3], stringComputingMonkeyMap));
			}
		}
	}

	@Override
	protected String computeSolution(Map<String, ComputingMonkey> stringComputingMonkeyMap) {
		return stringComputingMonkeyMap.get("root").getValue() + "";
	}
}

abstract class ComputingMonkey {
	abstract long getValue();
}

@AllArgsConstructor
class NumberMonkey extends ComputingMonkey {
	private final long value;

	@Override
	long getValue() {
		return value;
	}
}

abstract class OperationMonkey extends ComputingMonkey {
	private final String left;
	private Long leftValue;
	private final String right;
	private Long rightValue;
	private final Map<String, ComputingMonkey> monkeyMap;

	public OperationMonkey(String left, String right, Map<String, ComputingMonkey> monkeyMap) {
		this.left = left;
		this.right = right;
		this.monkeyMap = monkeyMap;
	}

	protected long getLeftValue() {
		if (leftValue == null) {
			leftValue = monkeyMap.get(left).getValue();
		}
		return leftValue;
	}

	protected long getRightValue() {
		if (rightValue == null) {
			rightValue = monkeyMap.get(right).getValue();
		}
		return rightValue;
	}
}

class SumMonkey extends OperationMonkey {
	public SumMonkey(String left, String right, Map<String, ComputingMonkey> monkeyMap) {
		super(left, right, monkeyMap);
	}

	@Override
	long getValue() {
		return getLeftValue() + getRightValue();
	}
}

class DifferenceMonkey extends OperationMonkey {
	public DifferenceMonkey(String left, String right, Map<String, ComputingMonkey> monkeyMap) {
		super(left, right, monkeyMap);
	}

	@Override
	long getValue() {
		return getLeftValue() - getRightValue();
	}
}

class ProductMonkey extends OperationMonkey {
	public ProductMonkey(String left, String right, Map<String, ComputingMonkey> monkeyMap) {
		super(left, right, monkeyMap);
	}

	@Override
	long getValue() {
		return getLeftValue() * getRightValue();
	}
}

class DivisionMonkey extends OperationMonkey {
	public DivisionMonkey(String left, String right, Map<String, ComputingMonkey> monkeyMap) {
		super(left, right, monkeyMap);
	}

	@Override
	long getValue() {
		return getLeftValue() / getRightValue();
	}
}