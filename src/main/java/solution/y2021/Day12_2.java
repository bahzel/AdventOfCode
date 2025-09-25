package solution.y2021;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;

import utils.Network;
import utils.soution.InstructionSolution;

public class Day12_2 extends InstructionSolution<String, Map<String, Network<String>>> {
	public static void main(String[] args) {
		new Day12_2().solve();
	}

	@Override
	protected Map<String, Network<String>> initializeValue() {
		return new HashMap<>();
	}

	@Override
	protected String transformInstruction(String instruction) {
		return instruction;
	}

	@Override
	protected boolean performInstruction(String s, Map<String, Network<String>> stringNetworkMap) {
		var instructions = s.split("-");
		stringNetworkMap.computeIfAbsent(instructions[0], Network::new)
						.addNeighbour(stringNetworkMap.computeIfAbsent(instructions[1], Network::new));
		return false;
	}

	@Override
	protected String getSolution(Map<String, Network<String>> stringNetworkMap) {
		var count = 0;
		Queue<Triple<Network<String>, Set<String>, Boolean>> queue = new LinkedList<>();
		queue.add(Triple.of(stringNetworkMap.get("start"), Set.of("start"), false));
		while (!queue.isEmpty()) {
			var currentCave = queue.poll();
			if ("end".equals(currentCave.getLeft().getValue())) {
				count++;
				continue;
			}

			for (var neighbour : currentCave.getLeft().getNeighbours()) {
				if ("start".equals(neighbour.getValue()) || StringUtils.isAllLowerCase(neighbour.getValue())
						&& currentCave.getMiddle().contains(neighbour.getValue()) && currentCave.getRight()) {
					continue;
				}

				var visitedCaves = new HashSet<>(currentCave.getMiddle());
				if (!visitedCaves.add(neighbour.getValue()) && StringUtils.isAllLowerCase(neighbour.getValue())) {
					queue.add(Triple.of(neighbour, visitedCaves, true));
				} else {
					queue.add(Triple.of(neighbour, visitedCaves, currentCave.getRight()));
				}
			}
		}

		return count + "";
	}
}
