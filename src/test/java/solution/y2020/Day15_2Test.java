package solution.y2020;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day15_2Test {
	@Test
	public void test() {
		assertThat(new Day15_2().disableLog().solve()).isEqualTo("8546398");
	}
}
