package solution.y2020;

import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day11_2 extends GridSolution<SeatState> {
	public static void main(String[] args) {
		new Day11_2().solve();
	}

	@Override
	protected GridElement<SeatState> transformCell(char ch, int x, int y) {
		return switch (ch) {
		case '.' -> new GridElement<>(SeatState.Floor, x, y);
		case 'L' -> new GridElement<>(SeatState.Free, x, y);
		default -> throw new IllegalArgumentException("Invalid character: " + ch);
		};
	}

	@Override
	protected String computeSolution() {
		setNeighbours();
		computeNextRound();
		while (stream().anyMatch(tile -> tile.getValue() != tile.getNextValue())) {
			stream().forEach(GridElement::assignNextValue);
			computeNextRound();
		}
		return stream().filter(tile -> tile.getValue() == SeatState.Occupied).count() + "";
	}

	private void setNeighbours() {
		stream().forEach(tile -> {
			addNeighbour(tile, 0, -1);
			addNeighbour(tile, 1, -1);
			addNeighbour(tile, 1, 0);
			addNeighbour(tile, 1, 1);
			addNeighbour(tile, 0, 1);
			addNeighbour(tile, -1, 1);
			addNeighbour(tile, -1, 0);
			addNeighbour(tile, -1, -1);
		});
	}

	private void addNeighbour(GridElement<SeatState> tile, int deltaX, int deltaY) {
		for (int x = tile.getCoordinates().getX() + deltaX, y = tile.getCoordinates().getY() + deltaY; x >= 0
				&& x < grid.length && y >= 0 && y < grid[x].length; x += deltaX, y += deltaY) {
			if (grid[x][y].getValue() == SeatState.Free) {
				if (!tile.getAllNeighbours().contains(grid[x][y])) {
					tile.getAllNeighbours().add(grid[x][y]);
				}
				return;
			}

		}
	}

	private void computeNextRound() {
		stream().forEach(tile -> {
			switch (tile.getValue()) {
			case SeatState.Floor:
				tile.setNextValue(SeatState.Floor);
				break;
			case SeatState.Free:
				tile.setNextValue(
						tile.getAllNeighbours()
							.stream()
							.anyMatch(neighbour -> neighbour.getValue() == SeatState.Occupied) ? SeatState.Free
									: SeatState.Occupied);
				break;
			case SeatState.Occupied:
				tile.setNextValue(tile	.getAllNeighbours()
										.stream()
										.filter(neighbour -> neighbour.getValue() == SeatState.Occupied)
										.count() >= 5 ? SeatState.Free : SeatState.Occupied);
				break;
			}
		});
	}
}
