package solution.y2023;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day18_2Test {
	@Test
	public void test() {
		assertThat(new Day18_2().disableLog().solve()).isEqualTo("93325849869340");
	}
}
