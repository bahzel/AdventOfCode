package solution.y2023;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.tuple.Triple;

import lombok.Getter;
import utils.MathUtils;
import utils.soution.MapSolution;

public class Day20_2 extends MapSolution<Map<String, Module>> {
	private final AtomicLong i = new AtomicLong(1);
	private SolvingConjunction solution;

	public static void main(String[] args) {
		new Day20_2().solve();
	}

	@Override
	protected Map<String, Module> initializeMapping() {
		return new HashMap<>();
	}

	@Override
	protected void transformInstruction(String instruction, Map<String, Module> stringModuleMap) {
		if (instruction.startsWith("&mf")) {
			solution = new SolvingConjunction("mf", Arrays.asList(instruction.split(" -> ")[1].split(", ")), i);
			stringModuleMap.put("mf", solution);
			return;
		}

		var module = Module.create(instruction);
		stringModuleMap.put(module.getLeft(), module.getRight());
	}

	@Override
	protected String computeSolution(Map<String, Module> modules) {
		modules.forEach((key, value) -> value.getReceivers().forEach(receiverName -> {
			var receiver = modules.get(receiverName);
			if (receiver instanceof Conjunction conjunction) {
				conjunction.addInput(key);
			}
		}));

		for (;; i.incrementAndGet()) {
			pressButton(modules);
			if (solution.isComplete()) {
				return solution.getSolution() + "";
			}
		}
	}

	private void pressButton(Map<String, Module> modules) {
		Queue<Triple<String, String, Boolean>> queue = new LinkedList<>();
		queue.add(Triple.of("broadcaster", "button", false));

		while (!queue.isEmpty()) {
			var currentStep = queue.poll();

			if (currentStep.getLeft().equals("rx")) {
				if (!currentStep.getRight()) {
					return;
				}
				continue;
			}

			if (modules.get(currentStep.getLeft()) == null) {
				println(currentStep.getLeft());
			}

			queue.addAll(modules.get(currentStep.getLeft()).send(currentStep.getMiddle(), currentStep.getRight()));
		}
	}
}

class SolvingConjunction extends Conjunction {
	@Getter
	private final Map<String, Long> cycles = new HashMap<>();
	private final AtomicLong counter;

	public SolvingConjunction(String name, List<String> receivers, AtomicLong counter) {
		super(name, receivers);
		this.counter = counter;
	}

	@Override
	public void addInput(String sender) {
		cycles.put(sender, 0L);
		super.addInput(sender);
	}

	@Override
	List<Triple<String, String, Boolean>> send(String sender, boolean impulse) {
		if (impulse) {
			if (cycles.get(sender) == 0L) {
				cycles.put(sender, counter.get());
			}
		}

		return super.send(sender, impulse);
	}

	public boolean isComplete() {
		return cycles.values().stream().noneMatch(cycle -> cycle == 0L);
	}

	public long getSolution() {
		var solution = 1L;
		for (var cycle : cycles.values()) {
			solution = MathUtils.leastCommonMultiple(solution, cycle);
		}
		return solution;
	}
}