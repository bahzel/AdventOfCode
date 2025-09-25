package solution.y2019;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day18_1 extends GridSolution<Character> {
	public static void main(String[] args) {
		new Day18_1().solve();
	}

	@Override
	protected GridElement<Character> transformCell(char ch, int x, int y) {
		return new GridElement<>(ch, x, y);
	}

	@Override
	protected String computeSolution() {
		var start = stream().filter(tile -> tile.getValue() == '@').findAny().orElseThrow();
		var keyCount = stream().filter(tile -> Character.isLowerCase(tile.getValue())).count();
		var cache = new HashSet<Pair<GridElement<Character>, Set<Character>>>();

		Queue<Triple<GridElement<Character>, Set<Character>, Integer>> queue = new LinkedList<>();
		queue.add(Triple.of(start, new HashSet<>(), 0));
		while (!queue.isEmpty()) {
			var currentState = queue.poll();
			if (currentState.getLeft().getValue() == '#' || !cache.add(
					Pair.of(currentState.getLeft(), currentState.getMiddle()))
					|| Character.isUpperCase(currentState.getLeft().getValue()) && !currentState.getMiddle()
																								.contains(
																										Character.toLowerCase(
																												currentState.getLeft()
																															.getValue()))) {
				continue;
			}

			if (Character.isLowerCase(currentState.getLeft().getValue())) {
				var keys = new HashSet<>(currentState.getMiddle());
				keys.add(currentState.getLeft().getValue());
				if (keys.size() == keyCount) {
					return currentState.getRight() + "";
				}

				currentState.getLeft()
							.getBorderingNeighbours()
							.forEach(neighbour -> queue.add(Triple.of(neighbour, keys, currentState.getRight() + 1)));
			} else {
				currentState.getLeft()
							.getBorderingNeighbours()
							.forEach(neighbour -> queue.add(
									Triple.of(neighbour, currentState.getMiddle(), currentState.getRight() + 1)));
			}
		}

		throw new IllegalStateException();
	}
}
