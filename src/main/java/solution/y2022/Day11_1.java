package solution.y2022;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.collections4.ListUtils;

import lombok.Getter;
import utils.MathUtils;
import utils.soution.Solution;

public class Day11_1 extends Solution {
	public static void main(String[] args) {
		new Day11_1().solve();
	}

	@Override
	protected String doSolve() {
		List<Monkey> monkeys = new ArrayList<>();
		ListUtils.partition(input, 7).forEach(instructions -> monkeys.add(new CalmingMonkey(instructions, monkeys)));
		for (var i = 0; i < 20; i++) {
			monkeys.forEach(Monkey::perform);
		}
		monkeys.sort(Comparator.comparingLong(Monkey::getCount));
		return monkeys.getLast().getCount() * monkeys.get(monkeys.size() - 2).getCount() + "";
	}
}

class CalmingMonkey extends Monkey {
	public CalmingMonkey(List<String> instructions, List<Monkey> monkeys) {
		super(instructions, monkeys);
	}

	@Override
	protected BigInteger inspect(BigInteger item) {
		return item.divide(BigInteger.valueOf(3));
	}

	@Override
	protected BigInteger reduceItem(BigInteger item) {
		return item;
	}
}

class Monkey {
	private final List<BigInteger> items = new ArrayList<>();
	private final Function<BigInteger, BigInteger> operation;
	private final Function<BigInteger, Integer> test;
	private final List<Monkey> monkeys;
	@Getter
	private final int divideBy;
	private BigInteger lowestCommonDenominator = null;
	@Getter
	private long count;

	public Monkey(List<String> instructions, List<Monkey> monkeys) {
		this.monkeys = monkeys;

		var itemsString = instructions.get(1).split(" ");
		for (var i = 4; i < itemsString.length; i++) {
			items.add(new BigInteger(itemsString[i].replace(",", "")));
		}

		var operations = instructions.get(2).split(" ");
		switch (operations[6]) {
		case "*":
			if (operations[7].equals("old")) {
				operation = item -> item.multiply(item);
			} else {
				var factor = new BigInteger(operations[7]);
				operation = item -> item.multiply(factor);
			}
			break;
		case "+":
			var summand = new BigInteger(operations[7]);
			operation = item -> item.add(summand);
			break;
		default:
			throw new IllegalArgumentException("Unknown operation " + operations[6]);
		}

		divideBy = Integer.parseInt(instructions.get(3).split(" ")[5]);
		var testValue = BigInteger.valueOf(divideBy);
		var testTrue = Integer.parseInt(instructions.get(4).split(" ")[9]);
		var testFalse = Integer.parseInt(instructions.get(5).split(" ")[9]);
		test = item -> item.mod(testValue).equals(BigInteger.ZERO) ? testTrue : testFalse;
	}

	public void perform() {
		for (var item : items) {
			item = operation.apply(item);
			item = inspect(item);
			monkeys.get(test.apply(item)).items.add(reduceItem(item));
		}
		count += items.size();
		items.clear();
	}

	protected BigInteger inspect(BigInteger item) {
		return item;
	}

	protected BigInteger reduceItem(BigInteger item) {
		return item.mod(getLowestCommonDenominator());
	}

	private BigInteger getLowestCommonDenominator() {
		if (lowestCommonDenominator != null) {
			return lowestCommonDenominator;
		}

		var computeLowestCommonDenominator = 1L;
		for (var monkey : monkeys) {
			computeLowestCommonDenominator = MathUtils.leastCommonMultiple(computeLowestCommonDenominator,
					monkey.getDivideBy());
		}
		lowestCommonDenominator = BigInteger.valueOf(computeLowestCommonDenominator);
		return lowestCommonDenominator;
	}
}
