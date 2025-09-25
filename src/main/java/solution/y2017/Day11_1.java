package solution.y2017;

import java.util.Arrays;
import java.util.List;

import utils.CardinalDirection;
import utils.Point;
import utils.soution.InstructionSolution;

public class Day11_1 extends InstructionSolution<CardinalDirection, Point> {
    public static void main(String[] args) {
        new Day11_1().solve();
    }

    @Override
    protected Point initializeValue() {
        return new Point(0, 0);
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
    protected boolean performInstruction(CardinalDirection cardinalDirection, Point point) {
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

        return false;
    }

    @Override
    protected String getSolution(Point point) {
        var xDiff = Math.abs(point.getX());
        var yDiff = Math.abs(point.getY());

        var solution = xDiff;
        if (yDiff > xDiff) {
            solution += (yDiff - xDiff) / 2;
        }
        return solution + "";
    }
}
