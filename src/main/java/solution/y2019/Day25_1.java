package solution.y2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import utils.soution.Solution;

import static org.awaitility.Awaitility.await;

public class Day25_1 extends Solution {
	private boolean manual = false;

	public static void main(String[] args) {
		new Day25_1().manual().solve();
	}

	public Day25_1 manual() {
		manual = true;
		return this;
	}

	@Override
	protected String doSolve() {
		if (manual) {
			solveManually();
		}
		return solveAutomatically();
	}

	private String solveAutomatically() {
		var register = new ArrayList<>(
				Arrays.stream(input.getFirst().split(",")).mapToLong(Long::parseLong).boxed().toList());
		var output = new BufferingQueue();
		var intCodeInterpreter = new IntCodeInterpreter()	.withRegister(register)
															.withInput(new LinkedBlockingQueue<>())
															.withOutput(output);
		new Thread(intCodeInterpreter::performComputation).start();

		input("east", intCodeInterpreter, output);
		input("take food ration", intCodeInterpreter, output);
		input("south", intCodeInterpreter, output);
		input("take prime number", intCodeInterpreter, output);
		input("north", intCodeInterpreter, output);
		input("east", intCodeInterpreter, output);
		input("take manifold", intCodeInterpreter, output);
		input("east", intCodeInterpreter, output);
		input("east", intCodeInterpreter, output);
		input("take jam", intCodeInterpreter, output);
		input("west", intCodeInterpreter, output);
		input("north", intCodeInterpreter, output);
		input("east", intCodeInterpreter, output);
		input("take spool of cat6", intCodeInterpreter, output);
		input("west", intCodeInterpreter, output);
		input("north", intCodeInterpreter, output);
		input("take fuel cell", intCodeInterpreter, output);
		input("south", intCodeInterpreter, output);
		input("south", intCodeInterpreter, output);
		input("west", intCodeInterpreter, output);
		input("west", intCodeInterpreter, output);
		input("west", intCodeInterpreter, output);
		input("north", intCodeInterpreter, output);
		input("north", intCodeInterpreter, output);
		input("west", intCodeInterpreter, output);
		input("take mug", intCodeInterpreter, output);
		input("east", intCodeInterpreter, output);
		input("north", intCodeInterpreter, output);
		input("east", intCodeInterpreter, output);
		input("east", intCodeInterpreter, output);
		input("take loom", intCodeInterpreter, output);
		input("west", intCodeInterpreter, output);
		input("west", intCodeInterpreter, output);
		input("south", intCodeInterpreter, output);
		input("south", intCodeInterpreter, output);
		input("west", intCodeInterpreter, output);
		input("north", intCodeInterpreter, output);
		input("west", intCodeInterpreter, output);
		input("drop jam", intCodeInterpreter, output);
		input("drop loom", intCodeInterpreter, output);
		input("drop mug", intCodeInterpreter, output);
		input("drop spool of cat6", intCodeInterpreter, output);
		input("drop prime number", intCodeInterpreter, output);
		input("drop food ration", intCodeInterpreter, output);
		input("drop fuel cell", intCodeInterpreter, output);
		input("drop manifold", intCodeInterpreter, output);

		var itemList = List.of("jam", "loom", "mug", "spool of cat6", "prime number", "food ration", "fuel cell",
				"manifold");
		Queue<Set<String>> queue = new LinkedList<>();
		for (var item : itemList) {
			queue.add(Set.of(item));
		}

		var cache = new HashSet<Set<String>>();
		while (!queue.isEmpty()) {
			var items = queue.remove();
			if (!cache.add(items)) {
				continue;
			}

			if (tryItems(items, intCodeInterpreter, output)) {
				var solution = output.getBuffer();
				var startIndex = solution.indexOf("typing") + 7;
				return solution.substring(startIndex, startIndex + 9);
			}

			for (var item : itemList) {
				if (items.contains(item)) {
					continue;
				}
				var newItems = new HashSet<>(items);
				newItems.add(item);
				queue.add(newItems);
			}
		}

		return "";
	}

	private boolean tryItems(Set<String> items, IntCodeInterpreter intCodeInterpreter, BufferingQueue output) {
		for (var item : items) {
			input("take " + item, intCodeInterpreter, output);
		}

		input("north", intCodeInterpreter, output);
		await().until(output::isReady);
		if (output.isFinished()) {
			return true;
		}

		for (var item : items) {
			input("drop " + item, intCodeInterpreter, output);
		}
		return false;
	}

	private void input(String input, IntCodeInterpreter intCodeInterpreter, BufferingQueue output) {
		await().until(output::isReady);
		output.getBuffer();
		intCodeInterpreter.inputString(input);
	}

	private void solveManually() {
		var register = new ArrayList<>(
				Arrays.stream(input.getFirst().split(",")).mapToLong(Long::parseLong).boxed().toList());
		var intCodeInterpreter = new IntCodeInterpreter()	.withRegister(register)
															.withInput(new LinkedBlockingQueue<>())
															.withOutput(new WritingQueue());
		new Thread(intCodeInterpreter::performComputation).start();

		var scanner = new Scanner(System.in);
		while (true) {
			intCodeInterpreter.inputString(scanner.nextLine());
		}
	}
}

class WritingQueue extends LinkedBlockingQueue<Long> {
	@Override
	public boolean add(Long aLong) {
		System.out.print((char) aLong.intValue());
		return true;
	}
}

class BufferingQueue extends LinkedBlockingQueue<Long> {
	private String buffer = "";

	public boolean isReady() {
		return buffer.contains("Command?") || isFinished();
	}

	public boolean isFinished() {
		return buffer.contains("on the keypad at the main airlock.");
	}

	public String getBuffer() {
		var copy = buffer;
		buffer = "";
		return copy;
	}

	@Override
	public boolean add(Long aLong) {
		buffer += (char) aLong.intValue();
		return true;
	}
}
