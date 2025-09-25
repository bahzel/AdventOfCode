package solution.y2022;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day10_1Test {
	@Test
	public void test() {
		assertThat(new Day10_1().disableLog().solve()).isEqualTo("14320");
	}
}
