package solution.y2018;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day20_2Test {
	@Test
	public void test() {
		assertThat(new Day20_2().disableLog().solve()).isEqualTo("8434");
	}
}
