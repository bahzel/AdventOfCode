package solution.y2024;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day4_2Test {
	@Test
	public void test() {
		assertThat(new Day4_2().disableLog().solve()).isEqualTo("1974");
	}
}
