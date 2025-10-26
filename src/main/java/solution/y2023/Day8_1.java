package solution.y2023;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import utils.Direction;
import utils.StringTransformer;
import utils.soution.Solution;

public class Day8_1 extends Solution {
	public static void main(String[] args) {
		new Day8_1().solve();
	}

	@Override
	protected String doSolve() {
		var instructions = getInstructions();
		var network = getNetwork();
		var current = network.get("AAA");
		var end = network.get("ZZZ");

		var i = 0;
		for (; current != end; i++) {
			switch (instructions.get(i % instructions.size())) {
			case Direction.LEFT -> current = current.getLeft();
			case Direction.RIGHT -> current = current.getRight();
			}
		}

		return i + "";
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

@Getter
@NoArgsConstructor
class Path {
	private Path left;
	private Path right;

	public Path setLeft(Path left) {
		this.left = left;
		return this;
	}

	public Path setRight(Path right) {
		this.right = right;
		return this;
	}
}