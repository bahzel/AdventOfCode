package solution.y2023;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.soution.MapSolution;

public class Day20_1 extends MapSolution<Map<String, Module>> {
	public static void main(String[] args) {
		new Day20_1().solve();
	}

	@Override
	protected Map<String, Module> initializeMapping() {
		return new HashMap<>();
	}

	@Override
	protected void transformInstruction(String instruction, Map<String, Module> stringModuleMap) {
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

		var lowCount = 0L;
		var highCount = 0L;
		for (var i = 0; i < 1000; i++) {
			var impulses = pressButton(modules);
			lowCount += impulses.getLeft();
			highCount += impulses.getRight();
		}

		return lowCount * highCount + "";
	}

	private Pair<Long, Long> pressButton(Map<String, Module> modules) {
		var lowCount = 0L;
		var highCount = 0L;

		Queue<Triple<String, String, Boolean>> queue = new LinkedList<>();
		queue.add(Triple.of("broadcaster", "button", false));

		while (!queue.isEmpty()) {
			var currentStep = queue.poll();
			if (currentStep.getRight()) {
				highCount++;
			} else {
				lowCount++;
			}

			if (currentStep.getLeft().equals("rx")) {
				continue;
			}

			if (modules.get(currentStep.getLeft()) == null) {
				println(currentStep.getLeft());
			}

			queue.addAll(modules.get(currentStep.getLeft()).send(currentStep.getMiddle(), currentStep.getRight()));
		}

		return Pair.of(lowCount, highCount);
	}
}

@AllArgsConstructor
@Getter
abstract class Module {
	protected final List<String> receivers;
	protected final String name;

	static Pair<String, Module> create(String input) {
		var inputs = input.split(" -> ");
		var receivers = Arrays.asList(inputs[1].split(", "));
		return switch (inputs[0].charAt(0)) {
		case '%' -> Pair.of(inputs[0].substring(1), new FlipFlop(inputs[0].substring(1), receivers));
		case '&' -> Pair.of(inputs[0].substring(1), new Conjunction(inputs[0].substring(1), receivers));
		case 'b' -> Pair.of(inputs[0], new Broadcaster(inputs[0], receivers));
		default -> throw new IllegalArgumentException("Unknown Module type: " + inputs[0]);
		};
	}

	abstract List<Triple<String, String, Boolean>> send(String sender, boolean impulse);
}

class FlipFlop extends Module {
	private boolean value = false;

	public FlipFlop(String name, List<String> receivers) {
		super(receivers, name);
	}

	@Override
	List<Triple<String, String, Boolean>> send(String sender, boolean impulse) {
		if (impulse) {
			return Collections.emptyList();
		}

		value = !value;
		return receivers.stream().map(receiver -> Triple.of(receiver, name, value)).toList();
	}
}

class Conjunction extends Module {
	private final Map<String, Boolean> inputs = new HashMap<>();

	public Conjunction(String name, List<String> receivers) {
		super(receivers, name);
	}

	public void addInput(String sender) {
		inputs.put(sender, false);
	}

	@Override
	List<Triple<String, String, Boolean>> send(String sender, boolean impulse) {
		inputs.put(sender, impulse);
		var value = inputs.values().stream().anyMatch(input -> !input);
		return receivers.stream().map(receiver -> Triple.of(receiver, name, value)).toList();
	}
}

class Broadcaster extends Module {
	public Broadcaster(String name, List<String> receivers) {
		super(receivers, name);
	}

	@Override
	List<Triple<String, String, Boolean>> send(String sender, boolean impulse) {
		return receivers.stream().map(receiver -> Triple.of(receiver, name, impulse)).toList();
	}
}