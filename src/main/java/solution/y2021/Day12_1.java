package solution.y2021;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import utils.Network;
import utils.soution.InstructionSolution;

public class Day12_1 extends InstructionSolution<String, Map<String, Network<String>>> {
	public static void main(String[] args) {
		new Day12_1().solve();
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
		Queue<Pair<Network<String>, Set<String>>> queue = new LinkedList<>();
		queue.add(Pair.of(stringNetworkMap.get("start"), Set.of("start")));
		while (!queue.isEmpty()) {
			var currentCave = queue.poll();
			if ("end".equals(currentCave.getLeft().getValue())) {
				count++;
				continue;
			}

			for (var neighbour : currentCave.getLeft().getNeighbours()) {
				if ("start".equals(neighbour.getValue()) || StringUtils.isAllLowerCase(neighbour.getValue())
						&& currentCave.getRight().contains(neighbour.getValue())) {
					continue;
				}

				var visitedCaves = new HashSet<>(currentCave.getRight());
				visitedCaves.add(neighbour.getValue());
				queue.add(Pair.of(neighbour, visitedCaves));
			}
		}

		return count + "";
	}
}
