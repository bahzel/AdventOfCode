package solution.y2016;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day8_1Test {
	@Test
	public void test() {
		assertThat(new Day8_1().disableLog().solve()).isEqualTo("121");
	}
}
