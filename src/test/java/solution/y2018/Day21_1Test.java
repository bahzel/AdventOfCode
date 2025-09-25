package solution.y2018;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day21_1Test {
	@Test
	public void test() {
		assertThat(new Day21_1().disableLog().solve()).isEqualTo("9566170");
	}
}
