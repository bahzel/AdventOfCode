package utils;

import java.util.List;

public abstract class InstructionSolution<Instruction, Value> extends Solution {
	private Value value;
	private List<Instruction> instructions;

	public InstructionSolution() {
		super();
	}

	public InstructionSolution(List<String> input) {
		super(input);
	}

	@Override
	protected void initialize() {
		value = initializeValue();
		instructions = getInstructions(input).stream().map(this::transformInstruction).toList();
	}

	@Override
	public String doSolve() {
		instructions.forEach(instruction -> performInstruction(instruction, value));
		return getSolution(value);
	}

	protected List<String> getInstructions(List<String> instructions) {
		return instructions;
	}

	protected abstract Value initializeValue();

	protected abstract Instruction transformInstruction(String instruction);

	protected abstract void performInstruction(Instruction instruction, Value value);

	protected abstract String getSolution(Value value);
}
