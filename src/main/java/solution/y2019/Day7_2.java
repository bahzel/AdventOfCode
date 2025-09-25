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
		var register = Arrays.stream(input.getFirst().split(",")).mapToInt(Integer::parseInt).toArray();
		var maximumOutput = Integer.MIN_VALUE;

		for (var phaseSetting : getPhaseSettings()) {
			var inputA = new ConcurrentLinkedQueue<>(List.of(phaseSetting.getFirst(), 0));
			var inputB = new ConcurrentLinkedQueue<>(List.of(phaseSetting.get(1)));
			var inputC = new ConcurrentLinkedQueue<>(List.of(phaseSetting.get(2)));
			var inputD = new ConcurrentLinkedQueue<>(List.of(phaseSetting.get(3)));
			var inputE = new ConcurrentLinkedQueue<>(List.of(phaseSetting.get(4)));
			var amplifierA = new Thread(
					new IntCodeInterpreterThread(Arrays.copyOf(register, register.length), inputA, inputB));
			var amplifierB = new Thread(
					new IntCodeInterpreterThread(Arrays.copyOf(register, register.length), inputB, inputC));
			var amplifierC = new Thread(
					new IntCodeInterpreterThread(Arrays.copyOf(register, register.length), inputC, inputD));
			var amplifierD = new Thread(
					new IntCodeInterpreterThread(Arrays.copyOf(register, register.length), inputD, inputE));
			var amplifierE = new Thread(
					new IntCodeInterpreterThread(Arrays.copyOf(register, register.length), inputE, inputA));

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

	private List<List<Integer>> getPhaseSettings() {
		var phaseSettings = new ArrayList<List<Integer>>();
		var possiblePhases = List.of(5, 6, 7, 8, 9);
		Queue<List<Integer>> queue = new LinkedList<>();
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
	private final int[] register;
	private final ConcurrentLinkedQueue<Integer> input;
	private final ConcurrentLinkedQueue<Integer> output;

	@Override
	public void run() {
		IntCodeInterpreter.performComputation(register, input, output);
	}
}
