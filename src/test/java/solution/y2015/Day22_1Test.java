package solution.y2015;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day22_1Test {
	@Test
	public void test() {
		assertThat(new Day22_1().disableLog().solve()).isEqualTo("953");
	}
}
