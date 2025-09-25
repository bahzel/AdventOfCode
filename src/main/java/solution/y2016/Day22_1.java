package solution.y2016;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Triple;
import utils.MapSolution;
import utils.StringTransformer;

public class Day22_1 extends MapSolution<List<Triple<String, Integer, Integer>>> {
    public static void main(String[] args) {
        new Day22_1().solve();
    }

    @Override
    protected List<Triple<String, Integer, Integer>> initializeMapping() {
        return new ArrayList<>();
    }

    @Override
    protected void transformInstruction(String instruction, List<Triple<String, Integer, Integer>> triples) {
        if (!instruction.startsWith("/")) {
            return;
        }

        var instructions = StringTransformer.removeDuplicateWhitespaces(instruction).replace("T", "").split(" ");
        triples.add(Triple.of(instructions[0], Integer.parseInt(instructions[2]), Integer.parseInt(instructions[3])));
    }

    @Override
    protected String computeSolution(List<Triple<String, Integer, Integer>> triples) {
        long solution = 0;

        for (var a : triples) {
            for (var b : triples) {
                if (a != b && a.getMiddle() > 0 && a.getMiddle() <= b.getRight()) {
                    solution++;
                }
            }
        }

        return solution + "";
    }
}
