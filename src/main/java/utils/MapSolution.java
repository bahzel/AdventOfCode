package utils;

import java.util.List;

public abstract class MapSolution<Mapping> extends Solution {
	private Mapping mapping;

	@Override
	protected void initialize() {
		mapping = initializeMapping();
		getInstructions(input).forEach(instruction -> transformInstruction(instruction, mapping));
	}

	@Override
	public String doSolve() {
		return computeSolution(mapping);
	}

	protected List<String> getInstructions(List<String> instructions) {
		return instructions;
	}

	protected abstract Mapping initializeMapping();

	protected abstract void transformInstruction(String instruction, Mapping mapping);

	protected abstract String computeSolution(Mapping mapping);
}
