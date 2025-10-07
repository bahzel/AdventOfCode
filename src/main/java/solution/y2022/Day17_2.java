package solution.y2022;

import java.util.ArrayList;
import java.util.List;

import utils.Direction;
import utils.Point;
import utils.StringTransformer;
import utils.soution.MapSolution;

public class Day17_2 extends MapSolution<List<Direction>> {
	public static void main(String[] args) {
		new Day17_2().solve();
	}

	@Override
	protected List<Direction> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		return StringTransformer.splitString(instructions.getFirst());
	}

	@Override
	protected void transformInstruction(String instruction, List<Direction> directions) {
		switch (instruction) {
		case "<" -> directions.add(Direction.LEFT);
		case ">" -> directions.add(Direction.RIGHT);
		default -> throw new IllegalArgumentException("Unknown direction: " + instruction);
		}
	}

	@Override
	protected String computeSolution(List<Direction> directions) {
		var tiles = initializeTiles();
		var grid = new ArrayList<boolean[]>();
		var height = 0;

		StringBuilder changes = new StringBuilder();
		var directionIndex = 0;
		var rounds = 0;
		for (; rounds < 10000; rounds++) {
			var currentTile = tiles.get(rounds % tiles.size());
			addLines(grid, currentTile, height);
			var currentPosition = new Point(2, height + 3);

			while (true) {
				currentPosition = move(grid, currentTile, currentPosition,
						directions.get(directionIndex % directions.size()));
				directionIndex++;
				var nextPosition = move(grid, currentTile, currentPosition, Direction.DOWN);
				if (nextPosition == currentPosition) {
					persistTile(grid, currentTile, currentPosition);
					var newHeight = Math.max(height, currentPosition.getY() + currentTile.getHeight());
					changes.append(newHeight - height);
					height = newHeight;
					break;
				} else {
					currentPosition = nextPosition;
				}
			}
		}

		for (var i = 1;; i++) {
			var period = changes.substring(changes.length() - 1 - i, changes.length() - 1);
			if (period.equals(changes.substring(changes.length() - 1 - i - i, changes.length() - 1 - i))) {
				for (; (1000000000000L - rounds) % period.length() != 0; rounds++) {
					var currentTile = tiles.get(rounds % tiles.size());
					addLines(grid, currentTile, height);
					var currentPosition = new Point(2, height + 3);

					while (true) {
						currentPosition = move(grid, currentTile, currentPosition,
								directions.get(directionIndex % directions.size()));
						directionIndex++;
						var nextPosition = move(grid, currentTile, currentPosition, Direction.DOWN);
						if (nextPosition == currentPosition) {
							persistTile(grid, currentTile, currentPosition);
							height = Math.max(height, currentPosition.getY() + currentTile.getHeight());
							break;
						} else {
							currentPosition = nextPosition;
						}
					}
				}
				var amountOfPeriods = (1000000000000L - rounds) / period.length();
				var periodGrowth = StringTransformer.splitString(period).stream().mapToInt(Integer::parseInt).sum();
				return (height + amountOfPeriods * periodGrowth) + "";
			}
		}
	}

	private List<Tile> initializeTiles() {
		List<Tile> tiles = new ArrayList<>();
		tiles.add(new Tile(List.of(new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(3, 0))));
		tiles.add(
				new Tile(List.of(new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2))));
		tiles.add(
				new Tile(List.of(new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(2, 1), new Point(2, 2))));
		tiles.add(new Tile(List.of(new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(0, 3))));
		tiles.add(new Tile(List.of(new Point(0, 0), new Point(1, 0), new Point(0, 1), new Point(1, 1))));
		return tiles;
	}

	private void addLines(List<boolean[]> grid, Tile tile, int towerHeight) {
		while (grid.size() < towerHeight + 3 + tile.getHeight()) {
			grid.add(new boolean[7]);
		}
	}

	private Point move(List<boolean[]> grid, Tile tile, Point position, Direction direction) {
		var newPosition = switch (direction) {
		case LEFT -> position.getLeftNeighbour();
		case RIGHT -> position.getRightNeighbour();
		case DOWN -> position.getUpperNeighbour();
		default -> throw new IllegalArgumentException("Unknown direction: " + direction);
		};

		for (var square : tile.getSquares()) {
			var newX = newPosition.getX() + square.getX();
			var newY = newPosition.getY() + square.getY();
			if (newX < 0 || newX >= 7 || newY < 0 || grid.get(newY)[newX]) {
				return position;
			}
		}
		return newPosition;
	}

	private void persistTile(List<boolean[]> grid, Tile tile, Point position) {
		for (var square : tile.getSquares()) {
			grid.get(position.getY() + square.getY())[position.getX() + square.getX()] = true;
		}
	}
}