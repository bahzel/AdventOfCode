package solution.y2021;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day9_2 extends GridSolution<Integer> {
	public static void main(String[] args) {
		new Day9_2().solve();
	}

	@Override
	protected GridElement<Integer> transformCell(char ch, int x, int y) {
		return new GridElement<>(Integer.parseInt(String.valueOf(ch)), x, y);
	}

	@Override
	protected String computeSolution() {
		stream().forEach(tile -> tile.setNextValue(0));
		stream().filter(tile -> tile.getValue() < 9).forEach(this::flowToBasin);
		var basins = stream()	.filter(tile -> tile.getNextValue() > 0)
								.map(GridElement::getNextValue)
								.sorted(Comparator.reverseOrder())
								.toList();
		return basins.getFirst() * basins.get(1) * basins.get(2) + "";
	}

	private void flowToBasin(GridElement<Integer> tile) {
		var endpoints = new HashSet<GridElement<Integer>>();
		Queue<GridElement<Integer>> queue = new LinkedList<>();
		queue.add(tile);
		while (!queue.isEmpty()) {
			var element = queue.poll();
			var lowerNeighbours = element	.getBorderingNeighbours()
											.stream()
											.filter(neighbour -> neighbour.getValue() < element.getValue())
											.toList();
			if (lowerNeighbours.isEmpty()) {
				endpoints.add(element);
			} else {
				queue.addAll(lowerNeighbours);
			}
		}
		if (endpoints.size() == 1) {
			var basin = endpoints.iterator().next();
			basin.setNextValue(basin.getNextValue() + 1);
		}
	}
}
