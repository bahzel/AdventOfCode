package solution.y2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import lombok.AllArgsConstructor;
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
			var inputA = new ConcurrentLinkedQueue<>(List.of(phaseSetting.getFirst(), 0L));
			var inputB = new ConcurrentLinkedQueue<>(List.of(phaseSetting.get(1)));
			var inputC = new ConcurrentLinkedQueue<>(List.of(phaseSetting.get(2)));
			var inputD = new ConcurrentLinkedQueue<>(List.of(phaseSetting.get(3)));
			var inputE = new ConcurrentLinkedQueue<>(List.of(phaseSetting.get(4)));
			var amplifierA = new Thread(new IntCodeInterpreterThread(new ArrayList<>(register), inputA, inputB));
			var amplifierB = new Thread(new IntCodeInterpreterThread(new ArrayList<>(register), inputB, inputC));
			var amplifierC = new Thread(new IntCodeInterpreterThread(new ArrayList<>(register), inputC, inputD));
			var amplifierD = new Thread(new IntCodeInterpreterThread(new ArrayList<>(register), inputD, inputE));
			var amplifierE = new Thread(new IntCodeInterpreterThread(new ArrayList<>(register), inputE, inputA));

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

@AllArgsConstructor
class IntCodeInterpreterThread implements Runnable {
	private final List<Long> register;
	private final ConcurrentLinkedQueue<Long> input;
	private final ConcurrentLinkedQueue<Long> output;

	@Override
	public void run() {
		IntCodeInterpreter.performComputation(register, input, output);
	}
}
