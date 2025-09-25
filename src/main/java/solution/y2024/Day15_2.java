package solution.y2024;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import utils.Direction;
import utils.StringTransformer;
import utils.soution.GridElement;
import utils.soution.GridInstructionSolution;

public class Day15_2 extends GridInstructionSolution<BigWarehouseTile, Direction> {
	private GridElement<BigWarehouseTile> robotLocation;

	public static void main(String[] args) {
		new Day15_2().solve();
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		return instructions	.stream()
							.map(instruction -> instruction	.replace("#", "##")
															.replace(".", "..")
															.replace("O", "[]")
															.replace("@", "@."))
							.toList();
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
		var affectedTiles = getAffectedTiles(robotLocation, direction);
		if (canMove(affectedTiles, direction)) {
			move(affectedTiles, direction);
		}
	}

	private List<GridElement<BigWarehouseTile>> getAffectedTiles(GridElement<BigWarehouseTile> tile,
			Direction direction) {
		var affectedTiles = new ArrayList<GridElement<BigWarehouseTile>>();

		Queue<GridElement<BigWarehouseTile>> queue = new LinkedList<>();
		switch (tile.getNeighbour(direction).getValue()) {
		case Left_Box:
			queue.add(tile.getNeighbour(direction));
			if (direction == Direction.UP || direction == Direction.DOWN) {
				queue.add(tile.getNeighbour(direction).getRightNeighbour());
			}
			break;
		case Right_Box:
			queue.add(tile.getNeighbour(direction));
			if (direction == Direction.UP || direction == Direction.DOWN) {
				queue.add(tile.getNeighbour(direction).getLeftNeighbour());
			}
		}

		while (!queue.isEmpty()) {
			var element = queue.remove();
			affectedTiles.add(element);
			switch (element.getNeighbour(direction).getValue()) {
			case Left_Box:
				queue.add(element.getNeighbour(direction));
				if (direction == Direction.UP || direction == Direction.DOWN) {
					queue.add(element.getNeighbour(direction).getRightNeighbour());
				}
				break;
			case Right_Box:
				queue.add(element.getNeighbour(direction));
				if (direction == Direction.UP || direction == Direction.DOWN) {
					queue.add(element.getNeighbour(direction).getLeftNeighbour());
				}
			}
		}

		return affectedTiles.stream().distinct().toList();
	}

	private boolean canMove(List<GridElement<BigWarehouseTile>> affectedTiles, Direction direction) {
		return robotLocation.getNeighbour(direction).getValue() != BigWarehouseTile.Wall
				&& affectedTiles.stream()
								.noneMatch(tile -> tile.getNeighbour(direction).getValue() == BigWarehouseTile.Wall);
	}

	private void move(List<GridElement<BigWarehouseTile>> affectedTiles, Direction direction) {
		affectedTiles.reversed().forEach(tile -> {
			tile.getNeighbour(direction).setValue(tile.getValue());
			tile.setValue(BigWarehouseTile.Empty);
		});

		robotLocation = robotLocation.getNeighbour(direction);
	}

	private void print() {
		for (var y = 0; y < grid[0].length; y++) {
			for (GridElement<BigWarehouseTile>[] gridElements : grid) {
				switch (gridElements[y].getValue()) {
				case Empty:
					if (gridElements[y] == robotLocation) {
						print('@');
					} else {
						print('.');
					}
					break;
				case Left_Box:
					print('[');
					break;
				case Right_Box:
					print(']');
					break;
				case Wall:
					print('#');
					break;
				}
			}
			println();
		}
	}

	@Override
	protected GridElement<BigWarehouseTile> transformCell(char ch, int x, int y) {
		return switch (ch) {
		case '#' -> new GridElement<>(BigWarehouseTile.Wall, x, y);
		case '.' -> new GridElement<>(BigWarehouseTile.Empty, x, y);
		case '[' -> new GridElement<>(BigWarehouseTile.Left_Box, x, y);
		case ']' -> new GridElement<>(BigWarehouseTile.Right_Box, x, y);
		case '@' -> {
			robotLocation = new GridElement<>(BigWarehouseTile.Empty, x, y);
			yield robotLocation;
		}
		default -> throw new IllegalArgumentException("Invalid character: " + ch);
		};
	}

	@Override
	protected String computeSolution() {
		print();
		return stream()	.filter(tile -> tile.getValue() == BigWarehouseTile.Left_Box)
						.mapToLong(tile -> 100L * tile.getCoordinates().getY() + tile.getCoordinates().getX())
						.sum()
				+ "";
	}
}

enum BigWarehouseTile {
	Left_Box, Right_Box, Wall, Empty
}
