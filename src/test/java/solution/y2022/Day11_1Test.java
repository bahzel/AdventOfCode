package solution.y2022;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day11_1Test {
	@Test
	public void test() {
		assertThat(new Day11_1().disableLog().solve()).isEqualTo("120384");
	}
}
