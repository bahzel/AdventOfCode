package solution.y2015;

import org.apache.commons.lang3.function.TriConsumer;
import org.apache.commons.lang3.tuple.Triple;
import utils.soution.Solution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day22_2 extends Solution {
	private long GLOBAL_MINIMUM = Long.MAX_VALUE;

	private final List<Triple<TriConsumer<Wizard, Wizard, Integer>, Integer, Integer>> SPELLS = List.of(
			Triple.of((player, enemy, timeLeft) -> enemy.doDamageIgnoringArmor(4), 1, 53),
			Triple.of((player, enemy, timeLeft) -> {
				enemy.doDamageIgnoringArmor(2);
				player.heal(2);
			}, 1, 73), Triple.of((player, enemy, timeLeft) -> {
				if (timeLeft == 6) {
					player.gainArmor(7);
				} else if (timeLeft == 1) {
					player.gainArmor(-7);
				}
			}, 6, 113), Triple.of((player, enemy, timeLeft) -> enemy.doDamageIgnoringArmor(3), 6, 173),
			Triple.of((player, enemy, timeLeft) -> player.healMana(101), 5, 229));

	public static void main(String[] args) {
		new Day22_2().solve();
	}

	@Override
	protected String doSolve() {
		int enemyHealthPoint = Integer.parseInt(input.get(0).split(" ")[2]);
		int enemyDamage = Integer.parseInt(input.get(1).split(" ")[1]);
		return castSpell(new Wizard(49, 0, 500), new Wizard(enemyHealthPoint, enemyDamage, 0), new HashMap<>()) + "";
	}

	private int computeCheapestVictory(Wizard player, Wizard enemy,
			Map<TriConsumer<Wizard, Wizard, Integer>, Integer> activeEffects) {
		if (player.getMana() < 0 || player.getSpentMana() >= GLOBAL_MINIMUM) {
			return Integer.MAX_VALUE;
		}

		executeEffects(player, enemy, activeEffects);
		if (enemy.getHitPoints() <= 0) {
			GLOBAL_MINIMUM = Math.min(GLOBAL_MINIMUM, player.getSpentMana());
			return player.getSpentMana();
		}

		player.doDamage(enemy.getDamage() + 1);
		if (player.getHitPoints() <= 0) {
			return Integer.MAX_VALUE;
		}

		executeEffects(player, enemy, activeEffects);
		if (enemy.getHitPoints() <= 0) {
			GLOBAL_MINIMUM = Math.min(GLOBAL_MINIMUM, player.getSpentMana());
			return player.getSpentMana();
		}

		return castSpell(player, enemy, activeEffects);
	}

	private int castSpell(Wizard player, Wizard enemy,
			Map<TriConsumer<Wizard, Wizard, Integer>, Integer> activeEffects) {
		int minimumSpentMana = Integer.MAX_VALUE;
		for (var spell : SPELLS) {
			var newPlayer = new Wizard(player);
			var newEffects = new HashMap<>(activeEffects);
			newEffects.put(spell.getLeft(), spell.getMiddle());
			newPlayer.spendMana(spell.getRight());
			minimumSpentMana = Math.min(minimumSpentMana,
					computeCheapestVictory(newPlayer, new Wizard(enemy), newEffects));
		}
		return minimumSpentMana;
	}

	private void executeEffects(Wizard player, Wizard enemy,
			Map<TriConsumer<Wizard, Wizard, Integer>, Integer> activeEffects) {
		for (var activeEffect : activeEffects.keySet()) {
			activeEffect.accept(player, enemy, activeEffects.get(activeEffect));
			activeEffects.put(activeEffect, activeEffects.get(activeEffect) - 1);
		}
		activeEffects.entrySet().removeIf(entry -> entry.getValue() == 0);
	}
}
