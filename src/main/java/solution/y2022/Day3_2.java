package solution.y2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import utils.soution.MapSolution;

public class Day3_2 extends MapSolution<List<List<String>>> {
	public static void main(String[] args) {
		new Day3_2().solve();
	}

	@Override
	protected List<List<String>> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<List<String>> lists) {
		lists.add(Arrays.asList(instruction.split("")));
	}

	@Override
	protected String computeSolution(List<List<String>> lists) {
		var sum = 0;
		for (var i = 0; i < lists.size(); i = i + 3) {
			var wrongItem = new ArrayList<>(lists.get(i));
			wrongItem.retainAll(lists.get(i + 1));
			wrongItem.retainAll(lists.get(i + 2));
			sum += wrongItem.getFirst().charAt(0) - (StringUtils.isAllUpperCase(wrongItem.getFirst()) ? 39 : 97) + 1;
		}
		return sum + "";
	}
}
