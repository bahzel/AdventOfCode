package solution.y2016;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Triple;
import utils.MapSolution;
import utils.Point;
import utils.StringTransformer;

public class Day22_2 extends MapSolution<List<Triple<Point, Integer, Integer>>> {
    public static void main(String[] args) {
        new Day22_2().solve();
    }

    @Override
    protected List<Triple<Point, Integer, Integer>> initializeMapping() {
        return new ArrayList<>();
    }

    @Override
    protected void transformInstruction(String instruction, List<Triple<Point, Integer, Integer>> triples) {
        if (!instruction.startsWith("/")) {
            return;
        }

        var instructions = StringTransformer.removeDuplicateWhitespaces(instruction).replace("T", "").split(" ");
        var coordinates = instructions[0].replace("y", "x").split("-x");
        triples.add(Triple.of(new Point(Integer.parseInt(coordinates[1]), Integer.parseInt(coordinates[2])), Integer.parseInt(instructions[2]), Integer.parseInt(instructions[3])));
    }

    @Override
    protected String computeSolution(List<Triple<Point, Integer, Integer>> triples) {
        var width = triples.stream().map(point -> point.getLeft().getX()).mapToInt(x -> x).max().orElseThrow();
        var emptyTile = triples.stream().filter(tile -> tile.getMiddle() == 0).findAny().orElseThrow().getLeft();
        var rowOfWall = triples.stream().filter(tile -> tile.getMiddle() > 100).findAny().orElseThrow().getLeft().getY();
        var columnOfGap = triples.stream().filter(tile -> tile.getMiddle() < 100 && tile.getLeft().getY() == rowOfWall).map(tile -> tile.getLeft().getX()).mapToInt(x -> x).max().orElseThrow();

        return emptyTile.getX() - columnOfGap + emptyTile.getY() + width - columnOfGap + 5 * (width - 1) + "";
    }
}
