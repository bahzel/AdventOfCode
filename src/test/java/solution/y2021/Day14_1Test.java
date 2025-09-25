package solution.y2021;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day14_1Test {
	@Test
	public void test() {
		assertThat(new Day14_1().disableLog().solve()).isEqualTo("2975");
	}
}
