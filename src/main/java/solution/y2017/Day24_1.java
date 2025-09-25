package solution.y2017;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;
import utils.soution.MapSolution;

public class Day24_1 extends MapSolution<Map<Integer, List<Component>>> {
	public static void main(String[] args) {
		new Day24_1().solve();
	}

	@Override
	protected Map<Integer, List<Component>> initializeMapping() {
		return new HashMap<>();
	}

	@Override
	protected void transformInstruction(String instruction, Map<Integer, List<Component>> integerComponentMap) {
		var instructions = instruction.split("/");
		var input = Integer.parseInt(instructions[0]);
		var output = Integer.parseInt(instructions[1]);
		var component = new Component(input, output);

		if (integerComponentMap.containsKey(input)) {
			integerComponentMap.get(input).add(component);
		} else {
			integerComponentMap.put(input, new ArrayList<>(List.of(component)));
		}

		if (integerComponentMap.containsKey(output)) {
			integerComponentMap.get(output).add(component);
		} else {
			integerComponentMap.put(output, new ArrayList<>(List.of(component)));
		}
	}

	@Override
	protected String computeSolution(Map<Integer, List<Component>> integerComponentMap) {
		Queue<Pair<List<Component>, Integer>> queue = new LinkedList<>();
		queue.add(Pair.of(Collections.emptyList(), 0));
		var maximumStrength = Integer.MIN_VALUE;

		while (!queue.isEmpty()) {
			var bridge = queue.poll();
			var tileFound = false;
			var possibleNextTiles = integerComponentMap.getOrDefault(bridge.getRight(), Collections.emptyList());

			for (var possibleNextTile : possibleNextTiles) {
				if (!bridge.getLeft().contains(possibleNextTile)) {
					var newBridge = new ArrayList<>(bridge.getLeft());
					newBridge.add(possibleNextTile);
					if (possibleNextTile.getInput() == bridge.getRight()) {
						queue.add(Pair.of(newBridge, possibleNextTile.getOutput()));
					} else {
						queue.add(Pair.of(newBridge, possibleNextTile.getInput()));
					}
					tileFound = true;
				}
			}

			if (!tileFound) {
				maximumStrength = Math.max(maximumStrength, computeStrength(bridge.getLeft()));
			}
		}

		return maximumStrength + "";
	}

	private int computeStrength(List<Component> bridge) {
		return bridge.stream().map(Component::getStrength).mapToInt(Integer::intValue).sum();
	}
}

@Getter
@AllArgsConstructor
class Component {
	private final int input;
	private final int output;

	public int getStrength() {
		return input + output;
	}
}
