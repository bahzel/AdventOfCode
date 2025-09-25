package solution.y2015;

import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.soution.Solution;

import java.util.Arrays;
import java.util.List;

public class Day21_1 extends Solution {
	private final List<Item> WEAPONS = Arrays.asList(new Item(8, 4, 0), new Item(10, 5, 0), new Item(25, 6, 0),
			new Item(40, 7, 0), new Item(74, 8, 0));
	private final List<Item> ARMORS = Arrays.asList(new Item(0, 0, 0), new Item(13, 0, 1), new Item(31, 0, 2),
			new Item(53, 0, 3), new Item(75, 0, 4), new Item(102, 0, 5));
	private final List<Item> RINGS = Arrays.asList(new Item(0, 0, 0), new Item(25, 1, 0), new Item(50, 2, 0),
			new Item(100, 3, 0), new Item(20, 0, 1), new Item(40, 0, 2), new Item(80, 0, 3));

	public static void main(String[] args) {
		new Day21_1().solve();
	}

	@Override
	protected String doSolve() {
		int enemyHealthPoint = Integer.parseInt(input.get(0).split(" ")[2]);
		int enemyDamage = Integer.parseInt(input.get(1).split(" ")[1]);
		int enemyArmor = Integer.parseInt(input.get(2).split(" ")[1]);
		int minimumCost = Integer.MAX_VALUE;

		for (Item value : WEAPONS) {
			for (Item item : ARMORS) {

				var playerWithoutRings = new Player(value, item, RINGS.getFirst(), RINGS.getFirst());
				if (playerWithoutRings.fight(new Player(enemyHealthPoint, enemyDamage, enemyArmor))) {
					minimumCost = Math.min(minimumCost, playerWithoutRings.getCost());
				}
				for (int ring1 = 0; ring1 < RINGS.size(); ring1++) {
					for (int ring2 = ring1 + 1; ring2 < RINGS.size(); ring2++) {
						var player = new Player(value, item, RINGS.get(ring1), RINGS.get(ring2));
						if (player.fight(new Player(enemyHealthPoint, enemyDamage, enemyArmor))) {
							minimumCost = Math.min(minimumCost, player.getCost());
						}
					}
				}
			}
		}

		return minimumCost + "";
	}
}

class Player {
	@Getter
	private int hitPoints;
	private final int damage;
	private final int armor;
	@Getter
	private final int cost;

	public Player(int hitPoints, int damage, int armor) {
		this.hitPoints = hitPoints;
		this.damage = damage;
		this.armor = armor;
		cost = 0;
	}

	public Player(Item... items) {
		hitPoints = 100;
		damage = Arrays.stream(items).mapToInt(Item::getDamage).sum();
		armor = Arrays.stream(items).mapToInt(Item::getArmor).sum();
		cost = Arrays.stream(items).mapToInt(Item::getCost).sum();
	}

	public boolean fight(Player enemy) {
		while (true) {
			if (enemy.doDamage(damage)) {
				return true;
			}
			if (doDamage(enemy.damage)) {
				return false;
			}
		}
	}

	public boolean doDamage(int damage) {
		hitPoints -= Math.max(1, damage - armor);
		return hitPoints <= 0;
	}
}

@AllArgsConstructor
@Getter
class Item {
	private final int cost;
	private final int damage;
	private final int armor;
}
