package solution.y2023;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day8_2Test {
	@Test
	public void test() {
		assertThat(new Day8_2().disableLog().solve()).isEqualTo("12833235391111");
	}
}
