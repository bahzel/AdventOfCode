package solution.y2022;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import utils.Direction;
import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day22_2 extends GridSolution<Boolean> {
	private String instruction;

	public static void main(String[] args) {
		new Day22_2().solve();
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		instruction = instructions.getLast();
		return instructions.subList(0, instructions.size() - 2);
	}

	@Override
	protected GridElement<Boolean> transformCell(char ch, int x, int y) {
		switch (ch) {
		case '.' -> {
			return new GridElement<>(true, x, y);
		}
		case '#' -> {
			return new GridElement<>(false, x, y);
		}
		case ' ' -> {
			return null;
		}
		default -> throw new IllegalArgumentException("Invalid character");
		}
	}

	@Override
	protected String computeSolution() {
		var neighbourMap = setNeighbours();
		var currentPosition = stream()	.filter(element -> element != null && element.getCoordinates().getY() == 0)
										.min(Comparator.comparingInt(element -> element.getCoordinates().getX()))
										.orElseThrow();
		var currentDirection = Direction.RIGHT;

		for (var move : getMoves()) {
			for (var i = 0; currentPosition.getNeighbour(currentDirection).getValue() == true
					&& i < move.getValue(); i++) {
				var neighbour = currentPosition.getNeighbour(currentDirection);
				var nextDirection = neighbourMap.get(
						Pair.of(currentPosition, currentPosition.getNeighbour(currentDirection)));
				if (nextDirection != null) {
					currentDirection = nextDirection;
				}
				currentPosition = neighbour;
			}
			currentDirection = currentDirection.turn(move.getLeft());
		}

		var score = 1000 * (currentPosition.getCoordinates().getY() + 1)
				+ 4 * (currentPosition.getCoordinates().getX() + 1);
		switch (currentDirection) {
		case DOWN -> score += 1;
		case LEFT -> score += 2;
		case UP -> score += 3;
		}
		return score + "";
	}

	private Map<Pair<GridElement<Boolean>, GridElement<Boolean>>, Direction> setNeighbours() {
		var neighbourMap = new HashMap<Pair<GridElement<Boolean>, GridElement<Boolean>>, Direction>();
		var x1 = 0;
		var x2 = stream()	.filter(element -> element != null && element.getCoordinates().getY() == 0)
							.mapToInt(element -> element.getCoordinates().getX())
							.min()
							.orElseThrow()
				- 1;
		var x3 = x2 + 1;
		var x6 = grid.length - 1;

		var y1 = 0;
		var y2 = stream()	.filter(element -> element != null && element.getCoordinates().getX() == grid.length - 1)
							.mapToInt(element -> element.getCoordinates().getY())
							.max()
							.orElseThrow();
		var y3 = y2 + 1;
		var y4 = stream()	.filter(element -> element != null && element.getCoordinates().getX() == 0)
							.mapToInt(element -> element.getCoordinates().getY())
							.min()
							.orElseThrow()
				- 1;
		var y5 = y4 + 1;
		var y6 = stream()	.filter(element -> element != null && element.getCoordinates().getX() == x3)
							.mapToInt(element -> element.getCoordinates().getY())
							.max()
							.orElseThrow();
		var y7 = y6 + 1;
		var y8 = grid[0].length - 1;

		var x4 = stream()	.filter(element -> element != null && element.getCoordinates().getY() == y3)
							.mapToInt(element -> element.getCoordinates().getX())
							.max()
							.orElseThrow();
		var x5 = x4 + 1;

		for (var x = x3; x <= x4; x++) {
			var tile = grid[x][y1];
			var neighbour = grid[x1][y7 + x - x3];
			tile.setUpperNeighbour(neighbour);
			neighbour.setLeftNeighbour(tile);
			neighbourMap.put(Pair.of(tile, neighbour), Direction.RIGHT);
			neighbourMap.put(Pair.of(neighbour, tile), Direction.DOWN);
		}
		for (var y = y1; y <= y2; y++) {
			var tile = grid[x3][y];
			var neighbour = grid[x1][y6 - y + y1];
			tile.setLeftNeighbour(neighbour);
			neighbour.setLeftNeighbour(tile);
			neighbourMap.put(Pair.of(tile, neighbour), Direction.RIGHT);
			neighbourMap.put(Pair.of(neighbour, tile), Direction.RIGHT);
		}
		for (var x = x5; x <= x6; x++) {
			var tile = grid[x][y1];
			var neighbour = grid[x1 + x - x5][y8];
			tile.setUpperNeighbour(neighbour);
			neighbour.setLowerNeighbour(tile);
		}
		for (var y = y1; y <= y2; y++) {
			var tile = grid[x6][y];
			var neighbour = grid[x4][y6 - y + y1];
			tile.setRightNeighbour(neighbour);
			neighbour.setRightNeighbour(tile);
			neighbourMap.put(Pair.of(tile, neighbour), Direction.LEFT);
			neighbourMap.put(Pair.of(neighbour, tile), Direction.LEFT);
		}
		for (var x = x5; x <= x6; x++) {
			var tile = grid[x][y2];
			var neighbour = grid[x4][y3 + x - x5];
			tile.setLowerNeighbour(neighbour);
			neighbour.setRightNeighbour(tile);
			neighbourMap.put(Pair.of(tile, neighbour), Direction.LEFT);
			neighbourMap.put(Pair.of(neighbour, tile), Direction.UP);
		}
		for (var y = y3; y <= y4; y++) {
			var tile = grid[x3][y];
			var neighbour = grid[x1 + y - y3][y5];
			tile.setLeftNeighbour(neighbour);
			neighbour.setUpperNeighbour(tile);
			neighbourMap.put(Pair.of(tile, neighbour), Direction.DOWN);
			neighbourMap.put(Pair.of(neighbour, tile), Direction.RIGHT);
		}
		for (var x = x3; x <= x4; x++) {
			var tile = grid[x][y6];
			var neighbour = grid[x2][y7 + x - x3];
			tile.setLowerNeighbour(neighbour);
			neighbour.setRightNeighbour(tile);
			neighbourMap.put(Pair.of(tile, neighbour), Direction.LEFT);
			neighbourMap.put(Pair.of(neighbour, tile), Direction.UP);
		}

		return neighbourMap;
	}

	private List<Pair<Direction, Integer>> getMoves() {
		var moves = new ArrayList<Pair<Direction, Integer>>();
		StringBuilder moveBuffer = new StringBuilder();

		for (var i = 0; i < instruction.length(); i++) {
			var ch = instruction.charAt(i);
			if (ch == 'R') {
				moves.add(Pair.of(Direction.RIGHT, Integer.valueOf(moveBuffer.toString())));
				moveBuffer = new StringBuilder();
			} else if (ch == 'L') {
				moves.add(Pair.of(Direction.LEFT, Integer.valueOf(moveBuffer.toString())));
				moveBuffer = new StringBuilder();
			} else {
				moveBuffer.append(ch);
			}
		}
		moves.add(Pair.of(Direction.UP, Integer.valueOf(moveBuffer.toString())));

		return moves;
	}
}