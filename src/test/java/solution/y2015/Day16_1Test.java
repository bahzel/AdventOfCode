package solution.y2015;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day16_1Test {
	@Test
	public void test() {
		assertThat(new Day16_1().disableLog().solve()).isEqualTo("40");
	}
}
