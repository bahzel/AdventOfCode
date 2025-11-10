package solution.y2023;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day18_1Test {
	@Test
	public void test() {
		assertThat(new Day18_1().disableLog().solve()).isEqualTo("38188");
	}
}
