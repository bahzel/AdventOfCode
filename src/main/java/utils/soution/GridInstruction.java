package utils.soution;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public abstract class GridInstruction<Element, Instruction> extends Solution {
    private GridElement<Element>[][] grid;
    private List<Instruction> instructions;

    protected GridInstruction() {
        super();
    }

    @Override
    protected void initialize() {
        grid = new GridElement[getWidth()][getHeigth()];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = new GridElement<>(initialValue(), i, j);
            }
        }
        instructions = getInstructions(input).stream().map(this::transformInstruction).toList();
    }

    protected abstract int getWidth();

    protected abstract int getHeigth();

    protected abstract Element initialValue();

    protected List<String> getInstructions(List<String> instructions) {
        return instructions;
    }

    protected abstract Instruction transformInstruction(String instruction);

    @Override
    public String doSolve() {
        instructions.forEach(instruction -> performInstruction(instruction, grid));
        return getSolution(grid);
    }

    protected abstract void performInstruction(Instruction instruction, GridElement<Element>[][] grid);

    protected abstract String getSolution(GridElement<Element>[][] grid);

    public Stream<GridElement<Element>> stream() {
        return Arrays.stream(grid).flatMap(Arrays::stream);
    }
}
