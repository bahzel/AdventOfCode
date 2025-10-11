package solution.y2022;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

import utils.soution.InstructionSolution;

public class Day19_2 extends InstructionSolution<Blueprint, AtomicInteger> {
	public static void main(String[] args) {
		new Day19_2().solve();
	}

	@Override
	protected AtomicInteger initializeValue() {
		return new AtomicInteger(1);
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		// return instructions;
		return instructions.subList(0, 3);
	}

	@Override
	protected Blueprint transformInstruction(String instruction) {
		var instructions = instruction.replace(":", "").split(" ");
		return new Blueprint(Integer.parseInt(instructions[1]), Integer.parseInt(instructions[6]),
				Integer.parseInt(instructions[12]), Integer.parseInt(instructions[18]),
				Integer.parseInt(instructions[21]), Integer.parseInt(instructions[27]),
				Integer.parseInt(instructions[30]));
	}

	@Override
	protected boolean performInstruction(Blueprint blueprint, AtomicInteger atomicInteger) {
		Queue<GeodeCollectingState> queue = new LinkedList<>();
		queue.add(new GeodeCollectingState(32, 0, 0, 0, 0, 1, 0, 0, 0));
		var cache = new HashMap<GeodeCollectingState, GeodeCollectingState>();

		var max = 0;
		while (!queue.isEmpty()) {
			var currentState = queue.poll();
			if (currentState.getMinutesLeft() <= 1) {
				max = Math.max(max,
						currentState.getGeode() + currentState.getGeodeRobots() * currentState.getMinutesLeft());
				continue;
			}
			var cachedState = cache.get(currentState);
			if (cachedState != null && cachedState.getMinutesLeft() > currentState.getMinutesLeft()) {
				continue;
			} else {
				cache.put(currentState, currentState);
			}

			// build an ore robot next
			GeodeCollectingState newState;
			var oreCostLeft = blueprint.getOreRobotCostOre() - currentState.getOre();
			if (currentState.getOreRobots() == blueprint.getMaxOreRobotCount() || currentState.getMinutesLeft() < 3) {
				max = Math.max(max,
						currentState.getGeode() + currentState.getGeodeRobots() * currentState.getMinutesLeft());
			} else if (oreCostLeft <= 0) {
				newState = new GeodeCollectingState(currentState, 1);
				newState.setOre(newState.getOre() - blueprint.getOreRobotCostOre());
				newState.setOreRobots(newState.getOreRobots() + 1);
				queue.add(newState);
			} else {
				var timeToWait = computeTimeToWait(oreCostLeft, currentState.getOreRobots());
				if (timeToWait < currentState.getMinutesLeft() - 2) {
					newState = new GeodeCollectingState(currentState, timeToWait + 1);
					newState.setOre(newState.getOre() - blueprint.getOreRobotCostOre());
					newState.setOreRobots(newState.getOreRobots() + 1);
					queue.add(newState);
				} else {
					max = Math.max(max,
							currentState.getGeode() + currentState.getGeodeRobots() * currentState.getMinutesLeft());
				}
			}

			// build a clay robot next
			oreCostLeft = blueprint.getClayRobotCostOre() - currentState.getOre();
			if (currentState.getClayRobots() == blueprint.getMaxClayRobotCount() || currentState.getMinutesLeft() < 4) {
				max = Math.max(max,
						currentState.getGeode() + currentState.getGeodeRobots() * currentState.getMinutesLeft());
			} else if (oreCostLeft <= 0) {
				newState = new GeodeCollectingState(currentState, 1);
				newState.setOre(newState.getOre() - blueprint.getClayRobotCostOre());
				newState.setClayRobots(newState.getClayRobots() + 1);
				queue.add(newState);
			} else {
				var timeToWait = computeTimeToWait(oreCostLeft, currentState.getOreRobots());
				if (timeToWait < currentState.getMinutesLeft() - 3) {
					newState = new GeodeCollectingState(currentState, timeToWait + 1);
					newState.setOre(newState.getOre() - blueprint.getClayRobotCostOre());
					newState.setClayRobots(newState.getClayRobots() + 1);
					queue.add(newState);
				} else {
					max = Math.max(max,
							currentState.getGeode() + currentState.getGeodeRobots() * currentState.getMinutesLeft());
				}
			}

			// build an obsidian robot next
			oreCostLeft = blueprint.getObsidianRobotCostOre() - currentState.getOre();
			var clayCostLeft = blueprint.getObsidianRobotCostClay() - currentState.getClay();
			if (currentState.getObsidianRobots() == blueprint.getMaxObsidianRobotCount()
					|| currentState.getMinutesLeft() < 3) {
				max = Math.max(max,
						currentState.getGeode() + currentState.getGeodeRobots() * currentState.getMinutesLeft());
			} else if (oreCostLeft <= 0 && clayCostLeft <= 0) {
				newState = new GeodeCollectingState(currentState, 1);
				newState.setOre(newState.getOre() - blueprint.getObsidianRobotCostOre());
				newState.setClay(newState.getClay() - blueprint.getObsidianRobotCostClay());
				newState.setObsidianRobots(newState.getObsidianRobots() + 1);
				queue.add(newState);
			} else {
				var timeToWait = Math.max(computeTimeToWait(oreCostLeft, currentState.getOreRobots()),
						computeTimeToWait(clayCostLeft, currentState.getClayRobots()));
				if (timeToWait < currentState.getMinutesLeft() - 2) {
					newState = new GeodeCollectingState(currentState, timeToWait + 1);
					newState.setOre(newState.getOre() - blueprint.getObsidianRobotCostOre());
					newState.setClay(newState.getClay() - blueprint.getObsidianRobotCostClay());
					newState.setObsidianRobots(newState.getObsidianRobots() + 1);
					queue.add(newState);
				} else {
					max = Math.max(max,
							currentState.getGeode() + currentState.getGeodeRobots() * currentState.getMinutesLeft());
				}
			}

			// build a geode robot next
			oreCostLeft = blueprint.getGeodeRobotCostOre() - currentState.getOre();
			var obsidianCostLeft = blueprint.getGeodeRobotCostObsidian() - currentState.getObsidian();
			if (oreCostLeft <= 0 && obsidianCostLeft <= 0) {
				if (currentState.getMinutesLeft() < 2) {
					max = Math.max(max,
							currentState.getGeode() + currentState.getGeodeRobots() * currentState.getMinutesLeft());
				} else {
					newState = new GeodeCollectingState(currentState, 1);
					newState.setOre(newState.getOre() - blueprint.getGeodeRobotCostOre());
					newState.setObsidian(newState.getObsidian() - blueprint.getGeodeRobotCostObsidian());
					newState.setGeodeRobots(newState.getGeodeRobots() + 1);
					queue.add(newState);
				}
			} else {
				var timeToWait = Math.max(computeTimeToWait(oreCostLeft, currentState.getOreRobots()),
						computeTimeToWait(obsidianCostLeft, currentState.getObsidianRobots()));
				if (timeToWait < currentState.getMinutesLeft() - 1) {
					newState = new GeodeCollectingState(currentState, timeToWait + 1);
					newState.setOre(newState.getOre() - blueprint.getGeodeRobotCostOre());
					newState.setObsidian(newState.getObsidian() - blueprint.getGeodeRobotCostObsidian());
					newState.setGeodeRobots(newState.getGeodeRobots() + 1);
					queue.add(newState);
				} else {
					max = Math.max(max,
							currentState.getGeode() + currentState.getGeodeRobots() * currentState.getMinutesLeft());
				}
			}
		}

		atomicInteger.set(atomicInteger.get() * max);
		return false;
	}

	private int computeTimeToWait(int cost, int robots) {
		if (robots == 0) {
			return Integer.MAX_VALUE;
		}
		return (int) Math.ceil((double) cost / robots);
	}

	@Override
	protected String getSolution(AtomicInteger atomicInteger) {
		return atomicInteger.toString();
	}
}