package solution.y2023;

import java.util.Collection;

import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day21_1 extends GridSolution<GardenTile> {
	public static void main(String[] args) {
		new Day21_1().solve();
	}

	@Override
	protected GridElement<GardenTile> transformCell(char ch, int x, int y) {
		return switch (ch) {
		case '.' -> new GridElement<>(GardenTile.Garden, x, y);
		case '#' -> new GridElement<>(GardenTile.Rock, x, y);
		case 'S' -> new GridElement<>(GardenTile.Visited, x, y);
		default -> throw new IllegalArgumentException("Invalid character");
		};
	}

	@Override
	protected String computeSolution() {
		printGrid();
		for (var i = 0; i < 64; i = i + 2) {
			step();
			printGrid();
		}
		return stream()	.filter(tile -> tile.getValue() == GardenTile.Visited || tile.getValue() == GardenTile.Handled)
						.count()
				+ "";
	}

	private void step() {
		var toVisit = stream().filter(tile -> tile.getValue() == GardenTile.Visited).toList();
		toVisit.forEach(this::step);
	}

	private void step(GridElement<GardenTile> tile) {
		tile.getBorderingNeighbours()
			.stream()
			.filter(neighbour -> neighbour.getValue() == GardenTile.Garden)
			.map(GridElement::getBorderingNeighbours)
			.flatMap(Collection::stream)
			.filter(neighbour -> neighbour.getValue() == GardenTile.Garden)
			.forEach(neighbour -> neighbour.setValue(GardenTile.Visited));
		tile.setValue(GardenTile.Handled);
	}

	@Override
	protected String printValue(GardenTile value) {
		return switch (value) {
		case GardenTile.Visited, GardenTile.Handled -> "O";
		case GardenTile.Garden -> ".";
		case GardenTile.Rock -> "#";
		};
	}
}

enum GardenTile {
	Rock, Garden, Visited, Handled
}