package solution.y2024;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day12_2Test {
	@Test
	public void test() {
		assertThat(new Day12_2().disableLog().solve()).isEqualTo("877492");
	}
}
