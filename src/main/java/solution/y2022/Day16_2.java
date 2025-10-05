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
		var solution = 0;
		var distanceMap = createDistanceMap(stringNetworkMap);

		Queue<ValveSituationWithElephant> queue = new LinkedList<>();
		var neighbours = new ArrayList<>(distanceMap.get(stringNetworkMap.get("AA")).keySet());
		for (var i = 0; i < neighbours.size() - 1; i++) {
			for (var j = i + 1; j < neighbours.size(); j++) {
				var neighbouringValve = neighbours.get(i);
				var neighbouringElephantValve = neighbours.get(j);
				var startingSituation = new ValveSituationWithElephant(neighbouringValve,
						distanceMap.get(stringNetworkMap.get("AA")).get(neighbouringValve), neighbouringElephantValve,
						distanceMap.get(stringNetworkMap.get("AA")).get(neighbouringElephantValve), 26, 0);
				startingSituation.getActivatedValves().add(neighbouringValve);
				startingSituation.getActivatedValves().add(neighbouringElephantValve);
				queue.add(startingSituation);
			}
		}

		while (!queue.isEmpty()) {
			var currentSituation = queue.poll();
			if (currentSituation.getNextValve() == null && currentSituation.getNextElephantValve() == null) {
				if (currentSituation.getScore() > solution) {
					solution = currentSituation.getScore();
					println(solution);
				}
				continue;
			}

			var nextSituations = new ArrayList<ValveSituationWithElephant>();
			var newSituation = new ValveSituationWithElephant();
			newSituation.setTimeLeft(currentSituation.getTimeLeft() - 1);
			newSituation.getActivatedValves().addAll(currentSituation.getActivatedValves());
			if (currentSituation.getNextValve() == null) {
				newSituation.setScore(currentSituation.getScore());
				nextSituations.add(newSituation);
			} else if (currentSituation.getNextValveTimeLeft() > 1) {
				newSituation.setScore(currentSituation.getScore());
				newSituation.setNextValve(currentSituation.getNextValve());
				newSituation.setNextValveTimeLeft(currentSituation.getNextValveTimeLeft() - 1);
				nextSituations.add(newSituation);
			} else {
				newSituation.setScore(currentSituation.getScore()
						+ currentSituation.getNextValve().getValue().getPressure() * (newSituation.getTimeLeft()));
				newSituation.setNextValveTimeLeft(currentSituation.getNextValveTimeLeft() - 1);
				nextSituations.add(newSituation);
				for (var neighbouringValve : distanceMap.get(currentSituation.getNextValve()).keySet()) {
					if (newSituation.getActivatedValves().contains(neighbouringValve)) {
						continue;
					}

					var nextSituation = new ValveSituationWithElephant(newSituation);
					nextSituation.setNextValve(neighbouringValve);
					nextSituation.setNextValveTimeLeft(
							distanceMap.get(currentSituation.getNextValve()).get(neighbouringValve));
					nextSituation.getActivatedValves().add(neighbouringValve);
					nextSituations.add(nextSituation);
				}
			}

			if (currentSituation.getNextElephantValve() == null) {
				queue.addAll(nextSituations);
			} else if (currentSituation.getNextElephantValveTimeLeft() > 1) {
				for (var nextSituation : nextSituations) {
					nextSituation.setNextElephantValve(currentSituation.getNextElephantValve());
					nextSituation.setNextElephantValveTimeLeft(currentSituation.getNextElephantValveTimeLeft() - 1);
					queue.add(nextSituation);
				}
			} else {
				for (var nextSituation : nextSituations) {
					nextSituation.setScore(
							nextSituation.getScore() + currentSituation.getNextElephantValve().getValue().getPressure()
									* (nextSituation.getTimeLeft()));
					queue.add(nextSituation);

					for (var neighbouringValve : distanceMap.get(currentSituation.getNextElephantValve()).keySet()) {
						if (nextSituation.getActivatedValves().contains(neighbouringValve)) {
							continue;
						}

						var newElephantSituation = new ValveSituationWithElephant(nextSituation);
						newElephantSituation.setNextElephantValve(neighbouringValve);
						newElephantSituation.setNextElephantValveTimeLeft(
								distanceMap.get(currentSituation.getNextElephantValve()).get(neighbouringValve));
						newElephantSituation.getActivatedValves().add(neighbouringValve);
						queue.add(newElephantSituation);
					}
				}
			}
		}

		return "" + solution;
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
@Setter
@AllArgsConstructor
class ValveSituationWithElephant {
	private Network<Valve> nextValve;
	private int nextValveTimeLeft;
	private Network<Valve> nextElephantValve;
	private int nextElephantValveTimeLeft;
	private int timeLeft;
	private int score;
	private final Set<Network<Valve>> activatedValves = new HashSet<>();

	public ValveSituationWithElephant() {
	}

	public ValveSituationWithElephant(ValveSituationWithElephant other) {
		this.nextValve = other.nextValve;
		this.nextValveTimeLeft = other.nextValveTimeLeft;
		this.nextElephantValve = other.nextElephantValve;
		this.nextElephantValveTimeLeft = other.nextElephantValveTimeLeft;
		this.timeLeft = other.timeLeft;
		this.score = other.score;
		this.activatedValves.addAll(other.activatedValves);
	}
}