package solution.y2016;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day21_2Test {
	@Test
	public void test() {
		assertThat(new Day21_2().disableLog().solve()).isEqualTo("fhgcdaeb");
	}
}
