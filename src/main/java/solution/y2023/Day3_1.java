package solution.y2023;

import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day3_1 extends GridSolution<Character> {
	public static void main(String[] args) {
		new Day3_1().solve();
	}

	@Override
	protected GridElement<Character> transformCell(char ch, int x, int y) {
		return new GridElement<>(ch, x, y);
	}

	@Override
	protected String computeSolution() {
		return stream()	.filter(element -> isStartOfPartNumber(element) && hasAdjacentSymbol(element))
						.mapToInt(this::extractPartNumber)
						.sum()
				+ "";
	}

	private boolean isStartOfPartNumber(GridElement<Character> gridElement) {
		return Character.isDigit(gridElement.getValue()) && (gridElement.getLeftNeighbour() == null
				|| !Character.isDigit(gridElement.getLeftNeighbour().getValue()));
	}

	private boolean hasAdjacentSymbol(GridElement<Character> gridElement) {
		if (isSymbol(gridElement.getLeftNeighbour()) || isSymbol(gridElement.getUpperNeighbour())
				|| isSymbol(gridElement.getLowerNeighbour())
				|| (gridElement.getLeftNeighbour() != null
						&& (isSymbol(gridElement.getLeftNeighbour().getUpperNeighbour())
								|| isSymbol(gridElement.getLeftNeighbour().getLowerNeighbour())))) {
			return true;
		}

		var currentElement = gridElement;
		while (currentElement.getRightNeighbour() != null
				&& Character.isDigit(currentElement.getRightNeighbour().getValue())) {
			currentElement = currentElement.getRightNeighbour();
			if (isSymbol(currentElement.getUpperNeighbour()) || isSymbol(currentElement.getLowerNeighbour())) {
				return true;
			}
		}

		return isSymbol(currentElement.getRightNeighbour()) || currentElement.getRightNeighbour() != null
				&& (isSymbol(currentElement.getRightNeighbour().getUpperNeighbour())
						|| isSymbol(currentElement.getRightNeighbour().getLowerNeighbour()));
	}

	private boolean isSymbol(GridElement<Character> gridElement) {
		return gridElement != null && !Character.isDigit(gridElement.getValue()) && !(gridElement.getValue() == '.');
	}

	private int extractPartNumber(GridElement<Character> gridElement) {
		var partNumber = new StringBuilder();
		var currentElement = gridElement;
		while (currentElement != null && Character.isDigit(currentElement.getValue())) {
			partNumber.append(currentElement.getValue());
			currentElement = currentElement.getRightNeighbour();
		}
		return Integer.parseInt(partNumber.toString());
	}
}