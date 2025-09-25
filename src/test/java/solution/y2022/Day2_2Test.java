package solution.y2022;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day2_2Test {
	@Test
	public void test() {
		assertThat(new Day2_2().disableLog().solve()).isEqualTo("11258");
	}
}
