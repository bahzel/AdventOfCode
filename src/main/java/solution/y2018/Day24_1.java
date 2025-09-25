package solution.y2018;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;
import utils.soution.Solution;

public class Day24_1 extends Solution {
	private final int boost;
	private final SelectingOrderComparator selectingOrderComparator = new SelectingOrderComparator();
	private final AttackingOrderComparator attackingOrderComparator = new AttackingOrderComparator();
	@Getter
	private List<Brigade> armies;

	public Day24_1() {
		super();
		boost = 0;
	}

	public Day24_1(int boost) {
		super();
		this.boost = boost;
	}

	public static void main(String[] args) {
		new Day24_1().solve();
	}

	@Override
	protected String doSolve() {
		armies = createArmies();

		for (int i = 1; armies.stream().anyMatch(brigade -> brigade instanceof ImmuneSystem) && armies.stream()
																									  .anyMatch(
																											  brigade -> brigade instanceof Infection); i++) {
			println("----- Round:" + i + " -----");
			var unitCountBefore = getUnitCount();
			performRound();
			if (unitCountBefore == getUnitCount()) {
				armies = armies.reversed();
				return "0";
			}
		}

		return getUnitCount() + "";
	}

	private int getUnitCount() {
		return armies.stream().mapToInt(Brigade::getUnitCount).sum();
	}

	private void performRound() {
		armies.forEach(brigade -> println(brigade + "(effective power: " + brigade.getEffectivePower() + ")"));
		println();

		var targetSelection = performTargetSelection();
		performAttacks(targetSelection);
		armies.removeIf(brigade -> !brigade.isAlive());
	}

	private List<Pair<Brigade, Brigade>> performTargetSelection() {
		var targetSelection = new ArrayList<Pair<Brigade, Brigade>>();

		armies.stream().sorted(selectingOrderComparator).forEach(brigade -> {
			var targetComparator = new TargetComparator(brigade);
			var assignedTargets = targetSelection.stream().map(Pair::getRight).toList();
			var possibleTarget = armies.stream()
									   .filter(target -> !target.getClass().equals(brigade.getClass()))
									   .filter(target -> !assignedTargets.contains(target))
									   .filter(target -> target.computeReceivedDamage(brigade) > 0)
									   .max(targetComparator);
			if (possibleTarget.isPresent()) {
				targetSelection.add(Pair.of(brigade, possibleTarget.get()));
				println(brigade + " will attack " + possibleTarget.get());
			} else {
				println(brigade + " did not find any target");
			}
		});

		return targetSelection;
	}

	private void performAttacks(List<Pair<Brigade, Brigade>> targetSelection) {
		targetSelection.stream().sorted(attackingOrderComparator).forEach(pair -> {
			if (pair.getLeft().isAlive()) {
				pair.getRight().receiveDamage(pair.getLeft());
			}
		});
	}

	private List<Brigade> createArmies() {
		var armies = new ArrayList<Brigade>();

		var indexSeparator = input.indexOf("");
		var immuneSystemList = input.subList(1, indexSeparator);
		for (int i = 0; i < immuneSystemList.size(); i++) {
			armies.add(new ImmuneSystem(immuneSystemList.get(i), i + 1, boost, isLog()));
		}
		var infectionList = input.subList(indexSeparator + 2, input.size());
		for (int i = 0; i < infectionList.size(); i++) {
			armies.add(new Infection(infectionList.get(i), i + 1, isLog()));
		}

		return armies;
	}
}

class SelectingOrderComparator implements Comparator<Brigade> {
	@Override
	public int compare(Brigade brigade1, Brigade brigade2) {
		var effectivePower1 = brigade1.getEffectivePower();
		var effectivePower2 = brigade2.getEffectivePower();

		return effectivePower1 == effectivePower2
				? brigade2.getInitiative() - brigade1.getInitiative()
				: effectivePower2 - effectivePower1;
	}
}

@AllArgsConstructor
class TargetComparator implements Comparator<Brigade> {
	private final Brigade brigade;

	@Override
	public int compare(Brigade target1, Brigade target2) {
		var receivedDamage1 = target1.computeReceivedDamage(brigade);
		var receivedDamage2 = target2.computeReceivedDamage(brigade);

		return receivedDamage1 == receivedDamage2 ? target1.getEffectivePower() == target2.getEffectivePower()
				? target1.getInitiative() - target2.getInitiative()
				: target1.getEffectivePower() - target2.getEffectivePower() : receivedDamage1 - receivedDamage2;
	}
}

class AttackingOrderComparator implements Comparator<Pair<Brigade, Brigade>> {
	@Override
	public int compare(Pair<Brigade, Brigade> brigade1, Pair<Brigade, Brigade> brigade2) {
		return brigade2.getLeft().getInitiative() - brigade1.getLeft().getInitiative();
	}
}

abstract class Brigade {
	private final int index;
	private final boolean log;
	@Getter
	private int unitCount;
	private final int hitPoints;
	private final int attack;
	@Getter
	private final int initiative;
	private final String attackElement;
	private final List<String> weakness = new ArrayList<>();
	private final List<String> immunity = new ArrayList<>();

	public Brigade(String instruction, int index, int boost, boolean log) {
		this.index = index;
		this.log = log;
		var instructions = Arrays.asList(instruction.split(" "));
		unitCount = Integer.parseInt(instructions.getFirst());
		hitPoints = Integer.parseInt(instructions.get(4));
		attack = Integer.parseInt(instructions.get(instructions.indexOf("does") + 1)) + boost;
		initiative = Integer.parseInt(instructions.getLast());
		attackElement = instructions.get(instructions.indexOf("does") + 2);
		if (instruction.contains("weak")) {
			var weaknessString = instruction.substring(instruction.indexOf("weak") + 8);
			weakness.addAll(Arrays.asList(weaknessString.substring(0,
																weaknessString.contains(";") ? weaknessString.indexOf(";") : weaknessString.indexOf(")"))
														.split(", ")));
		}
		if (instruction.contains("immune")) {
			var immunityString = instruction.substring(instruction.indexOf("immune") + 10);
			immunity.addAll(Arrays.asList(immunityString.substring(0,
																immunityString.contains(";") ? immunityString.indexOf(";") : immunityString.indexOf(")"))
														.split(", ")));
		}
	}

	public int computeReceivedDamage(Brigade attacker) {
		if (immunity.contains(attacker.attackElement)) {
			return 0;
		} else if (weakness.contains(attacker.attackElement)) {
			return attacker.getEffectivePower() * 2;
		} else {
			return attacker.getEffectivePower();
		}
	}

	public void receiveDamage(Brigade attacker) {
		var killCount = computeReceivedDamage(attacker) / hitPoints;
		if (log) {
			System.out.println(attacker + " killed " + Math.min(killCount, unitCount) + " " + this);
		}
		unitCount -= killCount;
	}

	public int getEffectivePower() {
		return unitCount * attack;
	}

	public boolean isAlive() {
		return unitCount > 0;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + index;
	}
}

class ImmuneSystem extends Brigade {
	public ImmuneSystem(String instruction, int index, int boost, boolean log) {
		super(instruction, index, boost, log);
	}
}

class Infection extends Brigade {
	public Infection(String instruction, int index, boolean log) {
		super(instruction, index, 0, log);
	}
}
