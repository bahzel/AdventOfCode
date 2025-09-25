package solution.y2017;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day25_1Test {
	@Test
	public void test() {
		assertThat(new Day25_1().disableLog().solve()).isEqualTo("633");
	}
}
