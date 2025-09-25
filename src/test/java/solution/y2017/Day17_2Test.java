package solution.y2017;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day17_2Test {
	@Test
	public void test() {
		assertThat(new Day17_2().disableLog().solve()).isEqualTo("1080289");
	}
}
