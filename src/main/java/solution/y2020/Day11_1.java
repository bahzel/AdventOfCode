package solution.y2020;

import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day11_1 extends GridSolution<SeatState> {
	public static void main(String[] args) {
		new Day11_1().solve();
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
		computeNextRound();
		while (stream().anyMatch(tile -> tile.getValue() != tile.getNextValue())) {
			stream().forEach(GridElement::assignNextValue);
			computeNextRound();
		}
		return stream().filter(tile -> tile.getValue() == SeatState.Occupied).count() + "";
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
										.count() >= 4 ? SeatState.Free : SeatState.Occupied);
				break;
			}
		});
	}
}

enum SeatState {
	Floor, Free, Occupied
}
