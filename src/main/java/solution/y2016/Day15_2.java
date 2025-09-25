package solution.y2016;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import utils.MapSolution;

public class Day15_2 extends MapSolution<List<Pair<Integer, Integer>>> {
    public static void main(String[] args) {
        new Day15_2().solve();
    }

    @Override
    protected List<Pair<Integer, Integer>> initializeMapping() {
        return new ArrayList<>();
    }

    @Override
    protected void transformInstruction(String instruction, List<Pair<Integer, Integer>> pairs) {
        var instructions = instruction.replace(".", "").split(" ");
        pairs.add(Pair.of(Integer.parseInt(instructions[3]), Integer.parseInt(instructions[11])));
    }

    @Override
    protected String computeSolution(List<Pair<Integer, Integer>> pairs) {
        pairs.add(Pair.of(11, 0));

        for (var i = 1L; i < Long.MAX_VALUE; i++) {
            if (isValid(i, pairs)) {
                return i - 1 + "";
            }
        }
        throw new IllegalStateException("No solution found");
    }

    private boolean isValid(long time, List<Pair<Integer, Integer>> pairs) {
        for (var j = 0; j < pairs.size(); j++) {
            var pair = pairs.get(j);
            if ((pair.getRight() + time + j) % pair.getLeft() != 0) {
                return false;
            }
        }
        return true;
    }
}
