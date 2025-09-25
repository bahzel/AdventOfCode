package solution.y2020;

import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

import utils.soution.InstructionSolution;

public class Day18_2 extends InstructionSolution<String, AtomicLong> {
	public static void main(String[] args) {
		new Day18_2().solve();
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

	private final Pattern SUM = Pattern.compile("\\d* \\+ \\d*");

	private long compute(String task) {
		var taskCopy = task;
		while (taskCopy.contains("+")) {
			var matcher = SUM.matcher(taskCopy);
			matcher.find();
			var match = matcher.group();
			var sum = match.split(" ");
			taskCopy = taskCopy.replaceFirst(match.replace("+", "\\+"),
					Long.parseLong(sum[0]) + Long.parseLong(sum[2]) + "");
		}

		var tasks = taskCopy.replace("(", "").replace(")", "").split(" ");
		var value = Long.parseLong(tasks[0]);
		for (var i = 1; i < tasks.length; i = i + 2) {
			if (tasks[i].equals("*")) {
				value *= Long.parseLong(tasks[i + 1]);
			} else {
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
