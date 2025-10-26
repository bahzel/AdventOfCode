package solution.y2023;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import utils.soution.Solution;

public class Day5_2 extends Solution {
	public static void main(String[] args) {
		new Day5_2().solve();
	}

	@Override
	protected String doSolve() {
		var mappings = getMappings();
		var min = Long.MAX_VALUE;

		for (var seed : getSeeds()) {
			var currentValues = new ArrayList<>(List.of(seed));

			for (var mapping : mappings) {
				var nextValues = new ArrayList<Pair<Long, Long>>();

				for (var currentValue : currentValues) {
					for (var singleMapping : mapping) {
						if (currentValue.getLeft() >= singleMapping.getLeft()
								&& currentValue.getLeft() <= singleMapping.getMiddle()) {
							nextValues.add(Pair.of(currentValue.getLeft() + singleMapping.getRight(),
									Math.min(currentValue.getRight(), singleMapping.getMiddle())
											+ singleMapping.getRight()));
						} else if (currentValue.getRight() >= singleMapping.getLeft()
								&& currentValue.getRight() <= singleMapping.getMiddle()) {
							nextValues.add(Pair.of(singleMapping.getLeft() + singleMapping.getRight(),
									currentValue.getRight() + singleMapping.getRight()));
						} else if (currentValue.getLeft() < singleMapping.getLeft()
								&& currentValue.getRight() > singleMapping.getMiddle()) {
							nextValues.add(Pair.of(singleMapping.getLeft() + singleMapping.getRight(),
									singleMapping.getMiddle() + singleMapping.getRight()));
						}
					}
				}

				nextValues.sort(Comparator.comparing(Pair::getLeft));
				currentValues = nextValues;
			}

			min = Math.min(min, currentValues.getFirst().getLeft());
		}

		return min + "";
	}

	private List<Pair<Long, Long>> getSeeds() {
		var inputs = input.getFirst().split(" ");
		var seeds = new ArrayList<Pair<Long, Long>>();
		for (var i = 1; i < inputs.length; i = i + 2) {
			var from = Long.parseLong(inputs[i]);
			seeds.add(Pair.of(from, from + Long.parseLong(inputs[i + 1]) - 1));
		}

		seeds.sort(Comparator.comparing(Pair::getLeft));
		return seeds;
	}

	private List<List<Triple<Long, Long, Long>>> getMappings() {
		var mappings = new ArrayList<List<Triple<Long, Long, Long>>>();

		var start = 3;
		for (var i = start; i < input.size(); i++) {
			if (!input.get(i).isBlank()) {
				continue;
			}

			mappings.add(getSingleMappings(input.subList(start, i)));
			start = i + 2;
		}
		mappings.add(getSingleMappings(input.subList(start, input.size())));

		return mappings;
	}

	private List<Triple<Long, Long, Long>> getSingleMappings(List<String> inputs) {
		var singleMappings = new ArrayList<Triple<Long, Long, Long>>();

		for (var line : inputs) {
			var values = line.split(" ");
			var destination = Long.parseLong(values[0]);
			var source = Long.parseLong(values[1]);
			var size = Long.parseLong(values[2]);
			singleMappings.add(Triple.of(source, source + size - 1, destination - source));
		}

		singleMappings.sort(Comparator.comparing(Triple::getLeft));

		var source = 0L;
		for (var i = 0; i < singleMappings.size(); i++) {
			if (source < singleMappings.get(i).getLeft()) {
				singleMappings.add(i, Triple.of(source, singleMappings.get(i).getLeft() - 1, 0L));
				i++;
			}

			source = singleMappings.get(i).getMiddle() + 1;
		}
		singleMappings.add(Triple.of(source, Long.MAX_VALUE, 0L));

		return singleMappings;
	}
}