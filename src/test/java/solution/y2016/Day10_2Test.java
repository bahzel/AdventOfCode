package solution.y2016;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day10_2Test {
	@Test
	public void test() {
		assertThat(new Day10_2().disableLog().solve()).isEqualTo("22847");
	}
}
