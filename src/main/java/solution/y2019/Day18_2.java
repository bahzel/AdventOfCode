package solution.y2019;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day18_2 extends GridSolution<Character> {
	public static void main(String[] args) {
		new Day18_2().solve();
	}

	@Override
	protected GridElement<Character> transformCell(char ch, int x, int y) {
		return new GridElement<>(ch, x, y);
	}

	@Override
	protected String computeSolution() {
		var start = stream().filter(tile -> tile.getValue() == '@').findAny().orElseThrow();
		start.setValue('#');
		start.getLeftNeighbour().setValue('#');
		start.getRightNeighbour().setValue('#');
		start.getUpperNeighbour().setValue('#');
		start.getLowerNeighbour().setValue('#');

		var keyCount = stream().filter(tile -> Character.isLowerCase(tile.getValue())).count();
		var cache = new HashSet<Pair<GridElement<Character>, Set<Character>>>();

		Queue<CaveState> queue = new LinkedList<>();
		var starts = List.of(start.getLeftNeighbour().getUpperNeighbour(), start.getLeftNeighbour().getLowerNeighbour(),
				start.getRightNeighbour().getUpperNeighbour(), start.getRightNeighbour().getLowerNeighbour());
		queue.add(new CaveState(starts, 0, new HashSet<>(), 0));
		queue.add(new CaveState(starts, 1, new HashSet<>(), 0));
		queue.add(new CaveState(starts, 2, new HashSet<>(), 0));
		queue.add(new CaveState(starts, 3, new HashSet<>(), 0));
		while (!queue.isEmpty()) {
			var currentState = queue.poll();
			if (currentState.getActivePosition().getValue() == '#' || !cache.add(
					Pair.of(currentState.getActivePosition(), currentState.keys()))
					|| Character.isUpperCase(currentState.getActivePosition().getValue()) && !currentState.keys()
																										  .contains(
																												  Character.toLowerCase(
																														  currentState.getActivePosition()
																																	  .getValue()))) {
				continue;
			}

			if (Character.isLowerCase(currentState.getActivePosition().getValue())) {
				var keys = new HashSet<>(currentState.keys());
				keys.add(currentState.getActivePosition().getValue());
				if (keys.size() == keyCount) {
					return currentState.steps() + "";
				}

				for (int i = 0; i < 4; i++) {
					var index = i;
					currentState.positions().get(index).getBorderingNeighbours().forEach(neighbour -> {
						var positions = new ArrayList<>(currentState.positions());
						positions.set(index, neighbour);
						queue.add(new CaveState(positions, index, keys, currentState.steps() + 1));
					});
				}
			} else {
				currentState.getActivePosition().getBorderingNeighbours().forEach(neighbour -> {
					var positions = new ArrayList<>(currentState.positions());
					positions.set(currentState.activeIndex(), neighbour);
					queue.add(new CaveState(positions, currentState.activeIndex(), currentState.keys(),
							currentState.steps() + 1));
				});
			}
		}

		throw new IllegalStateException();
	}
}

record CaveState(List<GridElement<Character>> positions, int activeIndex, Set<Character> keys, Integer steps) {
	public GridElement<Character> getActivePosition() {
		return positions.get(activeIndex);
	}
}
