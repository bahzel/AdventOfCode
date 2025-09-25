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
		for (Instruction instruction : instructions) {
			if (performInstruction(instruction, value)) {
				break;
			}
		}
		return getSolution(value);
	}

	protected List<String> getInstructions(List<String> instructions) {
		return instructions;
	}

	protected abstract Value initializeValue();

	protected abstract Instruction transformInstruction(String instruction);

	/**
	 * @return true if the algorithm shall finish after this instruction
	 */
	protected abstract boolean performInstruction(Instruction instruction, Value value);

	protected abstract String getSolution(Value value);
}
