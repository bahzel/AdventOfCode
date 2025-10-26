package solution.y2023;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import utils.soution.MapSolution;

public class Day7_1 extends MapSolution<List<Pair<Long, Long>>> {
	public static void main(String[] args) {
		new Day7_1().solve();
	}

	@Override
	protected List<Pair<Long, Long>> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<Pair<Long, Long>> pairList) {
		var instructions = instruction.split(" ");
		pairList.add(Pair.of(getHandValue(instructions[0]) * (long) Math.pow(13, 6) + getCardValues(instructions[0]),
				Long.parseLong(instructions[1])));
	}

	protected long getHandValue(String hand) {
		var cardCount = new HashMap<Character, Integer>();
		for (var card : hand.toCharArray()) {
			cardCount.put(card, StringUtils.countMatches(hand, card));
		}

		var charMax = cardCount	.entrySet()
								.stream()
								.max(Comparator.comparingInt(Map.Entry::getValue))
								.orElseThrow()
								.getKey();
		var charSecondMax = cardCount	.entrySet()
										.stream()
										.filter(entry -> entry.getKey() != charMax)
										.max(Comparator.comparingInt(Map.Entry::getValue));

		return switch (cardCount.get(charMax)) {
		case 5 -> 7;
		case 4 -> 6;
		case 3 -> {
			if (charSecondMax.isPresent() && cardCount.get(charSecondMax.get().getKey()) == 2) {
				yield 5;
			}
			yield 4;
		}
		case 2 -> {
			if (charSecondMax.isPresent() && cardCount.get(charSecondMax.get().getKey()) == 2) {
				yield 3;
			}
			yield 2;
		}
		default -> 1;
		};
	}

	private long getCardValues(String hand) {
		var value = 0L;
		for (var i = 0; i < hand.length(); i++) {
			value += (long) (getCardValue(hand.charAt(hand.length() - i - 1)) * Math.pow(13, i));
		}
		return value;
	}

	protected long getCardValue(char card) {
		return switch (card) {
		case 'A' -> 13;
		case 'K' -> 12;
		case 'Q' -> 11;
		case 'J' -> 10;
		case 'T' -> 9;
		default -> Long.parseLong(card + "") - 1;
		};
	}

	@Override
	protected String computeSolution(List<Pair<Long, Long>> pairList) {
		var value = 0L;
		pairList.sort(Comparator.comparing(Pair::getLeft));

		for (var i = 0; i < pairList.size(); i++) {
			value += (i + 1) * pairList.get(i).getRight();
		}

		return value + "";
	}
}