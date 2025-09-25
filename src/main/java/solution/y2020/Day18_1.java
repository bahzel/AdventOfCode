package solution.y2020;

import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

import utils.soution.InstructionSolution;

public class Day18_1 extends InstructionSolution<String, AtomicLong> {
	public static void main(String[] args) {
		new Day18_1().solve();
	}

	@Override
	protected AtomicLong initializeValue() {
		return new AtomicLong();
	}

	@Override
	protected String transformInstruction(String instruction) {
		return "(" + instruction + ")";
	}

	private final Pattern SIMPLE_TASK = Pattern.compile("\\([\\d +*]*\\)");

	@Override
	protected boolean performInstruction(String s, AtomicLong atomicLong) {
		var task = s;
		while (task.startsWith("(")) {
			var matcher = SIMPLE_TASK.matcher(task);
			while (matcher.find()) {
				var simpleTask = matcher.group();
				task = task.replace(simpleTask, compute(simpleTask) + "");
			}
		}
		atomicLong.addAndGet(Long.parseLong(task));
		return false;
	}

	private long compute(String task) {
		var tasks = task.replace("(", "").replace(")", "").split(" ");
		var value = Long.parseLong(tasks[0]);
		for (var i = 1; i < tasks.length; i = i + 2) {
			switch (tasks[i]) {
			case "+":
				value += Long.parseLong(tasks[i + 1]);
				break;
			case "*":
				value *= Long.parseLong(tasks[i + 1]);
				break;
			default:
				throw new IllegalArgumentException("Invalid task: " + tasks[i]);
			}
		}
		return value;
	}

	@Override
	protected String getSolution(AtomicLong atomicLong) {
		return atomicLong.toString();
	}
}
