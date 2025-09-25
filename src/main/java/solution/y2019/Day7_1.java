package solution.y2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.lang3.tuple.Pair;
import utils.soution.Solution;

public class Day7_1 extends Solution {
	public static void main(String[] args) {
		new Day7_1().solve();
	}

	@Override
	protected String doSolve() {
		var register = new ArrayList<>(
				Arrays.stream(input.getFirst().split(",")).mapToLong(Long::parseLong).boxed().toList());
		Queue<Pair<Long, List<Long>>> queue = new LinkedList<>();
		queue.add(Pair.of(0L, Arrays.asList(0L, 1L, 2L, 3L, 4L)));

		var maximumOutput = Long.MIN_VALUE;
		while (!queue.isEmpty()) {
			var currentStep = queue.poll();
			if (currentStep.getRight().isEmpty()) {
				if (maximumOutput < currentStep.getLeft()) {
					maximumOutput = currentStep.getLeft();
				}
			} else {
				for (int i = 0; i < currentStep.getRight().size(); i++) {
					var newInputList = new ArrayList<>(currentStep.getRight());
					newInputList.remove(i);

					var output = new LinkedBlockingQueue<Long>();
					IntCodeInterpreter.performComputation(new ArrayList<>(register),
							new LinkedBlockingQueue<>(List.of(currentStep.getRight().get(i), currentStep.getLeft())),
							output);
					queue.add(Pair.of(output.poll(), newInputList));
				}
			}
		}

		return maximumOutput + "";
	}
}
