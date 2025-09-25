package utils;

public abstract class InstructionSolution extends Solution {
	public void iterate(String instructions) {
		var instructionQueue = new InstructionQueue(instructions);
		while (instructionQueue.peek() != null) {
			performInstruction(instructionQueue.poll());
		}
	}

	protected abstract void performInstruction(Character instruction);
}
