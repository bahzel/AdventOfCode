package solution.y2019;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day13_1Test {
	@Test
	public void test() {
		assertThat(new Day13_1().disableLog().solve()).isEqualTo("420");
	}
}
