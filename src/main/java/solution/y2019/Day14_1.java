package solution.y2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.soution.MapSolution;

public class Day14_1 extends MapSolution<Map<String, Recipe>> {
	private final long amountOfOre;

	public static void main(String[] args) {
		new Day14_1().solve();
	}

	public Day14_1() {
		this(1L);
	}

	public Day14_1(long amountOfOre) {
		super();
		this.amountOfOre = amountOfOre;
	}

	@Override
	protected Map<String, Recipe> initializeMapping() {
		return new HashMap<>();
	}

	@Override
	protected void transformInstruction(String instruction, Map<String, Recipe> stringRecipeMap) {
		var instructions = instruction.split(" => ");
		var result = instructions[1].split(" ");
		var ingredients = instructions[0].split(", ");

		var recipe = new Recipe(new Ingredient(Long.parseLong(result[0]), result[1]));
		for (var ingredient : ingredients) {
			var ingredientValues = ingredient.split(" ");
			recipe.addIngredient(new Ingredient(Long.parseLong(ingredientValues[0]), ingredientValues[1]));
		}

		stringRecipeMap.put(recipe.getResult().getName(), recipe);
	}

	@Override
	protected String computeSolution(Map<String, Recipe> stringRecipeMap) {
		var neededOre = 0L;

		var leftovers = new HashMap<String, Long>();

		Queue<Ingredient> queue = new LinkedList<>();
		queue.add(new Ingredient(amountOfOre, "FUEL"));
		while (!queue.isEmpty()) {
			var ingredient = queue.remove();
			if ("ORE".equals(ingredient.getName())) {
				neededOre += ingredient.getAmount();
				continue;
			}

			var amountNeeded = ingredient.getAmount();
			var leftOver = leftovers.getOrDefault(ingredient.getName(), 0L);
			if (amountNeeded <= leftOver) {
				leftovers.put(ingredient.getName(), leftOver - amountNeeded);
				continue;
			} else {
				amountNeeded -= leftOver;
			}

			var recipe = stringRecipeMap.get(ingredient.getName());
			var factorProduced = amountNeeded / recipe.getResult().getAmount();
			if (amountNeeded % recipe.getResult().getAmount() > 0) {
				factorProduced++;
			}
			var amountProduced = factorProduced * recipe.getResult().getAmount();
			leftovers.put(ingredient.getName(), amountProduced - amountNeeded);

			for (var neededIngredient : recipe.getIngredients()) {
				queue.add(new Ingredient(neededIngredient.getAmount() * factorProduced, neededIngredient.getName()));
			}
		}

		return neededOre + "";
	}
}

@Getter
class Recipe {
	private final Ingredient result;
	private final List<Ingredient> ingredients = new ArrayList<>();

	public Recipe(Ingredient result) {
		this.result = result;
	}

	public void addIngredient(Ingredient ingredient) {
		ingredients.add(ingredient);
	}
}

@AllArgsConstructor
@Getter
class Ingredient {
	private final long amount;
	private final String name;
}
