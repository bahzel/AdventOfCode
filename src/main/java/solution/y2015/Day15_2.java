package solution.y2015;

import utils.soution.MapSolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day15_2 extends MapSolution<List<Ingredient>> {
	public static void main(String[] args) {
		new Day15_2().solve();
	}

	@Override
	protected List<Ingredient> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<Ingredient> ingredients) {
		var instructions = instruction.replace(",", "").split(" ");
		ingredients.add(new Ingredient(Long.parseLong(instructions[2]), Long.parseLong(instructions[4]),
				Long.parseLong(instructions[6]), Long.parseLong(instructions[8]), Long.parseLong(instructions[10])));
	}

	@Override
	protected String computeSolution(List<Ingredient> ingredients) {
		List<Cookie> cookies = new ArrayList<>();
		addIngredients(ingredients, new HashMap<>(), cookies, 0);

		return cookies.stream()
					  .filter(cookie -> cookie.computeCalories() == 500)
					  .map(Cookie::computeScore)
					  .mapToLong(Long::longValue)
					  .max()
					  .orElseThrow() + "";
	}

	private void addIngredients(List<Ingredient> ingredients, Map<Ingredient, Long> alreadyAdded, List<Cookie> cookies,
			long teaSpoonsInserted) {
		if (alreadyAdded.size() + 1 == ingredients.size()) {
			alreadyAdded.put(ingredients.getLast(), 100 - teaSpoonsInserted);
			cookies.add(new Cookie(alreadyAdded));
			return;
		}

		for (long i = 0; i <= 100 - teaSpoonsInserted; i++) {
			var newAlreadyAdded = new HashMap<>(alreadyAdded);
			newAlreadyAdded.put(ingredients.get(alreadyAdded.size()), i);
			addIngredients(ingredients, newAlreadyAdded, cookies, teaSpoonsInserted + i);
		}
	}
}
