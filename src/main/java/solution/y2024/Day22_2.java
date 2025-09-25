package solution.y2024;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.tuple.Pair;

import utils.soution.InstructionSolution;

public class Day22_2 extends InstructionSolution<Long, List<Pair<List<Long>, List<Long>>>> {
	public static void main(String[] args) {
		new Day22_2().solve();
	}

	@Override
	protected List<Pair<List<Long>, List<Long>>> initializeValue() {
		return new ArrayList<>();
	}

	@Override
	protected Long transformInstruction(String instruction) {
		return Long.parseLong(instruction);
	}

	@Override
	protected boolean performInstruction(Long previousNumber, List<Pair<List<Long>, List<Long>>> offerLists) {
		var offerList = Pair.of((List<Long>) new ArrayList<Long>(), (List<Long>) new ArrayList<Long>());
		offerLists.add(offerList);

		var previousPrice = previousNumber % 10;
		for (int i = 0; i < 2000; i++) {
			var nextNumber = calculateNextSecretNumber(previousNumber);
			var nextPrice = nextNumber % 10;
			offerList.getLeft().add(nextPrice);
			offerList.getRight().add(nextPrice - previousPrice);
			previousNumber = nextNumber;
			previousPrice = nextPrice;
		}

		return false;
	}

	private long calculateNextSecretNumber(long secretNumber) {
		secretNumber = mix(secretNumber * 64, secretNumber);
		secretNumber = prune(secretNumber);
		secretNumber = mix(secretNumber / 32, secretNumber);
		secretNumber = prune(secretNumber);
		secretNumber = mix(secretNumber * 2048, secretNumber);
		return prune(secretNumber);
	}

	private long mix(long givenNumber, long secretNumber) {
		return givenNumber ^ secretNumber;
	}

	private long prune(long secretNumber) {
		return secretNumber % 16777216;
	}

	@Override
	protected String getSolution(List<Pair<List<Long>, List<Long>>> offerLists) {
		var cache = new HashMap<List<Long>, Long>();

		for (int i = 0; i < offerLists.size(); i++) {
			System.out.println(i);
			var monkeyIndex = i;
			var offerList = offerLists.get(monkeyIndex);
			for (int j = 3; j < offerList.getRight().size(); j++) {
				var sellIndex = j;
				cache.computeIfAbsent(offerList.getRight().subList(sellIndex - 3, sellIndex + 1),
						key -> offerList.getLeft().get(sellIndex) + computeSales(key, offerLists, monkeyIndex + 1));
			}
		}

		return cache.values().stream().mapToLong(Long::longValue).max().orElseThrow() + "";
	}

	private long computeSales(List<Long> sequence, List<Pair<List<Long>, List<Long>>> offerLists, int startingIndex) {
		var sum = 0;
		for (int i = startingIndex; i < offerLists.size(); i++) {
			var offerList = offerLists.get(i);
			for (int j = 3; j < offerList.getRight().size(); j++) {
				if (Objects.equals(offerList.getRight().get(j - 3), sequence.getFirst())
						&& Objects.equals(offerList.getRight().get(j - 2), sequence.get(1))
						&& Objects.equals(offerList.getRight().get(j - 1), sequence.get(2))
						&& Objects.equals(offerList.getRight().get(j), sequence.get(3))) {
					sum += offerList.getLeft().get(j);
					break;
				}
			}
		}
		return sum;
	}
}
