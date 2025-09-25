package solution.y2024;

import java.util.Collection;
import java.util.List;

import utils.Direction;
import utils.StringTransformer;
import utils.soution.GridElement;
import utils.soution.GridInstructionSolution;

public class Day15_1 extends GridInstructionSolution<WarehouseTile, Direction> {
	private GridElement<WarehouseTile> robotLocation;

	public static void main(String[] args) {
		new Day15_1().solve();
	}

	@Override
	protected List<String> getInstructionStrings(List<String> instructions) {
		return instructions.stream().map(StringTransformer::splitString).flatMap(Collection::stream).toList();
	}

	@Override
	protected Direction transformInstruction(String instruction) {
		return switch (instruction) {
		case "^" -> Direction.UP;
		case "<" -> Direction.LEFT;
		case ">" -> Direction.RIGHT;
		case "v" -> Direction.DOWN;
		default -> throw new IllegalArgumentException("Invalid instruction: " + instruction);
		};
	}

	@Override
	protected void performInstruction(Direction direction) {
		if (canMove(robotLocation, direction)) {
			move(robotLocation, direction);
		}
	}

	private boolean canMove(GridElement<WarehouseTile> tile, Direction direction) {
		return switch (tile.getNeighbour(direction).getValue()) {
		case Box -> canMove(tile.getNeighbour(direction), direction);
		case Wall -> false;
		case Empty -> true;
		};
	}

	private void move(GridElement<WarehouseTile> tile, Direction direction) {
		if (tile.getValue() == WarehouseTile.Empty) {
			robotLocation = tile.getNeighbour(direction);
		}

		if (tile.getNeighbour(direction).getValue() == WarehouseTile.Box) {
			move(tile.getNeighbour(direction), direction);
		}
		tile.getNeighbour(direction).setValue(tile.getValue());
	}

	@Override
	protected GridElement<WarehouseTile> transformCell(char ch, int x, int y) {
		return switch (ch) {
		case '#' -> new GridElement<>(WarehouseTile.Wall, x, y);
		case '.' -> new GridElement<>(WarehouseTile.Empty, x, y);
		case 'O' -> new GridElement<>(WarehouseTile.Box, x, y);
		case '@' -> {
			robotLocation = new GridElement<>(WarehouseTile.Empty, x, y);
			yield robotLocation;
		}
		default -> throw new IllegalArgumentException("Invalid character: " + ch);
		};
	}

	@Override
	protected String computeSolution() {
		return stream()	.filter(tile -> tile.getValue() == WarehouseTile.Box)
						.mapToLong(tile -> 100L * tile.getCoordinates().getY() + tile.getCoordinates().getX())
						.sum()
				+ "";
	}
}

enum WarehouseTile {
	Box, Wall, Empty
}
