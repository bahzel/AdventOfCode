package solution.y2023;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class Day7_2 extends Day7_1 {
	public static void main(String[] args) {
		new Day7_2().solve();
	}

	@Override
	protected long getHandValue(String hand) {
		var cardCount = new HashMap<Character, Integer>();
		for (var card : hand.toCharArray()) {
			cardCount.put(card, StringUtils.countMatches(hand, card));
		}

		var charMax = cardCount	.entrySet()
								.stream()
								.filter(entry -> entry.getKey() != 'J')
								.max(Comparator.comparingInt(Map.Entry::getValue));
		var charSecondMax = cardCount	.entrySet()
										.stream()
										.filter(entry -> charMax.isPresent() && entry.getKey() != 'J'
												&& entry.getKey() != charMax.get().getKey())
										.max(Comparator.comparingInt(Map.Entry::getValue));
		var countJoker = cardCount.getOrDefault('J', 0);
		var countMax = 0;
		if (charMax.isPresent()) {
			countMax = charMax.get().getValue();
		}

		return switch (countMax + countJoker) {
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

	@Override
	protected long getCardValue(char card) {
		return switch (card) {
		case 'A' -> 13;
		case 'K' -> 12;
		case 'Q' -> 11;
		case 'J' -> 1;
		case 'T' -> 10;
		default -> Long.parseLong(card + "");
		};
	}
}