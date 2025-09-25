package solution.y2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import lombok.Getter;
import utils.soution.MapSolution;

public class Day7_1 extends MapSolution<Map<String, Bag>> {
	public static void main(String[] args) {
		new Day7_1().solve();
	}

	@Override
	protected Map<String, Bag> initializeMapping() {
		return new HashMap<>();
	}

	@Override
	protected void transformInstruction(String instruction, Map<String, Bag> bags) {
		var instructions = instruction.split(", ");
		var definition = instructions[0].split(" ");
		var bag = bags.computeIfAbsent(definition[0] + definition[1], Bag::new);

		if (!"no".equals(definition[4])) {
			bag.addChild(bags.computeIfAbsent(definition[5] + definition[6], Bag::new),
					Integer.parseInt(definition[4]));
		}

		for (int i = 1; i < instructions.length; ++i) {
			var child = instructions[i].split(" ");
			bag.addChild(bags.computeIfAbsent(child[1] + child[2], Bag::new), Integer.parseInt(child[0]));
		}
	}

	@Override
	protected String computeSolution(Map<String, Bag> bags) {
		return bags.values().stream().filter(bag -> bag.contains("shinygold")).count() - 1 + "";
	}
}

class Bag {
	@Getter
	private final String name;
	private final List<Pair<Bag, Integer>> children = new ArrayList<>();

	public Bag(String name) {
		this.name = name;
	}

	public void addChild(Bag child, int amount) {
		children.add(Pair.of(child, amount));
	}

	public boolean contains(String name) {
		if (this.name.equals(name)) {
			return true;
		}
		for (var child : children) {
			if (child.getLeft().contains(name)) {
				return true;
			}
		}
		return false;
	}

	public int countChildren() {
		return children.stream().mapToInt(child -> (child.getLeft().countChildren() + 1) * child.getRight()).sum();
	}
}
