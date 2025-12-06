package solution.y2025;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day3_2Test {
	@Test
	public void test() {
		assertThat(new Day3_2().disableLog().solve()).isEqualTo("168794698570517");
	}
}
