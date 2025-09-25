package solution.y2017;

import java.util.HashMap;
import java.util.Map;

import utils.Network;
import utils.soution.MapSolution;

public class Day12_2 extends MapSolution<Map<Integer, Network<Integer>>> {
	public static void main(String[] args) {
		new Day12_2().solve();
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
		var networkCount = 0;
		while (!stringNetworkMap.isEmpty()) {
			removeNetwork(stringNetworkMap.values().iterator().next(), stringNetworkMap);
			networkCount++;
		}

		return networkCount + "";
	}

	private void removeNetwork(Network<Integer> network, Map<Integer, Network<Integer>> stringNetworkMap) {
		if (stringNetworkMap.remove(network.getValue()) == null) {
			return;
		}

		for (var neighbour : network.getNeighbours()) {
			removeNetwork(neighbour, stringNetworkMap);
		}
	}
}
