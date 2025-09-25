package solution.y2017;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.tuple.Pair;
import utils.CardinalDirection;
import utils.Point;
import utils.soution.InstructionSolution;

public class Day11_2 extends InstructionSolution<CardinalDirection, Pair<Point, AtomicInteger>> {
    public static void main(String[] args) {
        new Day11_2().solve();
    }

    @Override
    protected Pair<Point, AtomicInteger> initializeValue() {
        return Pair.of(new Point(0, 0), new AtomicInteger());
    }

    @Override
    protected List<String> getInstructions(List<String> instructions) {
        return Arrays.asList(instructions.getFirst().split(","));
    }

    @Override
    protected CardinalDirection transformInstruction(String instruction) {
        return switch (instruction) {
            case "n" -> CardinalDirection.NORTH;
            case "ne" -> CardinalDirection.NORTH_EAST;
            case "se" -> CardinalDirection.SOUTH_EAST;
            case "s" -> CardinalDirection.SOUTH;
            case "sw" -> CardinalDirection.SOUTH_WEST;
            case "nw" -> CardinalDirection.NORTH_WEST;
            default -> throw new IllegalArgumentException("Invalid instruction: " + instruction);
        };
    }

    @Override
    protected boolean performInstruction(CardinalDirection cardinalDirection, Pair<Point, AtomicInteger> pair) {
        var point = pair.getLeft();
        switch (cardinalDirection) {
            case NORTH:
                point.setY(point.getY() - 2);
                break;
            case NORTH_EAST:
                point.setY(point.getY() - 1);
                point.setX(point.getX() + 1);
                break;
            case SOUTH_EAST:
                point.setY(point.getY() + 1);
                point.setX(point.getX() + 1);
                break;
            case SOUTH:
                point.setY(point.getY() + 2);
                break;
            case SOUTH_WEST:
                point.setY(point.getY() + 1);
                point.setX(point.getX() - 1);
                break;
            case NORTH_WEST:
                point.setY(point.getY() - 1);
                point.setX(point.getX() - 1);
        }

        pair.getRight().set(Math.max(pair.getRight().get(), computeDistance(point)));

        return false;
    }

    protected int computeDistance(Point point) {
        var xDiff = Math.abs(point.getX());
        var yDiff = Math.abs(point.getY());

        var solution = xDiff;
        if (yDiff > xDiff) {
            solution += (yDiff - xDiff) / 2;
        }
        return solution;
    }

    @Override
    protected String getSolution(Pair<Point, AtomicInteger> point) {
        return point.getRight().toString();
    }
}
