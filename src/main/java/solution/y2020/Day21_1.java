package solution.y2020;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import utils.soution.MapSolution;

public class Day21_1 extends MapSolution<List<Pair<List<String>, List<String>>>> {
	public static void main(String[] args) {
		new Day21_1().solve();
	}

	@Override
	protected List<Pair<List<String>, List<String>>> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<Pair<List<String>, List<String>>> pairs) {
		var instructions = instruction.replace(")", "").split(" \\(contains ");
		var ingredients = instructions[0].split(" ");
		var allergens = instructions[1].split(", ");
		pairs.add(Pair.of(new ArrayList<>(List.of(ingredients)), new ArrayList<>(List.of(allergens))));
	}

	@Override
	protected String computeSolution(List<Pair<List<String>, List<String>>> pairs) {
		var allergens = pairs.stream().map(Pair::getRight).flatMap(Collection::stream).distinct().toList();
		while (!allergens.isEmpty()) {
			for (var allergen : allergens) {
				var possibleIngredients = new ArrayList<String>();
				for (var recipe : pairs) {
					if (recipe.getRight().contains(allergen)) {
						if (possibleIngredients.isEmpty()) {
							possibleIngredients.addAll(recipe.getLeft());
						} else {
							possibleIngredients.retainAll(recipe.getLeft());
						}
					}
				}

				if (possibleIngredients.size() == 1) {
					println(possibleIngredients + " - " + allergen);
					pairs.forEach(recipe -> {
						recipe.getLeft().remove(possibleIngredients.getFirst());
						recipe.getRight().remove(allergen);
					});
				}
			}
			allergens = pairs.stream().map(Pair::getRight).flatMap(Collection::stream).distinct().toList();
		}

		return pairs.stream().map(Pair::getLeft).mapToLong(Collection::size).sum() + "";
	}
}
