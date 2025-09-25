package solution.y2018;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;
import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day15_1 extends GridSolution<Unit> {
	private final int attackPower;
	@Getter
	private final List<CombatUnit> elves = new ArrayList<>();

	public static void main(String[] args) {
		new Day15_1().solve();
	}

	public Day15_1() {
		super();
		attackPower = 3;
	}

	public Day15_1(int attackPower) {
		super();
		this.attackPower = attackPower;
	}

	@Override
	protected GridElement<Unit> transformCell(char ch, int x, int y) {
		Unit unit = switch (ch) {
			case '#' -> new Wall();
			case '.' -> new Cavern();
			case 'G' -> new Goblin();
			case 'E' -> new Elf(attackPower);
			default -> throw new IllegalArgumentException("Invalid character: " + ch);
		};

		var position = new GridElement<>(unit, x, y);
		unit.setPosition(position);
		return position;
	}

	@Override
	protected String computeSolution() {
		var rounds = -1;
		var goblins = new ArrayList<>(stream().filter(unit -> unit.getValue() instanceof Goblin)
											  .map(unit -> (Goblin) unit.getValue())
											  .toList());
		elves.addAll(
				stream().filter(unit -> unit.getValue() instanceof Elf).map(unit -> (Elf) unit.getValue()).toList());
		List<CombatUnit> combatUnits = new ArrayList<>(goblins);
		combatUnits.addAll(elves);
		print(combatUnits);

		while (!goblins.isEmpty() && !elves.isEmpty()) {
			rounds++;

			println("Round: -- " + rounds + " --");

			combatUnits.sort(CombatUnit::compareTo);
			for (var combatUnit : combatUnits) {
				if (combatUnit.getHealthPoints() <= 0) {
					continue;
				}

				combatUnit.performTurn();
			}

			combatUnits.removeIf(unit -> unit.getHealthPoints() <= 0);
			goblins.removeIf(unit -> unit.getHealthPoints() <= 0);
			elves.removeIf(unit -> unit.getHealthPoints() <= 0);
			print(combatUnits);
		}

		if (elves.isEmpty()) {
			return rounds * goblins.stream().mapToLong(CombatUnit::getHealthPoints).sum() + "";
		} else {
			return rounds * elves.stream().mapToLong(CombatUnit::getHealthPoints).sum() + "";
		}
	}

	public void print(List<CombatUnit> units) {
		println();
		for (int y = 0; y < grid[0].length; y++) {
			for (GridElement<Unit>[] gridElements : grid) {
				if (gridElements[y].getValue() instanceof Wall) {
					print("#");
				} else if (gridElements[y].getValue() instanceof Cavern) {
					print(".");
				} else if (gridElements[y].getValue() instanceof Goblin) {
					print("G");
				} else if (gridElements[y].getValue() instanceof Elf) {
					print("E");
				}
			}
			println();
		}
		for (var unit : units) {
			println(unit);
		}
		println();
	}
}

@Setter
@Getter
abstract class Unit {
	private GridElement<Unit> position;

	public Optional<Integer> lengthOfWayTo(CombatUnit enemy) {
		if (!(this instanceof Cavern)) {
			return Optional.empty();
		}

		var cache = new HashSet<GridElement<Unit>>();

		Queue<Pair<GridElement<Unit>, Integer>> queue = new LinkedList<>();
		queue.add(Pair.of(getPosition(), 0));

		while (!queue.isEmpty()) {
			var position = queue.remove();
			if (position.getLeft().getValue() == enemy) {
				return Optional.of(position.getRight());
			} else if (position.getLeft().getValue() instanceof Cavern && cache.add(position.getLeft())) {
				queue.add(Pair.of(position.getLeft().getLeftNeighbour(), position.getRight() + 1));
				queue.add(Pair.of(position.getLeft().getUpperNeighbour(), position.getRight() + 1));
				queue.add(Pair.of(position.getLeft().getRightNeighbour(), position.getRight() + 1));
				queue.add(Pair.of(position.getLeft().getLowerNeighbour(), position.getRight() + 1));
			}
		}

		return Optional.empty();
	}
}

class Wall extends Unit {
}

class Cavern extends Unit {
	public Cavern() {
	}

	public Cavern(GridElement<Unit> position) {
		setPosition(position);
	}
}

abstract class CombatUnit extends Unit implements Comparable<CombatUnit> {
	@Getter
	private int healthPoints = 200;
	private final int attackPower;

	public CombatUnit(int attackPower) {
		this.attackPower = attackPower;
	}

	public void performTurn() {
		var enemyInRange = getEnemyInRange();
		if (enemyInRange.isEmpty()) {
			var closestEnemy = getClosestEnemy();
			if (closestEnemy.isPresent()) {
				int lengthFromUp = getPosition().getUpperNeighbour()
												.getValue()
												.lengthOfWayTo(closestEnemy.get())
												.orElse(Integer.MAX_VALUE);
				int lengthFromRight = getPosition().getRightNeighbour()
												   .getValue()
												   .lengthOfWayTo(closestEnemy.get())
												   .orElse(Integer.MAX_VALUE);
				int lengthFromDown = getPosition().getLowerNeighbour()
												  .getValue()
												  .lengthOfWayTo(closestEnemy.get())
												  .orElse(Integer.MAX_VALUE);
				int lengthFromLeft = getPosition().getLeftNeighbour()
												  .getValue()
												  .lengthOfWayTo(closestEnemy.get())
												  .orElse(Integer.MAX_VALUE);
				var closestLength = Math.min(lengthFromUp,
						Math.min(lengthFromRight, Math.min(lengthFromLeft, lengthFromDown)));
				if (closestLength == lengthFromUp) {
					moveTo(getPosition().getUpperNeighbour());
				} else if (closestLength == lengthFromLeft) {
					moveTo(getPosition().getLeftNeighbour());
				} else if (closestLength == lengthFromRight) {
					moveTo(getPosition().getRightNeighbour());
				} else {
					moveTo(getPosition().getLowerNeighbour());
				}

				enemyInRange = getEnemyInRange();
			}
		}

		enemyInRange.ifPresent(this::attack);
	}

	private Optional<CombatUnit> getEnemyInRange() {
		return getPosition().getBorderingNeighbours()
							.stream()
							.map(GridElement::getValue)
							.filter(unit -> getClassOfEnemy().isInstance(unit))
							.map(unit -> (CombatUnit) unit)
							.min((enemy1, enemy2) -> enemy1.getHealthPoints() == enemy2.getHealthPoints()
									? enemy1.compareTo(enemy2)
									: enemy1.getHealthPoints() - enemy2.getHealthPoints());
	}

	private Optional<CombatUnit> getClosestEnemy() {
		var cache = new HashSet<GridElement<Unit>>();
		var closestEnemies = new ArrayList<CombatUnit>();

		var minimumLength = Integer.MAX_VALUE;
		Queue<Pair<GridElement<Unit>, Integer>> queue = new LinkedList<>();
		queue.add(Pair.of(getPosition().getUpperNeighbour(), 1));
		queue.add(Pair.of(getPosition().getLeftNeighbour(), 1));
		queue.add(Pair.of(getPosition().getRightNeighbour(), 1));
		queue.add(Pair.of(getPosition().getLowerNeighbour(), 1));

		while (!queue.isEmpty()) {
			var position = queue.remove();
			if (position.getLeft() == null || position.getRight() > minimumLength) {
				break;
			}

			if (getClassOfEnemy().isInstance(position.getLeft().getValue())) {
				minimumLength = position.getRight();
				closestEnemies.add((CombatUnit) position.getLeft().getValue());
			} else if (position.getLeft().getValue() instanceof Cavern && cache.add(position.getLeft())) {
				queue.add(Pair.of(position.getLeft().getLeftNeighbour(), position.getRight() + 1));
				queue.add(Pair.of(position.getLeft().getUpperNeighbour(), position.getRight() + 1));
				queue.add(Pair.of(position.getLeft().getRightNeighbour(), position.getRight() + 1));
				queue.add(Pair.of(position.getLeft().getLowerNeighbour(), position.getRight() + 1));
			}
		}

		return closestEnemies.stream().min(Comparator.naturalOrder());
	}

	private void attack(CombatUnit enemy) {
		System.out.println(this + " attacks " + enemy);

		enemy.healthPoints -= attackPower;
		if (enemy.healthPoints <= 0) {
			enemy.getPosition().setValue(new Cavern(enemy.getPosition()));
		}
	}

	private void moveTo(GridElement<Unit> newPosition) {
		if (!(newPosition.getValue() instanceof Cavern)) {
			throw new IllegalStateException();
		}

		System.out.println(getClass().getSimpleName() + " moves from " + getPosition() + " to " + newPosition);

		getPosition().setValue(new Cavern(getPosition()));
		setPosition(newPosition);
		newPosition.setValue(this);
	}

	@Override
	public int compareTo(CombatUnit other) {
		return getPosition().getCoordinates().getY() == other.getPosition().getCoordinates().getY()
				? getPosition().getCoordinates().getX() - other.getPosition().getCoordinates().getX()
				: getPosition().getCoordinates().getY() - other.getPosition().getCoordinates().getY();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ": " + getPosition() + ", HP: " + getHealthPoints();
	}

	abstract Class<?> getClassOfEnemy();
}

class Goblin extends CombatUnit {
	public Goblin() {
		super(3);
	}

	@Override
	Class<?> getClassOfEnemy() {
		return Elf.class;
	}
}

class Elf extends CombatUnit {
	public Elf(int attackPower) {
		super(attackPower);
	}

	@Override
	Class<?> getClassOfEnemy() {
		return Goblin.class;
	}
}
