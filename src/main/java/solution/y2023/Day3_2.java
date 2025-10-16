package solution.y2023;

import utils.Point;
import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day3_2 extends GridSolution<Character> {
	public static void main(String[] args) {
		new Day3_2().solve();
	}

	@Override
	protected GridElement<Character> transformCell(char ch, int x, int y) {
		return new GridElement<>(ch, x, y);
	}

	@Override
	protected String computeSolution() {
		return stream()	.filter(element -> element.getValue() == '*' && hasTwoAdjacentNumbers(element))
						.mapToInt(this::extractGearNumber)
						.sum()
				+ "";
	}

	private boolean hasTwoAdjacentNumbers(GridElement<Character> gridElement) {
		var adjacentNumbers = gridElement	.getAllNeighbours()
											.stream()
											.filter(element -> Character.isDigit(element.getValue()))
											.map(GridElement::getCoordinates)
											.toList();
		var lines = adjacentNumbers.stream().map(Point::getY).distinct().count();
		if (lines > 1) {
			return true;
		}

		var edges = adjacentNumbers	.stream()
									.filter(element -> !adjacentNumbers.contains(element.getLeftNeighbour())
											&& !adjacentNumbers.contains(element.getRightNeighbour()))
									.count();
		return edges > 1;
	}

	private int extractGearNumber(GridElement<Character> gridElement) {
		var adjacentNumbers = gridElement	.getAllNeighbours()
											.stream()
											.filter(element -> Character.isDigit(element.getValue()))
											.toList();
		var lines = adjacentNumbers.stream().map(element -> element.getCoordinates().getY()).distinct().toList();
		if (lines.size() > 1) {
			var firstGear = extractPartNumber(
					adjacentNumbers	.stream()
									.filter(element -> element.getCoordinates().getY() == lines.getFirst())
									.findAny()
									.orElseThrow());
			var secondGear = extractPartNumber(
					adjacentNumbers	.stream()
									.filter(element -> element.getCoordinates().getY() == lines.getLast())
									.findAny()
									.orElseThrow());
			return firstGear * secondGear;
		}

		var edges = adjacentNumbers	.stream()
									.filter(element -> !adjacentNumbers.contains(element.getLeftNeighbour())
											&& !adjacentNumbers.contains(element.getRightNeighbour()))
									.toList();
		return extractPartNumber(edges.getFirst()) * extractPartNumber(edges.getLast());
	}

	private int extractPartNumber(GridElement<Character> gridElement) {
		var currentElement = gridElement;
		while (currentElement.getLeftNeighbour() != null
				&& Character.isDigit(currentElement.getLeftNeighbour().getValue())) {
			currentElement = currentElement.getLeftNeighbour();
		}

		var partNumber = new StringBuilder();
		while (currentElement != null && Character.isDigit(currentElement.getValue())) {
			partNumber.append(currentElement.getValue());
			currentElement = currentElement.getRightNeighbour();
		}
		return Integer.parseInt(partNumber.toString());
	}
}