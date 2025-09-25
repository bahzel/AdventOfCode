package solution.y2019;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day20_2 extends GridSolution<Character> {
	public static void main(String[] args) {
		new Day20_2().solve();
	}

	@Override
	protected GridElement<Character> transformCell(char ch, int x, int y) {
		return new GridElement<>(ch, x, y);
	}

	@Override
	protected String computeSolution() {
		var start = getWormholesWithSign(Pair.of('A', 'A')).getFirst();
		var goal = getWormholesWithSign(Pair.of('Z', 'Z')).getFirst();
		getWormholes().stream()
					  .filter(wormhole -> wormhole != start)
					  .filter(wormhole -> wormhole != goal)
					  .forEach(wormhole -> wormhole.getBorderingNeighbours()
												   .add(getWormholesWithSign(getSign(wormhole)).stream()
																							   .filter(connectingWormhole ->
																									   connectingWormhole
																											   != wormhole)
																							   .findAny()
																							   .orElseThrow()));

		var cache = new HashSet<Pair<GridElement<Character>, Integer>>();
		Queue<Triple<GridElement<Character>, Integer, Integer>> queue = new LinkedList<>();
		queue.add(Triple.of(start, 0, 0));
		while (!queue.isEmpty()) {
			var currentState = queue.poll();
			if (currentState.getLeft().getValue() != '.' || !cache.add(
					Pair.of(currentState.getLeft(), currentState.getMiddle()))) {
				continue;
			}

			if (currentState.getLeft() == goal && currentState.getMiddle() == 0) {
				return currentState.getRight() + "";
			}

			currentState.getLeft().getBorderingNeighbours().forEach(neighbour -> {
				if (neighbour.getCoordinates().computeManhattanDistance(currentState.getLeft().getCoordinates()) == 1) {
					queue.add(Triple.of(neighbour, currentState.getMiddle(), currentState.getRight() + 1));
				} else {
					if (isOuterPortal(currentState.getLeft())) {
						if (currentState.getMiddle() > 0) {
							queue.add(Triple.of(neighbour, currentState.getMiddle() - 1, currentState.getRight() + 1));
						}
					} else {
						queue.add(Triple.of(neighbour, currentState.getMiddle() + 1, currentState.getRight() + 1));
					}
				}
			});
		}

		throw new IllegalStateException("No bordering neighbours found");
	}

	private boolean isOuterPortal(GridElement<Character> tile) {
		return tile.getCoordinates().getX() == 2 || tile.getCoordinates().getX() == grid.length - 3
				|| tile.getCoordinates().getY() == 2 || tile.getCoordinates().getY() == grid[0].length - 3;
	}

	private boolean hasNeighbourWithValue(GridElement<Character> tile, char value) {
		return tile.getBorderingNeighbours().stream().anyMatch(neighbour -> neighbour.getValue() == value);
	}

	private boolean hasSign(GridElement<Character> tile, char value1, char value2) {
		return tile.getBorderingNeighbours()
				   .stream()
				   .anyMatch(neighbour -> neighbour.getValue() == value1 && hasNeighbourWithValue(neighbour, value2));
	}

	private boolean hasSign(GridElement<Character> tile) {
		return tile.getBorderingNeighbours().stream().anyMatch(this::isWarpSign);
	}

	private Pair<Character, Character> getSign(GridElement<Character> tile) {
		var firstSign = tile.getBorderingNeighbours().stream().filter(this::isWarpSign).findAny().orElseThrow();
		var secondSign = firstSign.getBorderingNeighbours().stream().filter(this::isWarpSign).findAny().orElseThrow();
		return Pair.of(firstSign.getValue(), secondSign.getValue());
	}

	private List<GridElement<Character>> getWormholesWithSign(Pair<Character, Character> sign) {
		return stream().filter(this::isWalkable)
					   .filter(wormhole -> hasSign(wormhole, sign.getLeft(), sign.getRight()) || hasSign(wormhole,
							   sign.getRight(), sign.getLeft()))
					   .toList();
	}

	private List<GridElement<Character>> getWormholes() {
		return stream().filter(this::isWalkable).filter(this::hasSign).toList();
	}

	private boolean isWalkable(GridElement<Character> tile) {
		return tile.getValue() == '.';
	}

	private boolean isWarpSign(GridElement<Character> tile) {
		return Character.isAlphabetic(tile.getValue());
	}
}
