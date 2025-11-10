package solution.y2023;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day19_1Test {
	@Test
	public void test() {
		assertThat(new Day19_1().disableLog().solve()).isEqualTo("350678");
	}
}
