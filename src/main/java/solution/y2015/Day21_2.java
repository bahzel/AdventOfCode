package solution.y2015;

import utils.soution.Solution;

import java.util.Arrays;
import java.util.List;

public class Day21_2 extends Solution {
	private final List<Item> WEAPONS = Arrays.asList(new Item(8, 4, 0), new Item(10, 5, 0), new Item(25, 6, 0),
			new Item(40, 7, 0), new Item(74, 8, 0));
	private final List<Item> ARMORS = Arrays.asList(new Item(0, 0, 0), new Item(13, 0, 1), new Item(31, 0, 2),
			new Item(53, 0, 3), new Item(75, 0, 4), new Item(102, 0, 5));
	private final List<Item> RINGS = Arrays.asList(new Item(0, 0, 0), new Item(25, 1, 0), new Item(50, 2, 0),
			new Item(100, 3, 0), new Item(20, 0, 1), new Item(40, 0, 2), new Item(80, 0, 3));

	public static void main(String[] args) {
		new Day21_2().solve();
	}

	@Override
	protected String doSolve() {
		int enemyHealthPoint = Integer.parseInt(input.get(0).split(" ")[2]);
		int enemyDamage = Integer.parseInt(input.get(1).split(" ")[1]);
		int enemyArmor = Integer.parseInt(input.get(2).split(" ")[1]);
		int maximumCost = Integer.MIN_VALUE;

		for (Item value : WEAPONS) {
			for (Item item : ARMORS) {

				var playerWithoutRings = new Player(value, item, RINGS.getFirst(), RINGS.getFirst());
				if (!playerWithoutRings.fight(new Player(enemyHealthPoint, enemyDamage, enemyArmor))) {
					maximumCost = Math.max(maximumCost, playerWithoutRings.getCost());
				}
				for (int ring1 = 0; ring1 < RINGS.size(); ring1++) {
					for (int ring2 = ring1 + 1; ring2 < RINGS.size(); ring2++) {
						var player = new Player(value, item, RINGS.get(ring1), RINGS.get(ring2));
						if (!player.fight(new Player(enemyHealthPoint, enemyDamage, enemyArmor))) {
							maximumCost = Math.max(maximumCost, player.getCost());
						}
					}
				}
			}
		}

		return maximumCost + "";
	}
}
