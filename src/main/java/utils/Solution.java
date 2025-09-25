package utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public abstract class Solution {
	private List<String> input;

	public Solution(String fileName) {
		setInput(fileName);
	}

	public Solution() {
		var year = getClass().getPackageName().split("\\.")[1];
		var day = getClass().getSimpleName().split("_")[0];
		setInput(year + "/" + day + "/input.txt");
	}

	@lombok.SneakyThrows
	private void setInput(String fileName) {
		input = Files.readAllLines(Paths.get(getClass().getClassLoader().getResource(fileName).toURI()));
	}

	public void solve() {
		System.out.println(solve(input));
	}

	protected abstract String solve(List<String> input);
}
