package solution.y2017;

import java.util.HashMap;
import java.util.Map;

import utils.GridElement;
import utils.Point;
import utils.Solution;

public class Day3_2 extends Solution {
    public static void main(String[] args) {
        new Day3_2().solve();
    }

    @Override
    protected String doSolve() {
        var goal = Long.parseLong(input.getFirst());
        return getValueOver(goal) + "";
    }

    private long getValueOver(long goal) {
        var currentPoint = new GridElement<>(1L, 0, 0);
        var neighbours = new HashMap<Point, Long>();
        neighbours.put(currentPoint.getCoordinates(), 1L);

        for (int i = 1; true; i = i + 2) {
            for (int j = 0; j < i; j++) {
                var nextCoordinates = new Point(currentPoint.getCoordinates().getX() + 1, currentPoint.getCoordinates().getY());
                var nextValue = computeValue(nextCoordinates, neighbours);
                neighbours.put(nextCoordinates, nextValue);
                currentPoint = new GridElement<>(nextValue, nextCoordinates);
                if (nextValue >= goal) {
                    return nextValue;
                }
            }

            for (int j = 0; j < i; j++) {
                var nextCoordinates = new Point(currentPoint.getCoordinates().getX(), currentPoint.getCoordinates().getY() - 1);
                var nextValue = computeValue(nextCoordinates, neighbours);
                neighbours.put(nextCoordinates, nextValue);
                currentPoint = new GridElement<>(nextValue, nextCoordinates);
                if (nextValue >= goal) {
                    return nextValue;
                }
            }

            for (int j = 0; j <= i; j++) {
                var nextCoordinates = new Point(currentPoint.getCoordinates().getX() - 1, currentPoint.getCoordinates().getY());
                var nextValue = computeValue(nextCoordinates, neighbours);
                neighbours.put(nextCoordinates, nextValue);
                currentPoint = new GridElement<>(nextValue, nextCoordinates);
                if (nextValue >= goal) {
                    return nextValue;
                }
            }

            for (int j = 0; j <= i; j++) {
                var nextCoordinates = new Point(currentPoint.getCoordinates().getX(), currentPoint.getCoordinates().getY() + 1);
                var nextValue = computeValue(nextCoordinates, neighbours);
                neighbours.put(nextCoordinates, nextValue);
                currentPoint = new GridElement<>(nextValue, nextCoordinates);
                if (nextValue >= goal) {
                    return nextValue;
                }
            }
        }
    }

    private long computeValue(Point point, Map<Point, Long> neighbours) {
        long value = 0L;
        value += neighbours.getOrDefault(new Point(point.getX() - 1, point.getY() - 1), 0L);
        value += neighbours.getOrDefault(new Point(point.getX() - 1, point.getY()), 0L);
        value += neighbours.getOrDefault(new Point(point.getX() - 1, point.getY() + 1), 0L);
        value += neighbours.getOrDefault(new Point(point.getX(), point.getY() - 1), 0L);
        value += neighbours.getOrDefault(new Point(point.getX(), point.getY()), 0L);
        value += neighbours.getOrDefault(new Point(point.getX(), point.getY() + 1), 0L);
        value += neighbours.getOrDefault(new Point(point.getX() + 1, point.getY() - 1), 0L);
        value += neighbours.getOrDefault(new Point(point.getX() + 1, point.getY()), 0L);
        value += neighbours.getOrDefault(new Point(point.getX() + 1, point.getY() + 1), 0L);
        return value;
    }
}
