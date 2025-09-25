package solution.y2020;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day17_1Test {
	@Test
	public void test() {
		assertThat(new Day17_1().disableLog().solve()).isEqualTo("218");
	}
}
