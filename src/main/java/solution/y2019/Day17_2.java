package solution.y2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import lombok.SneakyThrows;
import utils.Direction;
import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day17_2 extends GridSolution<Boolean> {
	private List<Long> register;
	private GridElement<Boolean> startingPosition;
	private Direction startingDirection;

	public static void main(String[] args) {
		new Day17_2().solve();
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		register = new ArrayList<>(
				Arrays.stream(input.getFirst().split(",")).mapToLong(Long::parseLong).boxed().toList());
		var intCodeInterpreter = new IntCodeInterpreter().withRegister(new ArrayList<>(register));
		intCodeInterpreter.performComputation();

		var output = intCodeInterpreter.toString();
		print(output);
		return Arrays.asList(output.split("\n"));
	}

	@Override
	protected GridElement<Boolean> transformCell(char ch, int x, int y) {
		return switch (ch) {
			case '#' -> new GridElement<>(true, x, y);
			case '^' -> {
				startingPosition = new GridElement<>(true, x, y);
				startingDirection = Direction.UP;
				yield startingPosition;
			}
			case 'v' -> {
				startingPosition = new GridElement<>(true, x, y);
				startingDirection = Direction.DOWN;
				yield startingPosition;
			}
			case '<' -> {
				startingPosition = new GridElement<>(true, x, y);
				startingDirection = Direction.LEFT;
				yield startingPosition;
			}
			case '>' -> {
				startingPosition = new GridElement<>(true, x, y);
				startingDirection = Direction.RIGHT;
				yield startingPosition;
			}
			case '.' -> new GridElement<>(false, x, y);
			default -> throw new IllegalArgumentException("Invalid character: " + ch);
		};
	}

	@Override
	@SneakyThrows
	protected String computeSolution() {
		var directions = computeDirections();
		var functions = getFunctions(directions);

		register.set(0, 2L);
		var intCodeInterpreter = new IntCodeInterpreter().withRegister(register);
		for (var function : functions) {
			intCodeInterpreter.inputString(function);
		}
		intCodeInterpreter.inputString("n");

		intCodeInterpreter.performComputation();
		return new ArrayList<>(intCodeInterpreter.getOutput()).getLast() + "";
	}

	private List<String> getFunctions(String directions) {
		var aFunctions = new HashSet<String>();
		for (int i = 2; i <= 20; i++) {
			for (int j = 0; j < directions.length() - i; j++) {
				if ((j == 0 || directions.charAt(j - 1) == ',') && !Character.isDigit(directions.charAt(j)) && (
						j == directions.length() - i - 1 || directions.charAt(j + i) == ',') && Character.isDigit(
						directions.charAt(j + i - 1))) {
					aFunctions.add(directions.substring(j, j + i));
				}
			}
		}

		for (var aFunction : aFunctions) {
			var restA = directions.replaceAll(aFunction, "A");
			var bFunctions = new HashSet<String>();
			for (int i = 2; i <= 20; i++) {
				for (int j = 0; j < restA.length() - i; j++) {
					if ((j == 0 || restA.charAt(j - 1) == ',') && !Character.isDigit(restA.charAt(j)) && (
							j == restA.length() - i - 1 || restA.charAt(j + i) == ',') && Character.isDigit(
							restA.charAt(j + i - 1))) {
						bFunctions.add(restA.substring(j, j + i));
					}
				}
			}

			for (var bFunction : bFunctions) {
				var restB = restA.replaceAll(bFunction, "B");
				for (int i = 2; i <= 20; i++) {
					for (int j = 0; j < restB.length() - i; j++) {
						if ((j == 0 || restB.charAt(j - 1) == ',') && !Character.isDigit(restB.charAt(j)) && (
								j == restB.length() - i - 1 || restB.charAt(j + i) == ',') && Character.isDigit(
								restB.charAt(j + i - 1))) {
							var cFunction = restB.substring(j, j + i);
							if (restB.replaceAll(cFunction, "C").length() <= 20) {
								return List.of(restB.replaceAll(cFunction, "C"), aFunction, bFunction, cFunction);
							}
						}
					}
				}
			}
		}

		throw new IllegalStateException();
	}

	private String computeDirections() {
		var instruction = new StringBuilder();
		var currentPosition = startingPosition;
		var currentDirection = startingDirection;

		while (currentPosition.getNeighbour(currentDirection.turn(Direction.LEFT)).getValue()
				|| currentPosition.getNeighbour(currentDirection.turn(Direction.RIGHT)).getValue()) {
			if (currentPosition.getNeighbour(currentDirection.turn(Direction.LEFT)).getValue()) {
				currentDirection = currentDirection.turn(Direction.LEFT);
				instruction.append('L');
			} else {
				currentDirection = currentDirection.turn(Direction.RIGHT);
				instruction.append('R');
			}

			var amountOfSteps = 0;
			while (currentPosition.getNeighbour(currentDirection) != null && currentPosition.getNeighbour(
					currentDirection).getValue()) {
				currentPosition = currentPosition.getNeighbour(currentDirection);
				amountOfSteps++;
			}
			instruction.append(',');
			instruction.append(amountOfSteps);
			instruction.append(',');
		}
		instruction.deleteCharAt(instruction.length() - 1);
		return instruction.toString();
	}
}
