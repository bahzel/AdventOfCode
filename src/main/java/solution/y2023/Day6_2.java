package solution.y2023;

import java.util.List;

public class Day6_2 extends Day6_1 {
	public static void main(String[] args) {
		new Day6_2().solve();
	}

	@Override
	protected List<Long> getTimes() {
		return List.of(Long.parseLong(input.getFirst().replace(" ", "").split(":")[1]));
	}

	@Override
	protected List<Long> getDistances() {
		return List.of(Long.parseLong(input.getLast().replace(" ", "").split(":")[1]));
	}
}