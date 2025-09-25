package solution.y2024;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import utils.Point;
import utils.StringTransformer;
import utils.soution.InstructionSolution;

public class Day21_1 extends InstructionSolution<List<String>, AtomicInteger> {
	private final NumericalKeypadRobot numericalKeypadRobot = new NumericalKeypadRobot();
	private final ArrowKeypadRobot arrowKeypadRobot1 = new ArrowKeypadRobot();
	private final ArrowKeypadRobot arrowKeypadRobot2 = new ArrowKeypadRobot();

	public static void main(String[] args) {
		new Day21_1().solve();
	}

	@Override
	protected AtomicInteger initializeValue() {
		return new AtomicInteger();
	}

	@Override
	protected List<String> transformInstruction(String instruction) {
		return StringTransformer.splitString(instruction);
	}

	@Override
	protected boolean performInstruction(List<String> characters, AtomicInteger atomicInteger) {
		var sum = 0;
		for (var character : characters) {
			sum += numericalKeypadRobot	.moveTo(character)
										.stream()
										.mapToInt(this::getMinimumFromRobot1)
										.min()
										.orElseThrow();
		}
		atomicInteger.addAndGet(sum * Integer.parseInt(String.join("", characters).substring(0, 3)));

		return false;
	}

	private int getMinimumFromRobot1(List<NumpadDirection> directions) {
		var sum = 0;
		for (var direction : directions) {
			sum += arrowKeypadRobot1.moveTo(direction)
									.stream()
									.mapToInt(this::getMinimumFromRobot2)
									.min()
									.orElseThrow();
		}
		return sum;
	}

	private int getMinimumFromRobot2(List<NumpadDirection> directions) {
		return directions.stream().mapToInt(arrowKeypadRobot2::moveToDistance).sum();
	}

	@Override
	protected String getSolution(AtomicInteger atomicInteger) {
		return atomicInteger.toString();
	}
}

class KeypadRobot<T> {
	private final Map<T, Point> keypad;
	private final Point emptyPanel;
	private Point currentPanel;

	public KeypadRobot(Map<T, Point> keypad, Point emptyPanel, T startingValue) {
		this.keypad = keypad;
		this.emptyPanel = emptyPanel;
		currentPanel = keypad.get(startingValue);
	}

	public List<List<NumpadDirection>> moveTo(T goal) {
		var goalPanel = keypad.get(goal);
		var directions = getDirections(currentPanel, goalPanel);
		directions.forEach(direction -> direction.add(NumpadDirection.A));
		currentPanel = goalPanel;
		return directions;
	}

	public int moveToDistance(T goal) {
		var goalPanel = keypad.get(goal);
		var distance = goalPanel.computeManhattanDistance(currentPanel) + 1;
		currentPanel = goalPanel;
		return distance;
	}

	private List<List<NumpadDirection>> getDirections(Point from, Point to) {
		var directions = new ArrayList<List<NumpadDirection>>();
		for (int x = to.getX(); x < from.getX(); x++) {
			var nextFrom = from.getLeftNeighbour();
			if (nextFrom.equals(emptyPanel)) {
				continue;
			}
			if (nextFrom.equals(to)) {
				directions.add(new ArrayList<>(List.of(NumpadDirection.LEFT)));
			} else {
				getDirections(nextFrom, to).forEach(nextDirections -> {
					nextDirections.addFirst(NumpadDirection.LEFT);
					directions.add(nextDirections);
				});
			}
		}
		for (int x = from.getX(); x < to.getX(); x++) {
			var nextFrom = from.getRightNeighbour();
			if (nextFrom.equals(emptyPanel)) {
				continue;
			}
			if (nextFrom.equals(to)) {
				directions.add(new ArrayList<>(List.of(NumpadDirection.RIGHT)));
			} else {
				getDirections(nextFrom, to).forEach(nextDirections -> {
					nextDirections.addFirst(NumpadDirection.RIGHT);
					directions.add(nextDirections);
				});
			}
		}
		for (int y = to.getY(); y < from.getY(); y++) {
			var nextFrom = from.getUpperNeighbour();
			if (nextFrom.equals(emptyPanel)) {
				continue;
			}
			if (nextFrom.equals(to)) {
				directions.add(new ArrayList<>(List.of(NumpadDirection.UP)));
			} else {
				getDirections(nextFrom, to).forEach(nextDirections -> {
					nextDirections.addFirst(NumpadDirection.UP);
					directions.add(nextDirections);
				});
			}
		}
		for (int y = from.getY(); y < to.getY(); y++) {
			var nextFrom = from.getLowerNeighbour();
			if (nextFrom.equals(emptyPanel)) {
				continue;
			}
			if (nextFrom.equals(to)) {
				directions.add(new ArrayList<>(List.of(NumpadDirection.DOWN)));
			} else {
				getDirections(nextFrom, to).forEach(nextDirections -> {
					nextDirections.addFirst(NumpadDirection.DOWN);
					directions.add(nextDirections);
				});
			}
		}
		if (directions.isEmpty()) {
			directions.add(new ArrayList<>());
		}
		return directions;
	}
}

class NumericalKeypadRobot extends KeypadRobot<String> {
	public NumericalKeypadRobot() {
		super(createKeypad(), new Point(0, 3), "A");
	}

	private static Map<String, Point> createKeypad() {
		var keypad = new HashMap<String, Point>();
		keypad.put("7", new Point(0, 0));
		keypad.put("8", new Point(1, 0));
		keypad.put("9", new Point(2, 0));
		keypad.put("4", new Point(0, 1));
		keypad.put("5", new Point(1, 1));
		keypad.put("6", new Point(2, 1));
		keypad.put("1", new Point(0, 2));
		keypad.put("2", new Point(1, 2));
		keypad.put("3", new Point(2, 2));
		keypad.put("0", new Point(1, 3));
		keypad.put("A", new Point(2, 3));
		return keypad;
	}
}

class ArrowKeypadRobot extends KeypadRobot<NumpadDirection> {
	public ArrowKeypadRobot() {
		super(createKeypad(), new Point(0, 0), NumpadDirection.A);
	}

	private static Map<NumpadDirection, Point> createKeypad() {
		var keypad = new HashMap<NumpadDirection, Point>();
		keypad.put(NumpadDirection.UP, new Point(1, 0));
		keypad.put(NumpadDirection.A, new Point(2, 0));
		keypad.put(NumpadDirection.LEFT, new Point(0, 1));
		keypad.put(NumpadDirection.DOWN, new Point(1, 1));
		keypad.put(NumpadDirection.RIGHT, new Point(2, 1));
		return keypad;
	}
}

enum NumpadDirection {
	UP, DOWN, LEFT, RIGHT, A
}
