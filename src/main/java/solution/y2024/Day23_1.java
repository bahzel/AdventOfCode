package solution.y2024;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import utils.Network;
import utils.soution.MapSolution;

public class Day23_1 extends MapSolution<Map<String, Network<String>>> {
	public static void main(String[] args) {
		new Day23_1().solve();
	}

	@Override
	protected Map<String, Network<String>> initializeMapping() {
		return new HashMap<>();
	}

	@Override
	protected void transformInstruction(String instruction, Map<String, Network<String>> stringNetworkMap) {
		var instructions = instruction.split("-");
		var computer1 = stringNetworkMap.computeIfAbsent(instructions[0], Network::new);
		var computer2 = stringNetworkMap.computeIfAbsent(instructions[1], Network::new);
		computer1.addNeighbour(computer2);
		computer2.addNeighbour(computer1);
	}

	@Override
	protected String computeSolution(Map<String, Network<String>> stringNetworkMap) {
		var networks = new HashSet<Set<Network<String>>>();
		stringNetworkMap.values().forEach(computer -> networks.addAll(getTripleNetwork(computer)));
		return networks.size() + "";
	}

	private Set<Set<Network<String>>> getTripleNetwork(Network<String> firstComputer) {
		var network = new HashSet<Set<Network<String>>>();
		for (var secondComputer : firstComputer.getNeighbours()) {
			for (var thirdComputer : secondComputer.getNeighbours()) {
				if (!firstComputer.getValue().startsWith("t") && !secondComputer.getValue().startsWith("t")
						&& !thirdComputer.getValue().startsWith("t")) {
					continue;
				}

				for (var fourthComputer : thirdComputer.getNeighbours()) {
					if (fourthComputer == firstComputer) {
						network.add(Set.of(firstComputer, secondComputer, thirdComputer));
					}
				}
			}
		}
		return network;
	}
}
