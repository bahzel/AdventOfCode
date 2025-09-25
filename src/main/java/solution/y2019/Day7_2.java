package solution.y2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import lombok.SneakyThrows;
import utils.soution.Solution;

public class Day7_2 extends Solution {
	public static void main(String[] args) {
		new Day7_2().solve();
	}

	@SneakyThrows
	@Override
	protected String doSolve() {
		var register = new ArrayList<>(
				Arrays.stream(input.getFirst().split(",")).mapToLong(Long::parseLong).boxed().toList());
		var maximumOutput = Long.MIN_VALUE;

		for (var phaseSetting : getPhaseSettings()) {
			var inputA = new LinkedBlockingQueue<>(List.of(phaseSetting.getFirst(), 0L));
			var inputB = new LinkedBlockingQueue<>(List.of(phaseSetting.get(1)));
			var inputC = new LinkedBlockingQueue<>(List.of(phaseSetting.get(2)));
			var inputD = new LinkedBlockingQueue<>(List.of(phaseSetting.get(3)));
			var inputE = new LinkedBlockingQueue<>(List.of(phaseSetting.get(4)));
			var amplifierA = new Thread(() -> new IntCodeInterpreter().withRegister(new ArrayList<>(register))
																	  .withInput(inputA)
																	  .withOutput(inputB)
																	  .performComputation());
			var amplifierB = new Thread(() -> new IntCodeInterpreter().withRegister(new ArrayList<>(register))
																	  .withInput(inputB)
																	  .withOutput(inputC)
																	  .performComputation());
			var amplifierC = new Thread(() -> new IntCodeInterpreter().withRegister(new ArrayList<>(register))
																	  .withInput(inputC)
																	  .withOutput(inputD)
																	  .performComputation());
			var amplifierD = new Thread(() -> new IntCodeInterpreter().withRegister(new ArrayList<>(register))
																	  .withInput(inputD)
																	  .withOutput(inputE)
																	  .performComputation());
			var amplifierE = new Thread(() -> new IntCodeInterpreter().withRegister(new ArrayList<>(register))
																	  .withInput(inputE)
																	  .withOutput(inputA)
																	  .performComputation());

			amplifierA.start();
			amplifierB.start();
			amplifierC.start();
			amplifierD.start();
			amplifierE.start();
			amplifierE.join();

			var output = new ArrayList<>(inputA);
			if (maximumOutput < output.getLast()) {
				maximumOutput = output.getLast();
			}
		}

		return maximumOutput + "";
	}

	private List<List<Long>> getPhaseSettings() {
		var phaseSettings = new ArrayList<List<Long>>();
		var possiblePhases = List.of(5L, 6L, 7L, 8L, 9L);
		Queue<List<Long>> queue = new LinkedList<>();
		queue.add(Collections.emptyList());

		while (!queue.isEmpty()) {
			var currentStep = queue.poll();
			if (currentStep.size() == possiblePhases.size()) {
				phaseSettings.add(currentStep);
				continue;
			}

			for (var possiblePhase : possiblePhases) {
				if (!currentStep.contains(possiblePhase)) {
					var newPhase = new ArrayList<>(currentStep);
					newPhase.add(possiblePhase);
					queue.add(newPhase);
				}
			}
		}

		return phaseSettings;
	}
}

