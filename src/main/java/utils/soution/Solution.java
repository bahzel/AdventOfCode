package utils.soution;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public abstract class Solution {
	private boolean log = true;
	protected List<String> input;

	public Solution(String fileName) {
		setInput(fileName);
	}

	public Solution() {
		var year = getClass().getPackageName().split("\\.")[1];
		var day = getClass().getSimpleName().split("_")[0];
		setInput(year + "/" + day + "/input.txt");
	}

	public Solution(List<String> input) {
		this.input = input;
	}

	@lombok.SneakyThrows
	private void setInput(String fileName) {
		input = Files.readAllLines(Paths.get(getClass().getClassLoader().getResource(fileName).toURI()));
	}

	public String solve() {
		initialize();
		var solution = doSolve();
		println(solution);
		return solution;
	}

	protected void initialize() {
	}

	protected abstract String doSolve();

	public Solution disableLog() {
		log = false;
		return this;
	}

	public void print(String value) {
		if (log) {
			print(value);
		}
	}

	public void println() {
		if (log) {
			System.out.println();
		}
	}

	public void println(Object value) {
		if (log) {
			System.out.println(value);
		}
	}

	public void println(long value) {
		if (log) {
			System.out.println(value);
		}
	}
}
