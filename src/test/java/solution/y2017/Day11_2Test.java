package solution.y2017;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day11_2Test {
	@Test
	public void test() {
		assertThat(new Day11_2().disableLog().solve()).isEqualTo("1567");
	}
}
