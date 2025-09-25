package solution.y2016;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import utils.Point;
import utils.soution.Solution;

public class Day13_2 extends Solution {
    private final Set<Point> CACHE = new HashSet<>();
    private int favouriteNumber;

    public static void main(String[] args) {
        new Day13_2().solve();
    }

    @Override
    protected String doSolve() {
        favouriteNumber = Integer.parseInt(input.getFirst());
        return "" + computePossibleCoordinates(new Point(1, 1), 50);
    }

    private int computePossibleCoordinates(Point start, int steps) {
        Queue<Pair<Point, Integer>> queue = new LinkedList<>();
        queue.add(Pair.of(start, 0));

        while (!queue.isEmpty()) {
            var currentCandidate = queue.poll();
            var currentPoint = currentCandidate.getLeft();
            var nextLength = currentCandidate.getRight() + 1;

            if (nextLength > steps) {
                break;
            }

            if (currentPoint.getX() > 0) {
                addPoint(currentPoint.getLeftNeighbour(), nextLength, queue);
            }
            if (currentPoint.getY() > 0) {
                addPoint(currentPoint.getUpperNeighbour(), nextLength, queue);
            }
            addPoint(currentPoint.getRightNeighbour(), nextLength, queue);
            addPoint(currentPoint.getLowerNeighbour(), nextLength, queue);
        }

        return CACHE.size();
    }

    private void addPoint(Point point, int length, Queue<Pair<Point, Integer>> queue) {
        if (isValid(point) && CACHE.add(point)) {
            queue.add(Pair.of(point, length));
        }
    }

    private boolean isValid(Point point) {
        var sum = point.getX() * point.getX() + 3 * point.getX() + 2 * point.getX() * point.getY() + point.getY() + point.getY() * point.getY() + favouriteNumber;
        var binarySum = Integer.toBinaryString(sum);
        return StringUtils.countMatches(binarySum, "1") % 2 == 0;
    }
}
