package solution.y2018;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day5_1Test {
	@Test
	public void test() {
		assertThat(new Day5_1().disableLog().solve()).isEqualTo("11194");
	}
}
