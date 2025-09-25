package solution.y2015;

import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.soution.MapSolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day15_1 extends MapSolution<List<Ingredient>> {
	public static void main(String[] args) {
		new Day15_1().solve();
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

		return cookies.stream().map(Cookie::computeScore).mapToLong(Long::longValue).max().orElseThrow() + "";
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

@AllArgsConstructor
class Cookie {
	private final Map<Ingredient, Long> ingredients;

	public long computeScore() {
		var capacity = ingredients.entrySet()
								  .stream()
								  .map(ingredient -> ingredient.getValue() * ingredient.getKey().getCapacity())
								  .reduce(0L, Long::sum);
		var durability = ingredients.entrySet()
									.stream()
									.map(ingredient -> ingredient.getValue() * ingredient.getKey().getDurability())
									.reduce(0L, Long::sum);
		var flavor = ingredients.entrySet()
								.stream()
								.map(ingredient -> ingredient.getValue() * ingredient.getKey().getFlavor())
								.reduce(0L, Long::sum);
		var texture = ingredients.entrySet()
								 .stream()
								 .map(ingredient -> ingredient.getValue() * ingredient.getKey().getTexture())
								 .reduce(0L, Long::sum);

		if (capacity < 0 || flavor < 0 || durability < 0 || texture < 0) {
			return 0;
		} else {
			return capacity * durability * flavor * texture;
		}
	}

	public long computeCalories() {
		return ingredients.entrySet()
						  .stream()
						  .map(ingredient -> ingredient.getValue() * ingredient.getKey().getCalories())
						  .reduce(0L, Long::sum);
	}
}

@AllArgsConstructor
@Getter
class Ingredient {
	private final long capacity;
	private final long durability;
	private final long flavor;
	private final long texture;
	private final long calories;
}
