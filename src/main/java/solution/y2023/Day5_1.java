package solution.y2023;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.tuple.Triple;

import utils.soution.Solution;

public class Day5_1 extends Solution {
	public static void main(String[] args) {
		new Day5_1().solve();
	}

	@Override
	protected String doSolve() {
		var mappings = getMappings();
		var min = Long.MAX_VALUE;

		for (var seed : getSeeds()) {
			var currentValue = seed;

			for (var mapping : mappings) {
				for (var singleMapping : mapping) {
					if (currentValue >= singleMapping.getLeft() && currentValue <= singleMapping.getMiddle()) {
						currentValue += singleMapping.getRight();
						break;
					}
				}
			}

			min = Math.min(min, currentValue);
		}

		return min + "";
	}

	private List<Long> getSeeds() {
		var inputs = input.getFirst().split(" ");
		var seeds = new ArrayList<Long>();
		for (var i = 1; i < inputs.length; i++) {
			seeds.add(Long.parseLong(inputs[i]));
		}
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