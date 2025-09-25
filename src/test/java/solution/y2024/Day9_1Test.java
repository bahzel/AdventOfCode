package solution.y2024;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day9_1Test {
	@Test
	public void test() {
		assertThat(new Day9_1().disableLog().solve()).isEqualTo("6259790630969");
	}
}
