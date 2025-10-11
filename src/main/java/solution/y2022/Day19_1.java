package solution.y2022;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import utils.soution.InstructionSolution;

public class Day19_1 extends InstructionSolution<Blueprint, AtomicInteger> {
	public static void main(String[] args) {
		new Day19_1().solve();
	}

	@Override
	protected AtomicInteger initializeValue() {
		return new AtomicInteger();
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
		queue.add(new GeodeCollectingState(24, 0, 0, 0, 0, 1, 0, 0, 0));

		var max = 0;
		while (!queue.isEmpty()) {
			var currentState = queue.poll();
			if (currentState.getMinutesLeft() <= 1) {
				max = Math.max(max,
						currentState.getGeode() + currentState.getGeodeRobots() * currentState.getMinutesLeft());
				continue;
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

		atomicInteger.addAndGet(max * blueprint.getId());
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

@Getter
class Blueprint {
	private final int id;
	private final int oreRobotCostOre;
	private final int clayRobotCostOre;
	private final int obsidianRobotCostOre;
	private final int obsidianRobotCostClay;
	private final int geodeRobotCostOre;
	private final int geodeRobotCostObsidian;
	private final int maxOreRobotCount;
	private final int maxClayRobotCount;
	private final int maxObsidianRobotCount;

	Blueprint(int id, int oreRobotCostOre, int clayRobotCostOre, int obsidianRobotCostOre, int obsidianRobotCostClay,
			int geodeRobotCostOre, int geodeRobotCostObsidian) {
		this.id = id;
		this.oreRobotCostOre = oreRobotCostOre;
		this.clayRobotCostOre = clayRobotCostOre;
		this.obsidianRobotCostOre = obsidianRobotCostOre;
		this.obsidianRobotCostClay = obsidianRobotCostClay;
		this.geodeRobotCostOre = geodeRobotCostOre;
		this.geodeRobotCostObsidian = geodeRobotCostObsidian;
		this.maxOreRobotCount = Math.max(Math.max(oreRobotCostOre, clayRobotCostOre),
				Math.max(obsidianRobotCostOre, geodeRobotCostOre));
		this.maxClayRobotCount = obsidianRobotCostClay;
		this.maxObsidianRobotCount = geodeRobotCostObsidian;
	}
}

@AllArgsConstructor
@Getter
@Setter
@ToString
class GeodeCollectingState {
	private int minutesLeft;
	private int ore;
	private int clay;
	private int obsidian;
	private int geode;

	private int oreRobots;
	private int clayRobots;
	private int obsidianRobots;
	private int geodeRobots;

	public GeodeCollectingState(GeodeCollectingState other, int timeDelta) {
		this.minutesLeft = other.minutesLeft - timeDelta;
		this.ore = other.ore + other.oreRobots * timeDelta;
		this.clay = other.clay + other.clayRobots * timeDelta;
		this.obsidian = other.obsidian + other.obsidianRobots * timeDelta;
		this.geode = other.geode + other.geodeRobots * timeDelta;
		this.oreRobots = other.oreRobots;
		this.clayRobots = other.clayRobots;
		this.obsidianRobots = other.obsidianRobots;
		this.geodeRobots = other.geodeRobots;
	}
}