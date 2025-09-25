package solution.y2017;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import utils.StringTransformer;
import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day14_2 extends GridSolution<Boolean> {
    public static void main(String[] args) {
        new Day14_2().solve();
    }

    @Override
    protected List<String> getInstructions(List<String> instructions) {
        var key = instructions.getFirst();
        var rows = new ArrayList<String>();

        for (int i = 0; i < 128; i++) {
            rows.add(StringTransformer.hexToBin(new Day10_2(key + "-" + i).solve()));
        }

        return rows;
    }

    @Override
    protected GridElement<Boolean> transformCell(char ch, int x, int y) {
        return switch (ch) {
            case '0' -> new GridElement<>(false, x, y);
            case '1' -> new GridElement<>(true, x, y);
            default -> throw new IllegalArgumentException("Invalid character: " + ch);
        };
    }

    @Override
    protected String computeSolution() {
        var count = 0;
        while (removeGroup()) {
            count++;
        }
        return count + "";
    }

    private boolean removeGroup() {
        var usedCell = stream().filter(GridElement::getValue).findFirst();
        if (usedCell.isPresent()) {
            removePoint(usedCell.get());
            return true;
        } else {
            return false;
        }
    }

    private void removePoint(GridElement<Boolean> element) {
        Queue<GridElement<Boolean>> queue = new LinkedList<>();
        queue.add(element);

        while (!queue.isEmpty()) {
            var current = queue.remove();
            if (current == null || !current.getValue()) {
                continue;
            }

            current.setValue(false);
            queue.add(current.getLeftNeighbour());
            queue.add(current.getRightNeighbour());
            queue.add(current.getUpperNeighbour());
            queue.add(current.getLowerNeighbour());
        }
    }
}
