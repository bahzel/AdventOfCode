package solution.y2015;

import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day18_2 extends GridSolution<Boolean> {
    public static void main(String[] args) {
        new Day18_2().solve();
    }

    @Override
    protected GridElement<Boolean> transformCell(char ch, int x, int y) {
        return switch (ch) {
            case '#' -> new GridElement<>(true, x, y);
            case '.' -> new GridElement<>(false, x, y);
            default -> throw new IllegalArgumentException("Invalid character: " + ch);
        };
    }

    @Override
    protected String computeSolution() {
        grid[0][0].setValue(true);
        grid[0][grid[0].length - 1].setValue(true);
        grid[grid.length - 1][0].setValue(true);
        grid[grid.length - 1][grid[0].length - 1].setValue(true);

        for (int i = 0; i < 100; i++) {
            performStep();
        }

        return stream().filter(GridElement::getValue).count() + "";
    }

    private void performStep() {
        stream().forEach(this::computeNextValue);

        grid[0][0].setNextValue(true);
        grid[0][grid[0].length - 1].setNextValue(true);
        grid[grid.length - 1][0].setNextValue(true);
        grid[grid.length - 1][grid[0].length - 1].setNextValue(true);

        assignNextValue();
    }

    private void computeNextValue(GridElement<Boolean> element) {
        var activeNeighbours = element.getNeighbours().stream().filter(GridElement::getValue).count();
        if (element.getValue()) {
            element.setNextValue(activeNeighbours == 2 || activeNeighbours == 3);
        } else {
            element.setNextValue(activeNeighbours == 3);
        }
    }
}
