package solution.y2022;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import utils.Network;
import utils.soution.MapSolution;

public class Day16_2 extends MapSolution<Map<String, Network<Valve>>> {
	public static void main(String[] args) {
		new Day16_2().solve();
	}

	@Override
	protected Map<String, Network<Valve>> initializeMapping() {
		return new HashMap<>();
	}

	@Override
	protected void transformInstruction(String instruction, Map<String, Network<Valve>> stringNetworkMap) {
		var instructions = instruction.replace("=", " ").replace("; ", " ").replace(", ", " ").split(" ");
		var valve = stringNetworkMap.computeIfAbsent(instructions[1], name -> new Network<>(new Valve(0, name)));
		valve.getValue().setPressure(Integer.parseInt(instructions[5]));

		for (var i = 10; i < instructions.length; i++) {
			valve.addNeighbour(
					stringNetworkMap.computeIfAbsent(instructions[i], name -> new Network<>(new Valve(0, name))));
		}

		stringNetworkMap.put(instructions[1], valve);
	}

	@Override
	protected String computeSolution(Map<String, Network<Valve>> stringNetworkMap) {
		var distanceMap = createDistanceMap(stringNetworkMap);
		var startingPoint = stringNetworkMap.get("AA");
		var destinations = new ArrayList<>(distanceMap.get(startingPoint).keySet());
		var sizeOfDestinations = destinations.size();
		var max = 0;

		for (var size = (sizeOfDestinations + 1) / 2; size < sizeOfDestinations; size++) {
			Set<Set<Network<Valve>>> combinationsHuman = new HashSet<>(List.of(new HashSet<>()));
			while (combinationsHuman.iterator().next().size() < size) {
				combinationsHuman = addNextStep(combinationsHuman, destinations);
			}

			for (var combinationHuman : combinationsHuman) {
				var pressure = computePressure(startingPoint, combinationHuman, distanceMap, 26);
				var combinationElephant = new HashSet<>(destinations);
				combinationElephant.removeAll(combinationHuman);
				pressure += computePressure(startingPoint, combinationElephant, distanceMap, 26);
				if (pressure > max) {
					max = pressure;
					println(max);
				}
			}
		}

		return max + "";
	}

	private Set<Set<Network<Valve>>> addNextStep(Set<Set<Network<Valve>>> combinations,
			List<Network<Valve>> destinations) {
		var nextSteps = new HashSet<Set<Network<Valve>>>();
		for (var combination : combinations) {
			for (var destination : destinations) {
				if (combination.contains(destination)) {
					continue;
				}
				var newCombination = new HashSet<>(combination);
				newCombination.add(destination);
				nextSteps.add(newCombination);
			}
		}
		return nextSteps;
	}

	private final Map<Triple<Network<Valve>, Set<Network<Valve>>, Integer>, Integer> CACHE = new HashMap<>();

	private int computePressure(Network<Valve> lastValve, Set<Network<Valve>> valves,
			Map<Network<Valve>, Map<Network<Valve>, Integer>> distanceMap, int time) {
		var key = Triple.of(lastValve, valves, time);
		if (CACHE.containsKey(key)) {
			return CACHE.get(key);
		} else if (valves.size() == 1) {
			var valve = valves.iterator().next();
			return (time - distanceMap.get(lastValve).get(valve)) * valve.getValue().getPressure();
		}

		var max = 0;
		for (var valve : valves) {
			var timeLeft = time - distanceMap.get(lastValve).get(valve);
			var leftValves = new HashSet<>(valves);
			leftValves.remove(valve);
			max = Math.max(max, timeLeft * valve.getValue().getPressure()
					+ computePressure(valve, leftValves, distanceMap, timeLeft));
		}
		CACHE.put(key, max);
		return max;
	}

	private Map<Network<Valve>, Map<Network<Valve>, Integer>> createDistanceMap(
			Map<String, Network<Valve>> stringNetworkMap) {
		var distanceMap = new HashMap<Network<Valve>, Map<Network<Valve>, Integer>>();
		var relevantValves = stringNetworkMap	.values()
												.stream()
												.filter(valve -> valve.getValue().getPressure() > 0)
												.toList();
		var startingValves = new ArrayList<>(relevantValves);
		startingValves.add(stringNetworkMap.get("AA"));

		for (var startingValve : startingValves) {
			var goalMap = new HashMap<Network<Valve>, Integer>();
			for (var relevantValve : relevantValves) {
				goalMap.put(relevantValve, computeDistance(startingValve, relevantValve));
			}
			distanceMap.put(startingValve, goalMap);
		}

		return distanceMap;
	}

	private int computeDistance(Network<Valve> startingValve, Network<Valve> goalValve) {
		var visitedValves = new HashSet<Network<Valve>>();

		Queue<Pair<Network<Valve>, Integer>> queue = new LinkedList<>();
		queue.add(Pair.of(startingValve, 0));
		while (!queue.isEmpty()) {
			var currentValve = queue.remove();
			if (!visitedValves.add(currentValve.getLeft())) {
				continue;
			} else if (currentValve.getLeft() == goalValve) {
				return currentValve.getRight() + 1;
			} else {
				currentValve.getLeft()
							.getNeighbours()
							.forEach(neighbour -> queue.add(Pair.of(neighbour, currentValve.getRight() + 1)));
			}
		}
		throw new RuntimeException("Couldn't find neighbours");
	}
}