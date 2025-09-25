package solution.y2020;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day13_1Test {
	@Test
	public void test() {
		assertThat(new Day13_1().disableLog().solve()).isEqualTo("2947");
	}
}
