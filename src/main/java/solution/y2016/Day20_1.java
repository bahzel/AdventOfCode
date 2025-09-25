package solution.y2016;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import utils.MapSolution;

public class Day20_1 extends MapSolution<List<Pair<Long, Long>>> {
    public static void main(String[] args) {
        new Day20_1().solve();
    }

    @Override
    protected List<Pair<Long, Long>> initializeMapping() {
        return new ArrayList<>();
    }

    @Override
    protected void transformInstruction(String instruction, List<Pair<Long, Long>> pairs) {
        var instructions = instruction.split("-");
        pairs.add(Pair.of(Long.parseLong(instructions[0]), Long.parseLong(instructions[1])));
    }

    @Override
    protected String computeSolution(List<Pair<Long, Long>> pairs) {
        for (var i = 0L; i < Long.MAX_VALUE; i++) {
            var found = false;
            for (var pair : pairs) {
                if (pair.getLeft() <= i && pair.getRight() >= i) {
                    i = pair.getRight();
                    found = true;
                }
            }
            if (!found) {
                return i + "";
            }
        }

        throw new IllegalStateException("No solution found");
    }
}
