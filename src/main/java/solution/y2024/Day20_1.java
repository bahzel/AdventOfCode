package solution.y2024;

import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day20_1 extends GridSolution<Integer> {
	private GridElement<Integer> start;
	private GridElement<Integer> goal;

	public static void main(String[] args) {
		new Day20_1().solve();
	}

	@Override
	protected GridElement<Integer> transformCell(char ch, int x, int y) {
		return switch (ch) {
			case '#' -> new GridElement<>(-1, x, y);
			case 'S' -> {
				start = new GridElement<>(0, x, y);
				yield start;
			}
			case 'E' -> {
				goal = new GridElement<>(0, x, y);
				yield goal;
			}
			case '.' -> new GridElement<>(0, x, y);
			default -> throw new IllegalArgumentException("Invalid character: " + ch);
		};
	}

	@Override
	protected String computeSolution() {
		computeCost();
		var usefulCheats = 0;

		var shortCuts = stream().filter(tile -> tile.getValue() == -1)
								.filter(tile -> tile.getBorderingNeighbours()
													.stream()
													.filter(neighbour -> neighbour.getValue() != -1)
													.count() > 1)
								.toList();
		for (var shortCut : shortCuts) {
			var connectingTiles = shortCut.getBorderingNeighbours()
										  .stream()
										  .filter(neighbour -> neighbour.getValue() != -1)
										  .map(GridElement::getValue)
										  .sorted()
										  .toList();
			if (connectingTiles.getLast() - connectingTiles.getFirst() > 101) {
				usefulCheats++;
			}
		}

		return usefulCheats + "";
	}

	private void computeCost() {
		GridElement<Integer> lastElement = null;
		var currentElement = start;
		for (int i = 1; currentElement != goal; i++) {
			var finalLastElement = lastElement;
			var nextElement = currentElement.getBorderingNeighbours()
											.stream()
											.filter(neighbour -> neighbour != finalLastElement
													&& neighbour.getValue() != -1)
											.findFirst()
											.orElseThrow();
			lastElement = currentElement;
			currentElement = nextElement;
			currentElement.setValue(i);
		}
	}
}
