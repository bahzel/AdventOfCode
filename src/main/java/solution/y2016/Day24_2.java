package solution.y2016;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.commons.lang3.tuple.Pair;
import utils.soution.GridElement;
import utils.soution.GridSolution;
import utils.Point;

public class Day24_2 extends GridSolution<Boolean> {
    private final GridElement<Boolean>[] locations = new GridElement[8];

    public static void main(String[] args) {
        new Day24_2().solve();
    }

    @Override
    protected GridElement<Boolean> transformCell(char ch, int x, int y) {
        if (ch == '#') {
            return new GridElement<>(false, x, y);
        } else if (ch == '.') {
            return new GridElement<>(true, x, y);
        } else {
            var element = new GridElement<>(true, x, y);
            locations[Integer.parseInt(ch + "")] = element;
            return element;
        }
    }

    @Override
    protected String computeSolution() {
        var leftLocations = new ArrayList<Integer>();
        for (int i = 1; i < locations.length; i++) {
            leftLocations.add(i);
        }
        return computeShortestDistance(0, 0, leftLocations, createDistanceMap()) + "";
    }

    private long computeShortestDistance(int currentLocation, long currentCost, List<Integer> leftLocations, Map<Integer, Long> distanceMap) {
        if (leftLocations.size() == 1) {
            return distanceMap.get(currentLocation * 10 + leftLocations.getFirst()) + distanceMap.get(leftLocations.getFirst() * 10) + currentCost;
        }

        long minimumDistance = Long.MAX_VALUE;
        for (var leftLocation : leftLocations) {
            var newLeftLocations = new ArrayList<>(leftLocations);
            newLeftLocations.remove(leftLocation);
            minimumDistance = Math.min(minimumDistance, computeShortestDistance(leftLocation, distanceMap.get(currentLocation * 10 + leftLocation) + currentCost, newLeftLocations, distanceMap));
        }
        return minimumDistance;
    }

    private Map<Integer, Long> createDistanceMap() {
        var distanceMap = new HashMap<Integer, Long>();

        for (int i = 0; i < locations.length; i++) {
            for (int j = i + 1; j < locations.length; j++) {
                var distance = computeDistance(locations[i], locations[j]);
                distanceMap.put(i * 10 + j, distance);
                distanceMap.put(j * 10 + i, distance);
            }
        }

        return distanceMap;
    }

    private long computeDistance(GridElement<Boolean> start, GridElement<Boolean> goal) {
        var CACHE = new HashSet<Point>();
        Queue<Pair<GridElement<Boolean>, Integer>> queue = new LinkedList<>();
        queue.add(Pair.of(start, 0));

        while (!queue.isEmpty()) {
            var currentPair = queue.remove();
            var currentLocation = currentPair.getKey();
            if (currentLocation == goal) {
                return currentPair.getValue();
            }

            var left = currentLocation.getLeftNeighbour();
            if (left != null && left.getValue() && CACHE.add(left.getCoordinates())) {
                queue.add(Pair.of(left, currentPair.getValue() + 1));
            }
            var right = currentLocation.getRightNeighbour();
            if (right != null && right.getValue() && CACHE.add(right.getCoordinates())) {
                queue.add(Pair.of(right, currentPair.getValue() + 1));
            }
            var upper = currentLocation.getUpperNeighbour();
            if (upper != null && upper.getValue() && CACHE.add(upper.getCoordinates())) {
                queue.add(Pair.of(upper, currentPair.getValue() + 1));
            }
            var lower = currentLocation.getLowerNeighbour();
            if (lower != null && lower.getValue() && CACHE.add(lower.getCoordinates())) {
                queue.add(Pair.of(lower, currentPair.getValue() + 1));
            }
        }

        throw new IllegalStateException();
    }
}
