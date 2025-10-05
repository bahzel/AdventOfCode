package solution.y2022;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import utils.Network;
import utils.soution.MapSolution;

public class Day16_1 extends MapSolution<Map<String, Network<Valve>>> {
	public static void main(String[] args) {
		new Day16_1().solve();
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
		var solution = 0;
		var solutionLog = "";
		var distanceMap = createDistanceMap(stringNetworkMap);

		Queue<ValveSituation> queue = new LinkedList<>();
		queue.add(new ValveSituation(stringNetworkMap.get("AA"), 30, 0, ""));

		while (!queue.isEmpty()) {
			var currentSituation = queue.poll();
			for (var neighbouringValve : distanceMap.get(currentSituation.getCurrentValve()).keySet()) {
				if (!currentSituation.getActivatedValves().contains(neighbouringValve)
						&& distanceMap	.get(currentSituation.getCurrentValve())
										.get(neighbouringValve) <= currentSituation.getTimeLeft()) {
					var timeLeft = currentSituation.getTimeLeft()
							- distanceMap.get(currentSituation.getCurrentValve()).get(neighbouringValve);
					var nextSituation = new ValveSituation(neighbouringValve, timeLeft,
							currentSituation.getScore() + neighbouringValve.getValue().getPressure() * timeLeft,
							currentSituation.getLog());
					nextSituation.getActivatedValves().addAll(currentSituation.getActivatedValves());
					nextSituation.getActivatedValves().add(neighbouringValve);
					nextSituation.addLog(neighbouringValve.getValue().getName() + " opened with pressure "
							+ neighbouringValve.getValue().getPressure() + " at time " + timeLeft + " with score "
							+ nextSituation.getScore());
					queue.add(nextSituation);
				} else if (currentSituation.getScore() > solution) {
					solution = currentSituation.getScore();
					solutionLog = currentSituation.getLog();
				}
			}
		}

		print(solutionLog);
		return solution + "";
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

@Getter
@AllArgsConstructor
class ValveSituation {
	private final Network<Valve> currentValve;
	private final int timeLeft;
	private final int score;
	private final Set<Network<Valve>> activatedValves = new HashSet<>();
	private String log;

	public void addLog(String log) {
		this.log += log + "\n";
	}
}

@AllArgsConstructor
@Getter
@Setter
class Valve {
	private int pressure;
	private final String name;
}