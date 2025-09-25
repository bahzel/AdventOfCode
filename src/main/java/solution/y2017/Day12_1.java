package solution.y2017;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import utils.Network;
import utils.soution.MapSolution;

public class Day12_1 extends MapSolution<Map<Integer, Network<Integer>>> {
	public static void main(String[] args) {
		new Day12_1().solve();
	}

	@Override
	protected Map<Integer, Network<Integer>> initializeMapping() {
		return new HashMap<>();
	}

	@Override
	protected void transformInstruction(String instruction, Map<Integer, Network<Integer>> stringNetworkMap) {
		var instructions = instruction.split(" <-> ");
		var neighbours = instructions[1].split(", ");

		var network = stringNetworkMap.computeIfAbsent(Integer.parseInt(instructions[0]), Network::new);
		for (String neighbour : neighbours) {
			network.addNeighbour(stringNetworkMap.computeIfAbsent(Integer.parseInt(neighbour), Network::new));
		}
	}

	@Override
	protected String computeSolution(Map<Integer, Network<Integer>> stringNetworkMap) {
		return countDistinctNeighbours(stringNetworkMap.get(0), new HashSet<>()) + "";
	}

	private int countDistinctNeighbours(Network<Integer> network, Set<Integer> cache) {
		if (!cache.add(network.getValue())) {
			return 0;
		}

		var count = 1;
		for (var neighbour : network.getNeighbours()) {
			count += countDistinctNeighbours(neighbour, cache);
		}
		return count;
	}
}
