package utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public abstract class Solution {
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

	public void solve() {
		System.out.println(doSolve());
	}

	protected abstract String doSolve();
}
