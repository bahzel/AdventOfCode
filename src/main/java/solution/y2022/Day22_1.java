package solution.y2022;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import utils.Direction;
import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day22_1 extends GridSolution<Boolean> {
	private String instruction;

	public static void main(String[] args) {
		new Day22_1().solve();
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
		setNeighbours();
		var currentPosition = stream()	.filter(element -> element != null && element.getCoordinates().getY() == 0)
										.min(Comparator.comparingInt(element -> element.getCoordinates().getX()))
										.orElseThrow();
		var currentDirection = Direction.RIGHT;

		for (var move : getMoves()) {
			for (var i = 0; currentPosition.getNeighbour(currentDirection).getValue() == true
					&& i < move.getValue(); i++) {
				currentPosition = currentPosition.getNeighbour(currentDirection);
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

	private void setNeighbours() {
		stream().forEach(element -> {
			if (element == null) {
				return;
			}
			if (element.getLeftNeighbour() == null) {
				var leftNeighbour = element;
				while (leftNeighbour.getRightNeighbour() != null) {
					leftNeighbour = leftNeighbour.getRightNeighbour();
				}
				element.setLeftNeighbour(leftNeighbour);
				leftNeighbour.setRightNeighbour(element);
			}
			if (element.getUpperNeighbour() == null) {
				var upperNeighbour = element;
				while (upperNeighbour.getLowerNeighbour() != null) {
					upperNeighbour = upperNeighbour.getLowerNeighbour();
				}
				element.setUpperNeighbour(upperNeighbour);
				upperNeighbour.setLowerNeighbour(element);
			}
		});
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