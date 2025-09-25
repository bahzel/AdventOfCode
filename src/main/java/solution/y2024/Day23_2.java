package solution.y2024;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import utils.Network;
import utils.soution.MapSolution;

public class Day23_2 extends MapSolution<Map<String, Network<String>>> {
	public static void main(String[] args) {
		new Day23_2().solve();
	}

	@Override
	protected Map<String, Network<String>> initializeMapping() {
		return new HashMap<>();
	}

	@Override
	protected void transformInstruction(String instruction, Map<String, Network<String>> stringNetworkMap) {
		var instructions = instruction.split("-");
		var computer1 = stringNetworkMap.computeIfAbsent(instructions[0], key -> {
			var network = new Network<>(key);
			network.addNeighbour(network);
			return network;
		});
		var computer2 = stringNetworkMap.computeIfAbsent(instructions[1], key -> {
			var network = new Network<>(key);
			network.addNeighbour(network);
			return network;
		});
		computer1.addNeighbour(computer2);
		computer2.addNeighbour(computer1);
	}

	@Override
	protected String computeSolution(Map<String, Network<String>> stringNetworkMap) {
		return stringNetworkMap.values()
							   .stream()
							   .map(this::getBiggestLanParty)
							   .max(Comparator.comparingInt(Set::size))
							   .orElseThrow()
							   .stream()
							   .map(Network::getValue)
							   .sorted()
							   .collect(Collectors.joining(","));
	}

	private Set<Network<String>> getBiggestLanParty(Network<String> computer) {
		var lanParty = new HashSet<>(computer.getNeighbours());
		lanParty.add(computer);

		while (!isValidLanParty(lanParty)) {
			var lowestIntersection = lanParty.stream().map(cpu -> {
				var intersection = new HashSet<>(cpu.getNeighbours());
				intersection.retainAll(computer.getNeighbours());
				return Pair.of(cpu, intersection.size());
			}).min(Comparator.comparingInt(Pair::getRight)).orElseThrow().getLeft();
			lanParty.remove(lowestIntersection);
		}

		return lanParty;
	}

	private boolean isValidLanParty(Set<Network<String>> lanParty) {
		for (var computer : lanParty) {
			if (!new HashSet<>(computer.getNeighbours()).containsAll(lanParty)) {
				return false;
			}
		}
		return true;
	}
}
