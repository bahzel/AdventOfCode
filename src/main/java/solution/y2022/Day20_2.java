package solution.y2022;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import utils.soution.MapSolution;

public class Day20_2 extends MapSolution<List<AtomicLong>> {
	public static void main(String[] args) {
		new Day20_2().solve();
	}

	@Override
	protected List<AtomicLong> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<AtomicLong> longList) {
		longList.add(new AtomicLong(Long.parseLong(instruction) * 811589153L));
	}

	@Override
	protected String computeSolution(List<AtomicLong> longList) {
		var workingList = new LinkedList<>(longList);
		for (var i = 0; i < 10; i++) {
			for (AtomicLong currentValue : longList) {
				var currentIndex = workingList.indexOf(currentValue);
				workingList.remove(currentIndex);
				var newIndex = (currentIndex + currentValue.get())
						- (currentIndex + currentValue.get()) / workingList.size() * workingList.size();
				if (newIndex <= 0) {
					newIndex += workingList.size();
				}
				workingList.add((int) newIndex, currentValue);
			}
		}

		var zeroIndex = 0;
		for (int i = 0; i < workingList.size(); i++) {
			if (workingList.get(i).get() == 0L) {
				zeroIndex = i;
				break;
			}
		}
		return workingList.get((zeroIndex + 1000) % workingList.size()).get()
				+ workingList.get((zeroIndex + 2000) % workingList.size()).get()
				+ workingList.get((zeroIndex + 3000) % workingList.size()).get() + "";
	}
}