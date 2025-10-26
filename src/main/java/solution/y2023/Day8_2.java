package solution.y2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import utils.Direction;
import utils.MathUtils;
import utils.StringTransformer;
import utils.soution.Solution;

public class Day8_2 extends Solution {
	public static void main(String[] args) {
		new Day8_2().solve();
	}

	@Override
	protected String doSolve() {
		var instructions = getInstructions();
		var network = getNetwork();
		var startingPoints = network.entrySet()
									.stream()
									.filter(entry -> entry.getKey().endsWith("A"))
									.map(Map.Entry::getValue)
									.toList();
		var endPoints = network	.entrySet()
								.stream()
								.filter(entry -> entry.getKey().endsWith("Z"))
								.map(Map.Entry::getValue)
								.collect(Collectors.toSet());

		var periods = new ArrayList<Long>();
		for (var startingPoint : startingPoints) {
			var current = startingPoint;

			var i = 0L;
			for (; !endPoints.contains(current); i++) {
				switch (instructions.get((int) (i % instructions.size()))) {
				case Direction.LEFT -> current = current.getLeft();
				case Direction.RIGHT -> current = current.getRight();
				}
			}

			periods.add(i);
		}

		var solution = periods.getFirst();
		for (var i = 1; i < periods.size(); i++) {
			solution = MathUtils.leastCommonMultiple(solution, periods.get(i));
		}

		return solution + "";
	}

	private List<Direction> getInstructions() {
		return StringTransformer.splitString(input.getFirst()).stream().map(instruction -> switch (instruction) {
		case "L" -> Direction.LEFT;
		case "R" -> Direction.RIGHT;
		default -> throw new IllegalArgumentException();
		}).toList();
	}

	private Map<String, Path> getNetwork() {
		var network = new HashMap<String, Path>();

		for (var i = 2; i < input.size(); i++) {
			var inputs = input.get(i).replace("(", "").replace(")", "").replace(", ", " = ").split(" = ");
			network	.computeIfAbsent(inputs[0], key -> new Path())
					.setLeft(network.computeIfAbsent(inputs[1], key -> new Path()))
					.setRight(network.computeIfAbsent(inputs[2], key -> new Path()));
		}

		return network;
	}
}