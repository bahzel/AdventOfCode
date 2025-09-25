package utils.soution;

import java.util.List;

public abstract class InstructionWithInputSolution<Instruction, Input, Value> extends Solution {
	protected Value value;
	protected List<Instruction> instructions;
	protected Input inputValue;

	public InstructionWithInputSolution() {
		super();
	}

	public InstructionWithInputSolution(List<String> input) {
		super(input);
	}

	@Override
	protected void initialize() {
		value = initializeValue();
		instructions = getInstructions(input).stream().map(this::transformInstruction).toList();
		inputValue = transformInput(getInput(input));
	}

	@Override
	public String doSolve() {
		instructions.forEach(instruction -> performInstruction(instruction, inputValue, value));
		return getSolution(value);
	}

	protected List<String> getInstructions(List<String> instructions) {
		return instructions.subList(0, instructions.size() - 2);
	}

	protected String getInput(List<String> instructions) {
		return instructions.getLast();
	}

	protected abstract Value initializeValue();

	protected abstract Instruction transformInstruction(String instruction);

	protected abstract Input transformInput(String input);

	protected abstract void performInstruction(Instruction instruction, Input input, Value value);

	protected abstract String getSolution(Value value);
}
