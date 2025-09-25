package solution.y2015;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day13_2Test {
	@Test
	public void test() {
		assertThat(new Day13_2().disableLog().solve()).isEqualTo("725");
	}
}
