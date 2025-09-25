package utils;

import java.util.List;

public abstract class InstructionSolution<Instruction, Value> extends Solution {
	public InstructionSolution() {
		super();
	}

	public InstructionSolution(List<String> input) {
		super(input);
	}

	@Override
	public String doSolve() {
		var value = initializeValue();
		getInstructions(input).stream()
							  .map(this::transformInstruction)
							  .forEach(instruction -> performInstruction(instruction, value));
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
