package solution.y2024;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day4_1Test {
	@Test
	public void test() {
		assertThat(new Day4_1().disableLog().solve()).isEqualTo("2642");
	}
}
