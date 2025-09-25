package solution.y2024;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day1_2Test {
	@Test
	public void test() {
		assertThat(new Day1_2().disableLog().solve()).isEqualTo("20520794");
	}
}
