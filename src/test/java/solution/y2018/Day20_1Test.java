package solution.y2018;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day20_1Test {
	@Test
	public void test() {
		assertThat(new Day20_1().disableLog().solve()).isEqualTo("4155");
	}
}
