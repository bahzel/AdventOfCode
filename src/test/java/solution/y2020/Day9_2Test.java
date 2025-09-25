package solution.y2020;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day9_2Test {
	@Test
	public void test() {
		assertThat(new Day9_2().disableLog().solve()).isEqualTo("219202240");
	}
}
