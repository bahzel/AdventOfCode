package utils.soution;

import java.util.List;

public abstract class GridInstructionSolution<Element, Instruction> extends GridSolution<Element> {
	private List<Instruction> instructions;

	@Override
	protected void initialize() {
		input = getInstructions(input);
		initializeGrid();
		instructions = getInstructionStrings(input.subList(input.indexOf("") + 1, input.size())).stream()
																								.map(this::transformInstruction)
																								.toList();
	}

	protected List<String> getInstructionStrings(List<String> instructions) {
		return instructions;
	}

	private void initializeGrid() {
		var height = input.indexOf("");

		grid = new GridElement[input.getFirst().length()][height];

		for (int i = 0; i < height; i++) {
			var row = input.get(i);
			for (int j = 0; j < row.length(); j++) {
				grid[j][i] = transformCell(row.charAt(j), j, i);
			}
		}

		initializeNeighbours();
	}

	protected abstract Instruction transformInstruction(String instruction);

	@Override
	public String doSolve() {
		instructions.forEach(this::performInstruction);
		return computeSolution();
	}

	protected abstract void performInstruction(Instruction instruction);
}
