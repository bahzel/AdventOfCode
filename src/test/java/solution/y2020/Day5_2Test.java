package solution.y2020;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day5_2Test {
	@Test
	public void test() {
		assertThat(new Day5_2().disableLog().solve()).isEqualTo("705");
	}
}
